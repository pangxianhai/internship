package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveType;
import cn.edu.swufe.lawschool.internship.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016年11月05
 * <p>Title:       webApp学生请假controller</p>
 * <p>Description: webApp学生请假controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppStudentLeaveController")
@RequestMapping(value = "/webApp/student/leave")
public class StudentLeaveController {

    @Autowired
    LoginService loginService;

    @Autowired
    LeaveService leaveService;

    @RequestMapping(value = "/leave.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentLeave (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("timeInterval", TimeInterval.getValues());
        modelMap.put("leaveTypes", LeaveType.getValues());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/leave/leave";
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentLeaveList (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("leaveTypes", LeaveType.getValues());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/leave/leaveList";
    }

    @RequestMapping(value = "/detail/{desId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentLeaveDetail (ModelMap modelMap, @PathVariable String desId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        LeaveRecord leaveRecord = leaveService.getStudentLeaveRecordByDesId(desId);
        List<FlowStatus> examineStatus = new ArrayList<FlowStatus>();
        examineStatus.add(FlowStatus.PASS);
        examineStatus.add(FlowStatus.NO_PASS);
        modelMap.put("leaveRecord", leaveRecord);
        modelMap.put("examineStatus", examineStatus);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/leave/leaveDetail";
    }

}
