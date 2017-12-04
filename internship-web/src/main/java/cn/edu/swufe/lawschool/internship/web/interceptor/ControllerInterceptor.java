package cn.edu.swufe.lawschool.internship.web.interceptor;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.EncodeUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created on 2015年09月24
 * <p>Title:       controller拦截器</p>
 * <p>Description: 拦截controller：初始化ServletContext</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    @Logger
    private Log log;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private StudentService studentService;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ServletContext.init(request, response);
        UserInfo userInfo = loginService.getLoginUserInfo();
        if (userInfo != null) {
            request.setAttribute("logInfo", userInfo);
            if (userInfo.isTeacher()) {
                Teacher teacher = teacherService.getTeacherByUserId(userInfo.getId());
                request.setAttribute("teacher", teacher);
                userInfo.setLeader(teacher.isLeader());
                request.setAttribute("isLeader", teacher.isLeader());
            } else if (userInfo.isTutor()) {
                Tutor tutor = tutorService.getTutorByUserId(userInfo.getId());
                request.setAttribute("tutor", tutor);
                userInfo.setLeader(tutor.isLeader());
                request.setAttribute("isLeader", tutor.isLeader());
            } else if (userInfo.isStudent()) {
                Student student = studentService.getStudentByUserId(userInfo.getId());
                request.setAttribute("student", student);
            }
        }
        loginHandel(handler, userInfo);
        String locationURL = request.getRequestURL().toString();
        if (StringUtil.isNotBlank(request.getQueryString())) {
            locationURL = locationURL + "?" + request.getQueryString();
        }
        request.setAttribute("locationURL", EncodeUtil.urlDecode(locationURL));
        return true;
    }

    public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        ServletContext.clean();
    }

    private void loginHandel (Object handler, UserInfo userInfo) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginAccessPermission loginAccess = method.getAnnotation(LoginAccessPermission.class);
            if (loginAccess != null) {
                if (userInfo == null) {
                    throw new InternshipException(ErrorType.NO_LOGIN);
                }
                int[] types = loginAccess.value();
                //只检测是否需要登陆
                if (types.length == 1 && types[0] == UserType.DEFAULT) {
                    return;
                }
                //针对具体用户类型
                for (int t : types) {
                    if (t == userInfo.getUserType().getCode().intValue()) {
                        return;
                    }
                }
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
    }
}