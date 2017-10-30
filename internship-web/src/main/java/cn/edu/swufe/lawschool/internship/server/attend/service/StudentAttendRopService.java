package cn.edu.swufe.lawschool.internship.server.attend.service;

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
import cn.edu.swufe.lawschool.internship.server.annotation.RopServiceUserAccess;
import cn.edu.swufe.lawschool.internship.server.attend.request.AttendDeleteRequest;
import cn.edu.swufe.lawschool.internship.server.attend.request.AttendExamineRequest;
import cn.edu.swufe.lawschool.internship.server.service.RopLoginService;
import cn.edu.swufe.lawschool.internship.server.attend.request.AttendParamRequest;
import cn.edu.swufe.lawschool.internship.server.attend.request.StudentAttendRequest;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.rop.annotation.NeedInSessionType;
import com.xavier.rop.annotation.ServiceMethod;
import com.xavier.rop.annotation.ServiceMethodBean;
import com.xavier.rop.request.SimpleRopRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2016年10月19
 * <p>Title:       签到服务</p>
 * <p>Description: 签到服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@ServiceMethodBean
public class StudentAttendRopService {

    @Autowired
    RopLoginService ropLoginService;

    @Autowired
    AttendService attendService;

    @Autowired
    FlowService flowService;

    @ServiceMethod(method = "student.attend.list", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getStudentAttendList (AttendParamRequest request) {
        AttendRecordParam attendRecordParam = new AttendRecordParam();
        attendRecordParam.setAttendBeginDay(DateUtil.parseForMills(request.getBeginTime()));
        attendRecordParam.setAttendEndDay(DateUtil.parseForMills(request.getEndTime()));
        attendRecordParam.setAttendStatus(AttendStatus.parse(request.getAttendStatusCode()));
        UserInfo loginInfo = ropLoginService.getLoginUserInfo();
        if (loginInfo.isStudent()) {
            attendRecordParam.setUserId(loginInfo.getId());
        }

        Page page = PageUtil.buildPage(request.getCurrentPage(), request.getPageSize(), "attend_day.DESC");
        List<AttendRecord> attendRecordList = attendService.getAttendRecord(attendRecordParam, page);
        if (CollectionUtil.isNotEmpty(attendRecordList)) {
            for (AttendRecord _attRecord : attendRecordList) {
                _attRecord.setFlowRecords(flowService.getFlowRecordByTargetId(_attRecord.getId(), FlowType.ATTEND));
            }
        }

        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("attendRecordList", attendRecordList);
        data.put("page", page);
        return data;
    }

    @ServiceMethod(method = "student.attend", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess(UserType.STUDENT_CODE)
    public Boolean studentAttend (StudentAttendRequest attendRequest) {
        if (attendRequest.getTimeIntervalCode() == null) {
            throw new InternshipException(AttendError.ADD_ATTEND_TIME_INTERVAL_ERROR);
        }
        TimeInterval timeInterval = TimeInterval.parse(attendRequest.getTimeIntervalCode());
        if (timeInterval == null) {
            throw new InternshipException(AttendError.ADD_ATTEND_TIME_INTERVAL_ERROR);
        }
        UserInfo login = ropLoginService.getLoginUserInfo();
        if (StringUtil.isEmpty(attendRequest.getAttendDay())) {
            attendService.studentAttend(login.getId(), DateUtil.currentMilliseconds(), timeInterval);
        } else {
            Long attendDayLong = DateUtil.parseForMills(attendRequest.getAttendDay());
            if (attendDayLong == null) {
                throw new InternshipException(AttendError.ADD_ATTEND_DAY_ERROR);
            }
            attendService.studentAttend(login.getId(), attendDayLong, timeInterval);
        }
        return true;
    }

    @ServiceMethod(method = "student.attend.examine", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess(UserType.COMPANY_TUTOR_CODE)
    public Boolean studentAttendExamine (AttendExamineRequest request) {
        UserInfo loginInfo = ropLoginService.getLoginUserInfo();
        attendService.examineStudentAttend(request.getAttendRecordDestId(), loginInfo.getId(), AttendStatus.parse(request.getAttendStatusCode()), loginInfo.getName());
        return true;
    }

    @ServiceMethod(method = "student.attend.delete", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess(UserType.STUDENT_CODE)
    public Boolean deleteAttendExamine (AttendDeleteRequest request) {
        UserInfo loginInfo = ropLoginService.getLoginUserInfo();
        attendService.deleteAttendRecord(request.getAttendRecordDestId(), loginInfo.getId());
        return true;
    }
}
