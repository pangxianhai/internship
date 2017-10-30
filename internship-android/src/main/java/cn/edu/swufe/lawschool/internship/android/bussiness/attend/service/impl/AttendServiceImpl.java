package cn.edu.swufe.lawschool.internship.android.bussiness.attend.service.impl;

import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.TimeInterval;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.ServerService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       签到业务</p>
 * <p>Description: 签到业务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendServiceImpl implements AttendService {
    ServerService serverService;

    public AttendServiceImpl () {
        serverService = ServiceFactory.getInstance().getService("serverService", ServerService.class);
    }

    public Boolean studentAttend (UserInfo loginInfo, String attendDay, TimeInterval timeInterval) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.attend");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        accessParam.setParams("attendDay", attendDay);
        accessParam.setParams("timeIntervalCode", timeInterval.getCode());
        return serverService.access(accessParam, Boolean.class);
    }

    public PageResult<List<AttendRecord>> getAttendRecord (UserInfo loginInfo,
                                                           AttendRecordParam recordParam) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.attend.list");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        accessParam.setParams("currentPage", recordParam.getPage().getCurrentPage());
        accessParam.setParams("pageSize", recordParam.getPage().getPageSize());
        accessParam.setParams("beginTime", recordParam.getBeginTime());
        accessParam.setParams("endTime", recordParam.getEndTime());
        if (recordParam.getAttendStatus() != null) {
            accessParam.setParams("attendStatusCode", recordParam.getAttendStatus().getCode());
        }
        return serverService.access(accessParam, "attendRecordList", AttendRecord.class);
    }

    public Boolean deleteAttend (UserInfo loginInfo, String attendDesId) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("student.attend.delete");
        accessParam.setVersion("1.0");
        accessParam.setSessionId(loginInfo.getSessionId());
        accessParam.setParams("attendRecordDestId", attendDesId);
        return serverService.access(accessParam, Boolean.class);
    }
}
