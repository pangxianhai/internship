package cn.edu.swufe.lawschool.internship.user.service;

import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;

/**
 * Created on 2015年12月02
 * <p>Title:       权限服务</p>
 * <p>Description: 权限服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface AuthorizationService {

    /**
     * Created on 2015年12月02
     * <p>Description: 检测当前用户是否对该学生有权限</p>
     * @return
     * @author 庞先海
     */
    void checkStudentAuthorization (UserInfo loginInfo, Long studentId);

    /**
     * Created on 2015年12月03
     * <p>Description: 检测当前用户是否对该学生有权限</p>
     * @return
     * @author 庞先海
     */
    void checkStudentAuthorization (UserInfo loginInfo, Student student);

    /**
     * Created on 2015年12月02
     * <p>Description: 检测当前用户是否对该带队老师有权限</p>
     * @return
     * @author 庞先海
     */
    void checkTeacherAuthorization (UserInfo loginInfo, Teacher teacher);

    /**
     * Created on 2015年12月02
     * <p>Description: 检测当前用户是否对该带队老师有权限</p>
     * @return
     * @author 庞先海
     */
    void checkTeacherAuthorization (UserInfo loginInfo, Long teacherId);

    /**
     * Created on 2015年12月02
     * <p>Description: 检测当前用户是否对该导师有权限</p>
     * @return
     * @author 庞先海
     */
    void checkTutorAuthorization (UserInfo loginInfo, Tutor tutor);

    /**
     * Created on 2015年12月02
     * <p>Description: 检测当前用户是否对该导师有权限</p>
     * @return
     * @author 庞先海
     */
    void checkTutorAuthorization (UserInfo loginInfo, Long tutorId);

    /**
     * Created on 2017年02月10
     * <p>Description: 获取当前用户对该学生的操作权限</p>
     * @return
     * @author 庞先海
     */
    OperateAuthorization getOperateAuthorization (UserInfo loginInfo, Student student);

    /**
     * 当前用户对该学校组织是否有权限
     * @param loginInfo
     * @param department
     */
    void checkUniversityDepartmentAuthorization (UserInfo loginInfo, UniversityDepartment department);

    /**
     * 根据当前用户 重置学生条件
     * @param loginInfo
     * @param student
     */
    void resetStudentCondition (UserInfo loginInfo, Student student);

    /**
     * 根据当前用户 重置老师条件
     * @param loginInfo
     * @param teacher
     */
    void resetTeacherCondition (UserInfo loginInfo, Teacher teacher);

}
