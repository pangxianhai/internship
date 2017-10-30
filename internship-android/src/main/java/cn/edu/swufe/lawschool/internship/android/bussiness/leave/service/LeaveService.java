package cn.edu.swufe.lawschool.internship.android.bussiness.leave.service;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecordSearchParam;
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
public interface LeaveService {

    /**
     * 请假
     * @param loginInfo   当前用户信息
     * @param recordParam 请假参数
     * @return
     */
    boolean leave (UserInfo loginInfo, LeaveRecordParam recordParam);

    /**
     * 请假列表查询
     * @param loginInfo
     * @param searchParam
     * @return
     */
    PageResult<List<LeaveRecord>> getLeaveRecord (UserInfo loginInfo, LeaveRecordSearchParam searchParam);

    /**
     * 查询请假流程
     * @param leaveDesId 假期ID
     * @return
     */
    List<FlowRecord> getLeaveFlow (UserInfo loginInfo, String leaveDesId);
}
