package cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.impl;

import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecordSearchParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.ServerService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

import java.util.List;

/**
 * Created on 2017年05月12
 * <p>Title:       请假服务</p>
 * <p>Description: 请假服务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveServiceImpl implements LeaveService {

    ServerService serverService;

    public LeaveServiceImpl () {
        serverService = ServiceFactory.getInstance().getService("serverService", ServerService.class);
    }

    public boolean leave (UserInfo loginInfo, LeaveRecordParam recordParam) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.leave.apply");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        accessParam.setParams("beginDay", recordParam.getBeginDayStr());
        accessParam.setParams("beginTimerInterval", recordParam.getBeginTimeInterval().getCode());
        accessParam.setParams("endDay", recordParam.getEndDayStr());
        accessParam.setParams("endTimerInterval", recordParam.getEndTimeInterval().getCode());
        accessParam.setParams("leaveType", recordParam.getLeaveType().getCode());
        accessParam.setParams("reason", recordParam.getReason());
        return serverService.access(accessParam, Boolean.class);
    }

    public PageResult<List<LeaveRecord>> getLeaveRecord (UserInfo loginInfo,
                                                         LeaveRecordSearchParam searchParam) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.leave.list");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        if (searchParam.getLeaveType() != null) {
            accessParam.setParams("leaveType", searchParam.getLeaveType().getCode());
        }
        if (searchParam.getLeaveStatus() != null) {
            accessParam.setParams("leaveStatus", searchParam.getLeaveStatus().getCode());
        }
        accessParam.setParams("currentPage", searchParam.getPage().getCurrentPage());
        accessParam.setParams("pageSize", searchParam.getPage().getPageSize());
        return serverService.access(accessParam, "leaveRecord", LeaveRecord.class);
    }

    public List<FlowRecord> getLeaveFlow (UserInfo loginInfo, String leaveDesId) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.leave.detail.flow");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        accessParam.setParams("desId", leaveDesId);
        return serverService.accessList(accessParam, FlowRecord.class);
    }
}
