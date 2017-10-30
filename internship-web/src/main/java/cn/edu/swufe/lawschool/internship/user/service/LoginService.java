package cn.edu.swufe.lawschool.internship.user.service;

import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;

/**
 * Created on 2015年11月09
 * <p>Title:       登录服务</p>
 * <p>Description: 登录服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface LoginService {

    /**
     * Created on 2015年11月09
     * <p>Description: 保存登录信息</p>
     * @return
     * @author 庞先海
     */
    void saveLoginInfo(UserInfo userInfo);

    /**
     * 获取当前登录用户的用户信息
     * @return
     */
    UserInfo getLoginUserInfo();

    /**
     * 如果当前用户是学生 获取学生信息
     * @return
     */
    Student getStudentInfo();

    /**
     * 如果当前用户是带队老师 获取带队老师信息
     * @return
     */
    Teacher getTeacherInfo();

    /**
     * 如果当前用户是导师 获取导师信息
     * @return
     */
    Tutor getTutorInfo();

    /**
     * Created on 2015年11月09
     * <p>Description: 判断是否登录</p>
     * @return true/false
     * @author 庞先海
     */
    boolean isLogin();

    /**
     * Created on 2015年11月11
     * <p>Description: 登出</p>
     * @return
     * @author 庞先海
     */
    void logout();
}
