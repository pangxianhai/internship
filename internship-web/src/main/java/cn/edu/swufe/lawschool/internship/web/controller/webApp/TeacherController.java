package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.teacher.model.TeacherType;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2016年11月22
 * <p>Title:       带队老师controller</p>
 * <p>Description: </p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webTeacherController")
@RequestMapping(value = "/webApp/teacher")
public class TeacherController {

    @Autowired
    LoginService loginService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/detail/{teacherDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission
    public String getTeacherInfo (ModelMap modelMap, String returnUrl, @PathVariable String teacherDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Teacher teacher = teacherService.getTeacherByDesId(teacherDesId);
        authorizationService.checkTeacherAuthorization(loginService.getLoginUserInfo(), teacher);
        modelMap.put("teacher", teacher);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/teacher/teacherDetail";
    }

    /**
     * 老师首页
     * @return
     */
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.TEACHER_CODE)
    public String teacherMain () {
        return "webApp/teacher/teacherMain";
    }

    /**
     * 老师个人中心
     * @return
     */
    @RequestMapping(value = "/info.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.TEACHER_CODE)
    public String teacherInfo () {
        return "webApp/teacher/teacherInfo";
    }

    /**
     * 老师列表页
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String getTeacher (ModelMap modelMap, String action, String studentDesId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isLeader() && !loginInfo.isSysadmin()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("action", action);
        modelMap.put("studentDesId", studentDesId);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/teacher/teacherList";
    }
}
