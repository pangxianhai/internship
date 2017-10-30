package cn.edu.swufe.lawschool.internship.student.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.StudentError;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.journal.service.DiaryJournalService;
import cn.edu.swufe.lawschool.internship.journal.service.WeeklyJournalService;
import cn.edu.swufe.lawschool.internship.student.mapper.StudentMapper;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.model.StudentParam;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.service.UniversityDepartmentService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       学生服务</p>
 * <p>Description: 学生服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TutorService tutorService;

    @Autowired
    FlowService flowService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AttendService attendService;

    @Autowired
    DiaryJournalService diaryJournalService;

    @Autowired
    WeeklyJournalService weeklyJournalService;

    @Autowired
    UniversityDepartmentService universityDepartmentService;

    public Long addStudent (Student student) {
        universityDepartmentService.checkDepartmentOrganization(
                Arrays.asList(student.getUniversityId(), student.getCollegeId(), student.getDepartmentId(),
                              student.getSpecialityId()));

        //先注册学生用户信息
        UserInfo userInfo = student.getUserInfo();
        userInfo.setCreatedBy(student.getCreatedBy());
        userInfo.setUserName(student.getStudentNumber());
        userInfo.setUserType(UserType.STUDENT);
        Long userId = userService.addUser(student.getUserInfo());
        //注册学生信息
        student.setUserId(userId);
        student.setName(userInfo.getName());
        student.setStatus(Status.VALID);
        student.setCreatedTime(DateUtil.currentMilliseconds());
        student.setUpdatedBy(student.getCreatedBy());
        student.setUpdatedTime(student.getCreatedTime());
        int count = studentMapper.insert(student);
        if (count < 1) {
            throw new InternshipException(StudentError.ADD_STUDENT_ERROR);
        }
        return student.getId();
    }

    public List<Student> getStudent (StudentParam studentParam, Page page) {
        List<Student> studentList = studentMapper.select(studentParam, page);
        studentList.forEach(student -> {
            buildStudentUniversityInfo(student);
        });
        return studentList;
    }

    public void updateStudentCompany (String studentDestId, String companyDesId, String operator) {
        Student student = getStudentByDesId(studentDestId);
        Company company = companyService.getCompanyByDestId(companyDesId);
        if (student.getCompanyId() != null) {
            if (student.getCompanyId().equals(company.getId())) {
                return;
            }
            companyService.minusStudent(student.getCompanyId(), operator);
            Tutor oldTutor = tutorService.getCompanyLeader(student.getCompanyId());
            Tutor newTutor = tutorService.getCompanyLeader(company.getId());
            List<FlowRecord> needChangeFlowRecords = flowService.getNeedChangeFlowRecord(student.getId(),
                                                                                         oldTutor.getUserId(),
                                                                                         OperateUserType.COMPANY_LEADER);
            if (CollectionUtil.isNotEmpty(needChangeFlowRecords)) {
                if (newTutor == null) {
                    throw new InternshipException(StudentError.UPDATE_STUDENT_ERROR_NEW_COMPANY_NO_LEADER);
                }
                //学生需要原单位领导审核的消息更改为新的单位负责人
                flowService.changeOperateUser(student.getId(), oldTutor.getUserId(),
                                              OperateUserType.COMPANY_LEADER, newTutor.getUserId(),
                                              newTutor.getName(), operator);
            }
        }
        StudentParam _student = new StudentParam();
        _student.setId(student.getId());
        _student.setCompanyId(company.getId());
        _student.setCompanyName(company.getCompanyName());
        _student.setRemoveTutor(true);
        updateStudent(_student, operator);
        companyService.addStudent(company.getId(), operator);
        return;
    }

    public void updateStudentTeacher (String teacherDestId, String studentDestId, String operator) {
        Student student = getStudentByDesId(studentDestId);
        Teacher teacher = teacherService.getTeacherByDesId(teacherDestId);
        checkStudentAndTeacherIsAtOneUniversityDepartment(teacher, student);
        if (student.getTeacherId() != null) {
            if (student.getTeacherId().equals(teacher.getId())) {
                return;
            }
            teacherService.minusStudent(student.getTeacherId(), operator);
            Teacher oldTeacher = teacherService.getTeacherById(student.getTeacherId());
            //学生需要原带队老师审核的消息更改为新的带队老师
            flowService.changeOperateUser(student.getId(), oldTeacher.getUserId(), OperateUserType.TEACHER,
                                          teacher.getUserId(), teacher.getName(), operator);
        }
        StudentParam _student = new StudentParam();
        _student.setId(student.getId());
        _student.setTeacherId(teacher.getId());
        _student.setTeacherName(teacher.getName());
        updateStudent(_student, operator);
        teacherService.addStudent(teacher.getId(), operator);
        return;
    }

    public void updateStudentTutor (String tutorDestId, String studentDestId, String operator) {
        Student student = getStudentByDesId(studentDestId);
        Tutor tutor = tutorService.getTutorByDesId(tutorDestId);
        if (!tutor.getCompanyId().equals(student.getCompanyId())) {
            throw new InternshipException(StudentError.UPDATE_STUDENT_ERROR_COMPANY_DIFFERENT);
        }
        if (student.getTutorId() != null) {
            if (student.getTutorId().equals(tutor.getId())) {
                return;
            }
            tutorService.minusStudent(student.getTutorId(), operator);
            Tutor oldTutor = tutorService.getTutorById(student.getTutorId());
            flowService.changeOperateUser(student.getId(), oldTutor.getUserId(), OperateUserType.TUTOR,
                                          tutor.getUserId(), tutor.getName(), operator);
        }
        StudentParam _student = new StudentParam();
        _student.setId(student.getId());
        _student.setTutorId(tutor.getId());
        _student.setTutorName(tutor.getName());
        updateStudent(_student, operator);
        tutorService.addStudent(tutor.getId(), operator);
        return;
    }

    public Student getStudentById (Long id) {
        if (id == null) {
            return null;
        }
        StudentParam studentParam = new StudentParam();
        studentParam.setId(id);
        return selectOne(studentParam);
    }

    public Student getStudentByDesId (String desId) {
        if (StringUtil.isEmpty(desId)) {
            return null;
        }
        StudentParam studentParam = new StudentParam();
        studentParam.setDesId(desId);
        return selectOne(studentParam);
    }

    public Student getStudentByUserId (Long userId) {
        if (userId == null) {
            return null;
        }
        StudentParam studentParam = new StudentParam();
        studentParam.setUserId(userId);
        return selectOne(studentParam);
    }

    private Student selectOne (StudentParam studentParam) {
        List<Student> students = studentMapper.select(studentParam);
        if (CollectionUtil.isNotEmpty(students)) {
            return buildStudentUniversityInfo(students.get(0));
        } else {
            return null;
        }
    }

    public int updateStudent (StudentParam studentParam, String operator) {
        if (studentParam.getId() == null) {
            throw new InternshipException(StudentError.UPDATE_STUDENT_ID_EMPTY);
        }
        if (studentParam.getUpdateUniversity()) {
            universityDepartmentService.checkDepartmentOrganization(
                    Arrays.asList(studentParam.getUniversityId(), studentParam.getCollegeId(),
                                  studentParam.getDepartmentId(), studentParam.getSpecialityId()));
        }
        studentParam.setUpdatedBy(operator);
        studentParam.setUpdatedTime(DateUtil.currentMilliseconds());
        int c = studentMapper.update(studentParam);
        if (c <= 0) {
            throw new InternshipException(StudentError.UPDATE_STUDENT_ERROR);
        }
        return c;
    }

    public boolean checkStudentEnableInternship (Student student) {
        if (student == null) {
            throw new InternshipException(StudentError.STUDENT_NOT_EXIST);
        }
        if (student.getTeacherId() == null) {
            throw new InternshipException(StudentError.NO_TEACHER);
        }
        if (student.getCompanyId() == null) {
            throw new InternshipException(StudentError.NO_COMPANY);
        }
        if (student.getTutorId() == null) {
            throw new InternshipException(StudentError.NO_TUTOR);
        }
        return true;
    }

    public void deleteStudent (String destId, String operator) {
        Student student = this.getStudentByDesId(destId);
        if (student == null) {
            throw new InternshipException(StudentError.STUDENT_NOT_EXIST);
        }
        Integer count = attendService.getAttendUserCount(student.getUserId());
        if (count != null && count.intValue() > 0) {
            throw new InternshipException(StudentError.DELETE_STUDENT_ERROR_BY_HAS_INTERNSHIP);
        }
        count = weeklyJournalService.getStudentWeeklyJournalCount(student.getId());
        if (count != null && count.intValue() > 0) {
            throw new InternshipException(StudentError.DELETE_STUDENT_ERROR_BY_HAS_INTERNSHIP);
        }
        count = diaryJournalService.getStudentDiaryJournalCount(student.getId());
        if (count != null && count.intValue() > 0) {
            throw new InternshipException(StudentError.DELETE_STUDENT_ERROR_BY_HAS_INTERNSHIP);
        }
        if (student.getCompanyId() != null) {
            companyService.minusStudent(student.getCompanyId(), operator);
        }
        if (student.getTutorId() != null) {
            tutorService.minusStudent(student.getTutorId(), operator);
        }
        if (student.getTeacherId() != null) {
            teacherService.minusStudent(student.getTeacherId(), operator);
        }
        userService.delete(student.getUserId());
        studentMapper.delete(student.getId());
    }

    private Student buildStudentUniversityInfo (Student student) {
        student.setUniversity(
                universityDepartmentService.getUniversityDepartmentById(student.getUniversityId()));
        student.setCollege(universityDepartmentService.getUniversityDepartmentById(student.getCollegeId()));
        student.setDepartment(
                universityDepartmentService.getUniversityDepartmentById(student.getDepartmentId()));
        student.setSpeciality(
                universityDepartmentService.getUniversityDepartmentById(student.getSpecialityId()));
        return student;
    }

    private void checkStudentAndTeacherIsAtOneUniversityDepartment (Teacher teacher, Student student) {
        if (!teacher.getUniversityId().equals(student.getUniversityId()) || !teacher.getCollegeId().equals(
                student.getCollegeId())) {
            throw new InternshipException(StudentError.UNIVERSITY_NO_MATCH);
        }
        if (teacher.getDepartmentId() != null && student.getDepartmentId() != null) {
            if (!teacher.getDepartmentId().equals(student.getDepartmentId())) {
                throw new InternshipException(StudentError.UNIVERSITY_NO_MATCH);
            }
        } else if (teacher.getDepartmentId() != null || student.getDepartmentId() != null) {
            throw new InternshipException(StudentError.UNIVERSITY_NO_MATCH);
        }
        if (teacher.getSpecialityId() != null && student.getSpecialityId() != null) {
            if (!teacher.getSpecialityId().equals(student.getSpecialityId())) {
                throw new InternshipException(StudentError.UNIVERSITY_NO_MATCH);
            }
        } else if (teacher.getSpecialityId() != null || student.getSpecialityId() != null) {
            throw new InternshipException(StudentError.UNIVERSITY_NO_MATCH);
        }
    }
}
