package cn.edu.swufe.lawschool.internship.user.service.impl;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import cn.edu.swufe.lawschool.internship.web.cookie.CookieManager;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;
import com.xavier.commons.util.encrypt.HashUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2015年11月09
 * <p>Title:       登录服务</p>
 * <p>Description: 登录服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Logger
    private Log log;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TutorService tutorService;

    public void saveLoginInfo (UserInfo userInfo) {
        CookieManager.setCookie(getUserIdKey(), AESUtil.encrypt(String.valueOf(userInfo.getId())));
        CookieManager.setCookie(getUserTypeKey(), AESUtil.encrypt(String.valueOf(userInfo.getUserType().getCode())));
        String name = userInfo.getName();
        if (StringUtil.isEmpty(name)) {
            name = userInfo.getUserName();
        }
        CookieManager.setCookie(getNameKey(), AESUtil.encrypt(name));
    }

    public UserInfo getLoginUserInfo () {
        try{
            UserInfo userInfo = getLoginInfoFromContext();
            if (userInfo != null) {
                return userInfo;
            }
            String userId = CookieManager.getCookie(getUserIdKey());
            String userType = CookieManager.getCookie(getUserTypeKey());
            String name = CookieManager.getCookie(getNameKey());
            if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(userType) || StringUtil.isEmpty(name)) {
                return null;
            }
            userInfo = new UserInfo();
            userInfo.setId(Long.parseLong(AESUtil.decrypt(userId)));
            UserType type = UserType.parse(Integer.parseInt(AESUtil.decrypt(userType)));
            userInfo.setUserType(type);
            userInfo.setName(AESUtil.decrypt(name));
            return userInfo;
        } catch (Exception e) {
            log.error("get current user info failed", e);
            return null;
        }
    }

    public Student getStudentInfo () {
        HttpServletRequest request = ServletContext.getRequest();
        if (request == null) {
            return null;
        }
        Object student = request.getAttribute("student");
        if (student != null) {
            return (Student) student;
        }
        UserInfo loginInfo = getLoginUserInfo();
        if (loginInfo.isStudent()) {
            return studentService.getStudentByUserId(loginInfo.getId());
        } else {
            return null;
        }
    }

    public Teacher getTeacherInfo () {
        HttpServletRequest request = ServletContext.getRequest();
        if (request == null) {
            return null;
        }
        Object teacher = request.getAttribute("teacher");
        if (teacher != null) {
            return (Teacher) teacher;
        }
        UserInfo loginInfo = getLoginUserInfo();
        if (loginInfo.isTeacher()) {
            return teacherService.getTeacherByUserId(loginInfo.getId());
        } else {
            return null;
        }
    }

    public Tutor getTutorInfo () {
        HttpServletRequest request = ServletContext.getRequest();
        if (request == null) {
            return null;
        }
        Object tutor = request.getAttribute("tutor");
        if (tutor != null) {
            return (Tutor) tutor;
        }
        UserInfo loginInfo = getLoginUserInfo();
        if (loginInfo.isTutor()) {
            return tutorService.getTutorByUserId(loginInfo.getId());
        } else {
            return null;
        }
    }

    public void logout () {
        CookieManager.setCookie(getUserIdKey(), "");
        CookieManager.setCookie(getUserTypeKey(), "");
        CookieManager.setCookie(getNameKey(), "");
    }

    public boolean isLogin () {
        return getLoginUserInfo() != null;
    }

    private UserInfo getLoginInfoFromContext () {
        HttpServletRequest request = ServletContext.getRequest();
        if (request == null) {
            return null;
        }
        Object loginInfo = request.getAttribute("logInfo");
        if (loginInfo == null) {
            return null;
        }
        return (UserInfo) loginInfo;
    }

    private String getUserIdKey () {
        return HashUtil.md5Hash("user.id");
    }

    private String getUserTypeKey () {
        return HashUtil.md5Hash("user.type");
    }

    private String getNameKey () {
        return HashUtil.md5Hash("user.name");
    }
}
