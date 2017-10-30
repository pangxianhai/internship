package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.attend.model.AttendStatus;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.exception.AttendError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created on 2015年11月20
 * <p>Title:       学生签到controller</p>
 * <p>Description: 学生签到controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/student/attend")
public class StudentAttendController {
    @Autowired
    AttendService attendService;

    @Autowired
    LoginService loginService;

    @Autowired
    FlowService flowService;

    @Autowired
    TutorService tutorService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentAttendList (ModelMap modelMap, AttendRecordParam attendRecordParam, Student student,
            String attendBeginDayStr, String attendEndDayStr, Integer attendStatusCode) {
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
        modelMap.put("attendRecordParam", attendRecordParam);
        modelMap.put("attendBeginDayStr", attendBeginDayStr);
        modelMap.put("attendEndDayStr", attendEndDayStr);
        modelMap.put("attendStatusCode", attendStatusCode);
        if (loginInfo.isStudent()) {
            Student _student = studentService.getStudentByUserId(loginInfo.getId());
            student.setStudentNumber(_student.getStudentNumber());
            student.setName(_student.getName());
        }
        modelMap.put("student", student);
        return "student/attend";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object studentAttendListData (AttendRecordParam attendRecord, String attendBeginDayStr,
            Integer attendStatusCode, String attendEndDayStr,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        authorizationService.resetStudentCondition(loginInfo, attendRecord.getStudent());
        attendRecord.setAttendBeginDay(DateUtil.parseForMills(attendBeginDayStr));
        attendRecord.setAttendEndDay(DateUtil.parseForMills(attendEndDayStr));
        attendRecord.setAttendStatus(AttendStatus.parse(attendStatusCode));
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("attend_day.DESC"));
        List<AttendRecord> attendRecords = attendService.getAttendRecord(attendRecord, page);
        if (CollectionUtil.isNotEmpty(attendRecords)) {
            for (AttendRecord _attRecord : attendRecords) {
                _attRecord.setFlowRecords(
                        flowService.getFlowRecordByTargetId(_attRecord.getId(), FlowType.ATTEND));
            }
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("page", page);
        data.put("attendRecords", attendRecords);
        return data;
    }

    @RequestMapping(value = "/attend.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Object studentAttend (String attendDay, Integer timeIntervalCode) {
        if (timeIntervalCode == null) {
            throw new InternshipException(AttendError.ADD_ATTEND_TIME_INTERVAL_ERROR);
        }
        TimeInterval timeInterval = TimeInterval.parse(timeIntervalCode);
        if (timeInterval == null) {
            throw new InternshipException(AttendError.ADD_ATTEND_TIME_INTERVAL_ERROR);
        }
        UserInfo login = loginService.getLoginUserInfo();
        if (StringUtil.isEmpty(attendDay)) {
            attendService.studentAttend(login.getId(), DateUtil.currentMilliseconds(), timeInterval);
        } else {
            Long attendDayLong = DateUtil.parseForMills(attendDay);
            if (attendDayLong == null) {
                throw new InternshipException(AttendError.ADD_ATTEND_DAY_ERROR);
            }
            attendService.studentAttend(login.getId(), attendDayLong, timeInterval);
        }
        return true;
    }

    @RequestMapping(value = "/examine.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object studentAttendExamine (String attendRecordDestId, Integer attendStatusCode) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        attendService.examineStudentAttend(attendRecordDestId, loginInfo.getId(),
                                           AttendStatus.parse(attendStatusCode), loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Object deleteAttendExamine (String attendRecordDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        attendService.deleteAttendRecord(attendRecordDesId, loginInfo.getId());
        return true;
    }
}
