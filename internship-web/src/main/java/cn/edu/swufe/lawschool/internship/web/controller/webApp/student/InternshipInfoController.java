package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2016年11月24
 * <p>Title:       web app 学生实习信息</p>
 * <p>Description: web app 学生实习信息 controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppInternshipInfoController")
@RequestMapping(value = "/webApp/student/internship")
public class InternshipInfoController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/info.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    public String internshipStatistics (ModelMap modelMap, String studentDesId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        OperateAuthorization operateAuthorization;
        if (!loginInfo.isStudent()) {
            modelMap.put("studentDesId", studentDesId);
            Student student = studentService.getStudentByDesId(studentDesId);
            operateAuthorization = authorizationService.getOperateAuthorization(loginInfo, student);
        } else {
            operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                                                                                loginService.getStudentInfo());
        }
        modelMap.put("canCreateReport", operateAuthorization.isCanCreateReport());
        modelMap.put("canSendAssessment", operateAuthorization.isCanSendAssessment());
        modelMap.put("canSendEvaluation", operateAuthorization.isCanSendEvaluation());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/internship/internshipInfo";
    }

    /**
     * 学生的实习信息列表
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String internshipList (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/internship/internshipList";
    }
}
