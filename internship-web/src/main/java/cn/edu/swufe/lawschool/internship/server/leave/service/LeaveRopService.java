package cn.edu.swufe.lawschool.internship.server.leave.service;

import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveStatus;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveType;
import cn.edu.swufe.lawschool.internship.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.server.annotation.RopServiceUserAccess;
import cn.edu.swufe.lawschool.internship.server.leave.request.LeaveApplyRequest;
import cn.edu.swufe.lawschool.internship.server.leave.request.LeaveSearchParamRequest;
import cn.edu.swufe.lawschool.internship.server.service.RopLoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import com.xavier.rop.annotation.NeedInSessionType;
import com.xavier.rop.annotation.ServiceMethod;
import com.xavier.rop.annotation.ServiceMethodBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017年05月12
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@ServiceMethodBean
public class LeaveRopService {

    @Autowired
    RopLoginService ropLoginService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    FlowService flowService;

    @ServiceMethod(method = "student.leave.apply", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess(UserType.STUDENT_CODE)
    public Boolean leave (LeaveApplyRequest request) {
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setBeginDay(DateUtil.parseForMills(request.getBeginDay()));
        leaveRecord.setBeginTimeInterval(TimeInterval.parse(request.getBeginTimerInterval()));
        leaveRecord.setEndDay(DateUtil.parseForMills(request.getEndDay()));
        leaveRecord.setEndTimeInterval(TimeInterval.parse(request.getEndTimerInterval()));
        leaveRecord.setLeaveType(LeaveType.parse(request.getLeaveType()));
        leaveRecord.setReason(request.getReason());
        UserInfo loginInfo = ropLoginService.getLoginUserInfo();
        leaveRecord.setUserId(loginInfo.getId());
        leaveRecord.setCreatedBy(loginInfo.getName());
        leaveService.studentAskForLeave(leaveRecord);
        return true;
    }

    @ServiceMethod(method = "student.leave.list", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess(UserType.STUDENT_CODE)
    public Object leave (LeaveSearchParamRequest request) {
        UserInfo loginInfo = ropLoginService.getLoginUserInfo();
        Page page = PageUtil.buildPage(request.getCurrentPage(), request.getPageSize(), "id.DESC");
        LeaveRecord leaveRecord = new LeaveRecord();
        if (request.getLeaveType() != null) {
            leaveRecord.setLeaveType(LeaveType.parse(request.getLeaveType()));
        }
        if (request.getLeaveStatus() != null) {
            leaveRecord.setLeaveStatus(LeaveStatus.parse(request.getLeaveStatus()));
        }
        leaveRecord.setUserId(loginInfo.getId());
        List<LeaveRecord> leaveRecordList = leaveService.getStudentLeaveRecord(leaveRecord, page);
        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("leaveRecord", leaveRecordList);
        data.put("page", page);
        return data;
    }

    @ServiceMethod(method = "student.leave.detail.flow", version = "1.0", needInSession = NeedInSessionType.YES)
    @RopServiceUserAccess({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public List<FlowRecord> getLeaveFlow (LeaveSearchParamRequest request) {
        List<FlowRecord> flowRecords = flowService.getFlowRecordByTargetId(request.getDesId(), FlowType.LEAVE);
        Collections.sort(flowRecords, (t0, t1) -> {
            return t0.getFlowOrder() - t1.getFlowOrder();
        });
        return flowRecords;
    }
}
