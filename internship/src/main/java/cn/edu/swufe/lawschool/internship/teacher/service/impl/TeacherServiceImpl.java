package cn.edu.swufe.lawschool.internship.teacher.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.TeacherError;
import cn.edu.swufe.lawschool.internship.exception.UniversityDepartmentError;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.service.UniversityDepartmentService;
import cn.edu.swufe.lawschool.internship.teacher.mapper.TeacherMapper;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.model.TeacherType;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
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
 * <p>Title:       带队老师服务</p>
 * <p>Description: 带队老师服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    UserService userService;

    @Autowired
    UniversityDepartmentService universityDepartmentService;

    public Long addTeacher (Teacher teacher) {

        List<Long> teacherDepartmentList = Arrays.asList(teacher.getUniversityId(), teacher.getCollegeId(),
                                                         teacher.getDepartmentId(),
                                                         teacher.getSpecialityId());
        universityDepartmentService.checkDepartmentOrganization(teacherDepartmentList);
        checkTeacherLeader(teacher);

        UserInfo userInfo = teacher.getUserInfo();
        userInfo.setUserType(UserType.TEACHER);
        userInfo.setCreatedBy(teacher.getCreatedBy());
        Long userId = userService.addUser(userInfo);

        teacher.setStatus(Status.VALID);
        teacher.setUserId(userId);
        teacher.setName(userInfo.getName());
        teacher.setUpdatedBy(teacher.getCreatedBy());
        teacher.setCreatedTime(DateUtil.currentMilliseconds());
        teacher.setUpdatedTime(teacher.getCreatedTime());
        int count = teacherMapper.insert(teacher);
        if (count < 1) {
            throw new InternshipException(TeacherError.ADD_TEACHER_ERROR);
        }
        return teacher.getId();
    }

    public List<Teacher> getTeacher (Teacher teacher, Page page) {
        List<Teacher> teacherList = teacherMapper.select(teacher, page);
        teacherList.forEach((_teacher) -> {
            buildUniversityInfoOfTeacher(_teacher);
        });
        return teacherList;
    }

    public Teacher getTeacherById (Long teacherId) {
        if (teacherId == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        return selectOne(teacher);
    }

    public Teacher getTeacherByDesId (String desId) {
        if (StringUtil.isEmpty(desId)) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setDesId(desId);
        return selectOne(teacher);
    }

    public Teacher getLeaderByTeacherOrganization (TeacherType teacherType, String teacherDesId) {
        Teacher teacher = this.getTeacherByDesId(teacherDesId);
        return getLeaderByTeacherOrganization(teacherType, teacher);
    }

    public void minusStudent (Long teacherId, String operator) {
        Teacher teacher = getTeacherById(teacherId);
        Teacher _teacher = new Teacher();
        _teacher.setId(teacher.getId());
        Integer num = teacher.getStudentNumber() - 1;
        if (num < 0) {
            num = 0;
        }
        _teacher.setStudentNumber(num);
        updateTeacher(_teacher, operator);
    }

    public void addStudent (Long teacherId, String operator) {
        Teacher teacher = getTeacherById(teacherId);
        Teacher _teacher = new Teacher();
        _teacher.setId(teacher.getId());
        Integer sum = teacher.getStudentNumber();
        if (sum == null) {
            sum = 0;
        }
        _teacher.setStudentNumber(sum + 1);
        updateTeacher(_teacher, operator);
    }

    public Teacher getTeacherByUserId (Long userId) {
        if (userId == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setUserId(userId);
        return selectOne(teacher);
    }

    public void updateTeacherType (Long teacherId, TeacherType teacherType, String operator) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setTeacherType(teacherType);
        updateTeacher(teacher, operator);
    }

    public void setTeacherLeader (String teacherDesId, TeacherType teacherType, String operator) {
        Teacher teacher = this.getTeacherByDesId(teacherDesId);
        if (teacher == null) {
            throw new InternshipException(TeacherError.TEACHER_NOT_EXIST);
        }
        if (teacherType == null || teacherType.equals(TeacherType.GENERAL)) {
            throw new InternshipException(TeacherError.TEACHER_TYPE_ERROR);
        }
        Teacher formerLeader = this.getLeaderByTeacherOrganization(teacherType, teacher);
        if (formerLeader != null) {
            //原领导设置成普通带队老师
            this.updateTeacherType(formerLeader.getId(), TeacherType.GENERAL, operator);
        }
        this.updateTeacherType(teacher.getId(), teacherType, operator);
    }

    public void updateTeacher (Teacher teacher, String operator) {
        if (teacher.getId() == null) {
            throw new InternshipException(TeacherError.UPDATE_TEACHER_ID_EMPTY);
        }
        List<Long> teacherDepartmentList = Arrays.asList(teacher.getUniversityId(), teacher.getCollegeId(),
                                                         teacher.getDepartmentId(),
                                                         teacher.getSpecialityId());
        universityDepartmentService.checkDepartmentOrganization(teacherDepartmentList);
        teacher.setUpdatedBy(operator);
        teacher.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = teacherMapper.update(teacher);
        if (count <= 0) {
            throw new InternshipException(TeacherError.UPDATE_TEACHER_ERROR);
        }
    }

    public void delete (String destId) {
        if (StringUtil.isEmpty(destId)) {
            throw new InternshipException(TeacherError.UPDATE_TEACHER_ID_EMPTY);
        }
        Teacher teacher = this.getTeacherByDesId(destId);
        if (teacher == null) {
            throw new InternshipException(TeacherError.TEACHER_NOT_EXIST);
        }
        if (teacher.getStudentNumber() != null && teacher.getStudentNumber().intValue() > 0) {
            throw new InternshipException(TeacherError.DELETE_TEACHER_ERROR_BY_HAS_STUDENT);
        }
        this.teacherMapper.delete(teacher.getId());
        userService.delete(teacher.getUserId());
    }

    private void checkTeacherLeader (Teacher teacher) {
        if (teacher.isLeader()) {
            Teacher leader = getLeaderByTeacherOrganization(teacher.getTeacherType(), teacher);
            if (leader != null) {
                throw new InternshipException(TeacherError.HAS_SPECIALITY_LEADER);
            }
            if (teacher.isDepartmentLeader()) {
                if (teacher.getDepartmentId() == null) {
                    throw new InternshipException(TeacherError.ADD_TEACHER_ERROR);
                }
            } else if (teacher.isSpecialityLeader()) {
                if (teacher.getSpecialityId() == null) {
                    throw new InternshipException(TeacherError.ADD_TEACHER_ERROR);
                }
            }
        }
    }

    private Teacher selectOne (Teacher teacher) {
        List<Teacher> teachers = teacherMapper.select(teacher);
        if (CollectionUtil.isNotEmpty(teachers)) {
            return buildUniversityInfoOfTeacher(teachers.get(0));
        } else {
            return null;
        }
    }

    private Teacher buildUniversityInfoOfTeacher (Teacher teacher) {
        if (teacher == null) {
            return teacher;
        }
        teacher.setUniversity(
                universityDepartmentService.getUniversityDepartmentById(teacher.getUniversityId()));
        teacher.setCollege(universityDepartmentService.getUniversityDepartmentById(teacher.getCollegeId()));
        teacher.setDepartment(
                universityDepartmentService.getUniversityDepartmentById(teacher.getDepartmentId()));
        teacher.setSpeciality(
                universityDepartmentService.getUniversityDepartmentById(teacher.getSpecialityId()));
        return teacher;
    }

    private Teacher getLeaderByTeacherOrganization (TeacherType teacherType, Teacher currentTeacher) {
        Teacher teacher = new Teacher();
        teacher.setTeacherType(teacherType);
        if (teacherType.equals(TeacherType.UNIVERSITY_LEADER)) {
            teacher.setUniversityId(currentTeacher.getUniversityId());
        } else if (teacherType.equals(TeacherType.COLLEGE_LEADER)) {
            teacher.setCollegeId(currentTeacher.getCollegeId());
        } else if (teacherType.equals(TeacherType.DEPARTMENT_LEADER)) {
            if (currentTeacher.getDepartmentId() == null) {
                throw new InternshipException(TeacherError.TEACHER_IS_NOT_IN_DEPARTMENT);
            } else {
                teacher.setDepartmentId(currentTeacher.getDepartmentId());
            }

        } else if (teacherType.equals(TeacherType.SPECIALITY_LEADER)) {
            if (currentTeacher.getSpecialityId() == null) {
                throw new InternshipException(TeacherError.TEACHER_IS_NOT_IN_DEPARTMENT);
            } else {
                teacher.setSpecialityId(currentTeacher.getSpecialityId());
            }
        } else {
            return null;
        }
        return selectOne(teacher);
    }
}
