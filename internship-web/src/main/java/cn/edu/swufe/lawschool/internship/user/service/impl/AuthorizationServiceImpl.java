package cn.edu.swufe.lawschool.internship.user.service.impl;

import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.StudentError;
import cn.edu.swufe.lawschool.internship.exception.TeacherError;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2015年12月02
 * <p>Title:       权限服务</p>
 * <p>Description: 权限服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TutorService tutorService;

    @Autowired
    CompanyService companyService;

    @Autowired
    LoginService loginService;

    public void checkStudentAuthorization (UserInfo loginInfo, Student student) {
        if (student == null) {
            throw new InternshipException(StudentError.STUDENT_NOT_EXIST);
        }
        if (loginInfo.isStudent()) {
            Student currentStudent = loginService.getStudentInfo();
            if (!student.getId().equals(currentStudent.getId())) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        } else if (loginInfo.isTutor()) {
            Tutor tutor = loginService.getTutorInfo();
            if (tutor.isLeader()) {
                if (!tutor.getCompanyId().equals(student.getCompanyId())) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            } else {
                if (!tutor.getId().equals(student.getTutorId())) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            }
        } else if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (teacher.isUniversityLeader()) {
                if (!teacher.getUniversityId().equals(student.getUniversityId())) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            } else if (teacher.isCollegeLeader()) {
                if (!(teacher.getUniversityId().equals(student.getUniversityId()) &&
                              teacher.getCollegeId().equals(student.getCollegeId()))) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            } else if (teacher.isDepartmentLeader()) {
                if (!(teacher.getUniversityId().equals(student.getUniversityId()) &&
                              teacher.getCollegeId().equals(student.getCollegeId()) &&
                              teacher.getDepartmentId().equals(student.getDepartmentId()))) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            } else if (teacher.isSpecialityLeader()) {
                if (!(teacher.getUniversityId().equals(student.getUniversityId()) &&
                              teacher.getCollegeId().equals(student.getCollegeId()) &&
                              teacher.getDepartmentId().equals(student.getDepartmentId()) &&
                              teacher.getSpecialityId().equals(student.getSpecialityId()))) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            } else {
                if (!teacher.getId().equals(student.getTeacherId())) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            }
        }
    }

    public void checkStudentAuthorization (UserInfo loginInfo, Long studentId) {
        Student student = studentService.getStudentById(studentId);
        checkStudentAuthorization(loginInfo, student);
    }

    public void checkTeacherAuthorization (UserInfo loginInfo, Teacher teacher) {
        if (loginInfo.isStudent()) {
            Student student = loginService.getStudentInfo();
            if (!student.getTeacherId().equals(teacher.getId())) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        } else if (loginInfo.isTeacher()) {
            Teacher currentTeacher = loginService.getTeacherInfo();
            if (currentTeacher.getTeacherType().higher(teacher.getTeacherType())) {
                if (currentTeacher.isUniversityLeader()) {
                    if (!currentTeacher.getUniversityId().equals(teacher.getUniversityId())) {
                        throw new InternshipException(ErrorType.NO_ACCESS);
                    }
                } else if (currentTeacher.isCollegeLeader()) {
                    if (!(currentTeacher.getUniversityId().equals(teacher.getUniversityId()) &&
                                  currentTeacher.getCollegeId().equals(teacher.getCollegeId()))) {
                        throw new InternshipException(ErrorType.NO_ACCESS);
                    }
                } else if (currentTeacher.isDepartmentLeader()) {
                    if (!(currentTeacher.getUniversityId().equals(teacher.getUniversityId()) &&
                                  currentTeacher.getCollegeId().equals(teacher.getCollegeId()) &&
                                  currentTeacher.getDepartmentId().equals(teacher.getDepartmentId()))) {
                        throw new InternshipException(ErrorType.NO_ACCESS);
                    }
                } else if (currentTeacher.isSpecialityLeader()) {
                    if (!(currentTeacher.getUniversityId().equals(teacher.getUniversityId()) &&
                                  currentTeacher.getCollegeId().equals(teacher.getCollegeId()) &&
                                  currentTeacher.getDepartmentId().equals(teacher.getDepartmentId()) &&
                                  currentTeacher.getSpecialityId().equals(teacher.getSpecialityId()))) {
                        throw new InternshipException(ErrorType.NO_ACCESS);
                    }
                } else {
                    //没有这种可能
                    throw new InternshipException(ErrorType.SYS_ERROR);
                }
            } else {
                //currentTeacher不是teacher的上级 自己对自己才有权限
                if (!currentTeacher.getId().equals(teacher.getId())) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            }
        } else if (loginInfo.isTutor()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
    }

    public void checkTeacherAuthorization (UserInfo loginInfo, Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        checkTeacherAuthorization(loginInfo, teacher);
    }

    public void checkTutorAuthorization (UserInfo loginInfo, Tutor tutor) {
        if (loginInfo.isStudent()) {
            Student student = loginService.getStudentInfo();
            if (!student.getTutorId().equals(tutor.getId())) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        } else if (loginInfo.isTeacher()) {
            Teacher teacher = teacherService.getTeacherByUserId(loginInfo.getId());
            if (!teacher.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        } else if (loginInfo.isTutor()) {
            Tutor _tutor = tutorService.getTutorByUserId(loginInfo.getId());
            if (_tutor.getId().equals(tutor.getId())) {
                return;
            }
            if (tutor.getCompanyId().equals(tutor.getCompanyId()) && _tutor.isLeader()) {
                return;
            }
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
    }

    public void checkTutorAuthorization (UserInfo loginInfo, Long tutorId) {
        Tutor tutor = tutorService.getTutorById(tutorId);
        checkTutorAuthorization(loginInfo, tutor);
    }

    public OperateAuthorization getOperateAuthorization (UserInfo loginInfo, Student student) {
        OperateAuthorization operateAuthorization = new OperateAuthorization();
        if (loginInfo.isTeacher()) {
            if (loginInfo.isLeader()) {
                operateAuthorization.setCanChangeCompany(true);
                operateAuthorization.setCanChangeTeacher(true);
                operateAuthorization.setCanDeleteStudent(true);
            }
            if (student != null) {
                Teacher teacher = loginService.getTeacherInfo();
                if (teacher.getId().equals(student.getTeacherId())) {
                    operateAuthorization.setCanSendEvaluation(true);
                    operateAuthorization.setCanReviewAssessment(true);
                }
            }
        } else if (loginInfo.isTutor()) {
            if (loginInfo.isLeader()) {
                operateAuthorization.setCanChangeTutor(true);
            }
            if (student != null) {
                Tutor tutor = loginService.getTutorInfo();
                if (tutor.getId().equals(student.getTutorId())) {
                    operateAuthorization.setCanSendAssessment(true);
                }
                if (tutor.isLeader() && tutor.getCompanyId().equals(student.getCompanyId())) {
                    operateAuthorization.setCanReviewAssessment(true);
                }
            }
        } else if (loginInfo.isSysadmin()) {
            operateAuthorization.setCanChangeCompany(true);
            operateAuthorization.setCanChangeTeacher(true);
            operateAuthorization.setCanDeleteStudent(true);
            operateAuthorization.setCanChangeTutor(true);
        } else if (loginInfo.isStudent()) {
            if (student != null) {
                Student currentStudent = loginService.getStudentInfo();
                if (currentStudent.getId().equals(student.getId())) {
                    operateAuthorization.setCanUpdateStudent(true);
                    operateAuthorization.setCanCreateReport(true);
                }
            }
        } else {

        }
        return operateAuthorization;
    }

    public void checkUniversityDepartmentAuthorization (UserInfo loginInfo, UniversityDepartment department) {
        if (loginInfo.isSysadmin()) {
            return;
        } else if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (department.getIsUniversity() && teacher.isUniversityLeader() &&
                    teacher.getUniversityId().equals(department.getId())) {
                return;
            }
            if (department.getIsCollege() && teacher.isCollegeLeader() && teacher.getCollegeId().equals(
                    department.getId())) {
                return;
            }
            if (department.getIsDepartment() && teacher.isDepartmentLeader() &&
                    teacher.getDepartmentId().equals(department.getId())) {
                return;
            }
            if (department.getIsSpeciality() && teacher.isSpecialityLeader() &&
                    teacher.getSpecialityId().equals(department.getId())) {
                return;
            }
            throw new InternshipException(ErrorType.NO_ACCESS);
        } else {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
    }

    public void resetStudentCondition (UserInfo loginInfo, Student student) {
        if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (teacher.isUniversityLeader()) {
                student.setUniversityId(teacher.getUniversityId());
            } else if (teacher.isCollegeLeader()) {
                student.setUniversityId(teacher.getUniversityId());
                student.setCollegeId(teacher.getCollegeId());
            } else if (teacher.isDepartmentLeader()) {
                student.setUniversityId(teacher.getUniversityId());
                student.setCollegeId(teacher.getCollegeId());
                student.setDepartmentId(teacher.getDepartmentId());
            } else if (teacher.isSpecialityLeader()) {
                student.setUniversityId(teacher.getUniversityId());
                student.setCollegeId(teacher.getCollegeId());
                student.setDepartmentId(teacher.getDepartmentId());
                student.setSpecialityId(teacher.getSpecialityId());
            } else {
                student.setUniversityId(teacher.getUniversityId());
                student.setCollegeId(teacher.getCollegeId());
                student.setDepartmentId(teacher.getDepartmentId());
                student.setSpecialityId(teacher.getSpecialityId());
                student.setTeacherId(teacher.getId());
            }
        } else if (loginInfo.isTutor()) {
            Tutor tutor = loginService.getTutorInfo();
            if (loginInfo.isLeader()) {
                student.setCompanyId(tutor.getCompanyId());
            } else {
                student.setCompanyId(tutor.getCompanyId());
                student.setTutorId(tutor.getId());
            }
        } else if (loginInfo.isStudent()) {
            Student _student = loginService.getStudentInfo();
            student.setId(_student.getId());
        }
    }

    public void resetTeacherCondition (UserInfo loginInfo, Teacher teacher) {
        if (loginInfo.isTeacher()) {
            Teacher currentTeacher = loginService.getTeacherInfo();
            if (currentTeacher == null) {
                throw new InternshipException(TeacherError.NO_ACCESS);
            }
            if (currentTeacher.isUniversityLeader()) {
                teacher.setUniversityId(currentTeacher.getUniversityId());
            } else if (currentTeacher.isCollegeLeader()) {
                teacher.setUniversityId(currentTeacher.getUniversityId());
                teacher.setCollegeId(currentTeacher.getCollegeId());
            } else if (currentTeacher.isDepartmentLeader()) {
                teacher.setUniversityId(currentTeacher.getUniversityId());
                teacher.setCollegeId(currentTeacher.getCollegeId());
                teacher.setDepartmentId(currentTeacher.getDepartmentId());
            } else if (currentTeacher.isSpecialityLeader()) {
                teacher.setUniversityId(currentTeacher.getUniversityId());
                teacher.setCollegeId(currentTeacher.getCollegeId());
                teacher.setDepartmentId(currentTeacher.getDepartmentId());
                teacher.setSpecialityId(currentTeacher.getSpecialityId());
            }
        }
    }
}
