package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.tutor.model.TutorType;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2016年11月22
 * <p>Title:       导师controller</p>
 * <p>Description: </p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppTutorController")
@RequestMapping(value = "/webApp/tutor")
public class TutorController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TutorService tutorService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/detail/{tutorDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission()
    public String getTutorInfo (ModelMap modelMap, String returnUrl, @PathVariable String tutorDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = tutorService.getTutorByDesId(tutorDesId);
        authorizationService.checkTutorAuthorization(loginService.getLoginUserInfo(), tutor);
        modelMap.put("tutor", tutor);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/tutor/tutorDetail";
    }

    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.COMPANY_TUTOR_CODE)
    public String tutorMainPage (ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
        modelMap.put("tutor", tutor);
        modelMap.put("isLeader", tutor.isLeader());
        return "webApp/tutor/tutorMain";
    }

    @RequestMapping(value = "/info.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.COMPANY_TUTOR_CODE)
    public String tutorInfoPage (ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
        modelMap.put("tutor", tutor);
        modelMap.put("sexes", SexType.getValues());
        return "webApp/tutor/tutorInfo";
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE })
    public String getTutor (ModelMap modelMap, Tutor tutor, UserInfo userInfo, String action,
            String studentDesId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (StringUtil.isNotBlank(studentDesId)) {
            modelMap.put("student", studentService.getStudentByDesId(studentDesId));
        }
        tutor.setUserInfo(userInfo);
        modelMap.put("action", action);
        modelMap.put("tutor", tutor);
        modelMap.put("roleTypes", TutorType.getValues());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/tutor/tutorList";
    }
}
