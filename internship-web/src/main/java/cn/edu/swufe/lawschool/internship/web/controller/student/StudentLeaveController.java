package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.LeaveError;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveType;
import cn.edu.swufe.lawschool.internship.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created on 2015年11月22
 * <p>Title:       学生请假</p>
 * <p>Description: 学生请假 controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/leave")
public class StudentLeaveController {

    @Autowired
    LoginService loginService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TutorService tutorService;

    @Autowired
    FlowService flowService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentLeaveList (ModelMap modelMap) {
        modelMap.put("timeInterval", TimeInterval.getValues());
        modelMap.put("leaveTypes", LeaveType.getValues());
        return "student/leaveList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object studentLeaveListData (LeaveRecord leaveRecord, Student student,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        leaveRecord.setStudent(student);
        authorizationService.resetStudentCondition(loginInfo, leaveRecord.getStudent());
        Map<String, Object> data = new HashMap<String, Object>(2);
        Page page = new Page(currentPage, 10, Order.formString("id.DESC"));
        List<LeaveRecord> leaveRecords = leaveService.getStudentLeaveRecord(leaveRecord, page);
        data.put("leaveRecords", leaveRecords);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/ask_for_leave.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public Object studentAskForLeave (String beginDay, Integer beginTime, String endDay, Integer endTime,
            Integer leaveType, String reason) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setBeginDay(DateUtil.parseForMills(beginDay));
        leaveRecord.setBeginTimeInterval(TimeInterval.parse(beginTime.intValue()));
        leaveRecord.setEndDay(DateUtil.parseForMills(endDay));
        leaveRecord.setEndTimeInterval(TimeInterval.parse(endTime.intValue()));
        leaveRecord.setReason(reason);
        leaveRecord.setLeaveType(LeaveType.parse(leaveType.intValue()));
        leaveRecord.setUserId(loginInfo.getId());
        leaveRecord.setCreatedBy(loginInfo.getName());
        leaveService.studentAskForLeave(leaveRecord);
        return true;
    }

    @RequestMapping(value = "/detail/{desId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentLeaveDetail (ModelMap modelMap, @PathVariable String desId) {
        LeaveRecord leaveRecord = leaveService.getStudentLeaveRecordByDesId(desId);
        if (leaveRecord == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        modelMap.put("leaveRecord", leaveRecord);
        modelMap.put("examineStatus", Arrays.asList(FlowStatus.PASS, FlowStatus.NO_PASS));
        return "student/leaveDetail";
    }

    @RequestMapping(value = "/getFlow.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public  List<FlowRecord> studentLeaveFlowInfo (Long leaveId) {
        List<FlowRecord> flowRecords = flowService.getFlowRecordByTargetId(leaveId, FlowType.LEAVE);
        Collections.sort(flowRecords, (t0, t1) -> {
            return t0.getFlowOrder() - t1.getFlowOrder();
        });
        return flowRecords;
    }

    @RequestMapping(value = "/examine.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object tutorExamine (Long leaveId, Integer statusCode) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTutor()) {
            leaveService.tutorExamine(leaveId, loginInfo.getId(), FlowStatus.parse(statusCode));
        } else if (loginInfo.isTeacher()) {
            leaveService.teacherExamine(leaveId, loginInfo.getId(), FlowStatus.parse(statusCode));
        } else {
            throw new InternshipException(LeaveError.NO_ACCESS);
        }
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Object deleteLeave (String leaveDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        leaveService.deleteLeaveRecord(leaveDesId, loginInfo.getId());
        return true;
    }
}
