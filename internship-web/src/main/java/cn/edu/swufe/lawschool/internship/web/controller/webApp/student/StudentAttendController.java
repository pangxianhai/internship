package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.attend.model.AttendStatus;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016年10月26
 * <p>Title:       webApp学生签到controller</p>
 * <p>Description: webApp学生签到controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppStudentAttendController")
@RequestMapping(value = "/webApp/student/attend")
public class StudentAttendController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/attend.htm")
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentAttend (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("am", TimeInterval.AM);
        modelMap.put("pm", TimeInterval.PM);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/attend/attend";
    }

    @RequestMapping(value = "/list.htm")
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentAttendList (ModelMap modelMap, AttendRecordParam attendRecord, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("am", TimeInterval.AM);
        modelMap.put("pm", TimeInterval.PM);
        List<AttendStatus> attendStatus = new ArrayList<AttendStatus>();
        attendStatus.add(AttendStatus.ATTENDED);
        attendStatus.add(AttendStatus.LATE);
        attendStatus.add(AttendStatus.EARLY);
        attendStatus.add(AttendStatus.ABSENT);
        attendStatus.add(AttendStatus.LEAVE);
        modelMap.put("attendStatus", attendStatus);
        modelMap.put("selectAttendStatus", AttendStatus.getValues());
        modelMap.put("attendRecord", attendRecord);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/attend/attendList";
    }
}
