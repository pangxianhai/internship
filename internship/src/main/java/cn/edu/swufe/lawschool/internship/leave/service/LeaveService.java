package cn.edu.swufe.lawschool.internship.leave.service;

import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月22
 * <p>Title:       请假服务</p>
 * <p>Description: 请假服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface LeaveService {

    /**
     * Created on 2015年11月22
     * <p>Description: 学生请假</p>
     * @author 庞先海
     */
    void studentAskForLeave(LeaveRecord leaveRecord);

    /**
     * Created on 2015年11月22
     * <p>Description: 根据条件分页查询请假</p>
     * @author 庞先海
     */
    List<LeaveRecord> getStudentLeaveRecord(LeaveRecord leaveRecord, Page page);

    /**
     * Created on 2015年11月22
     * <p>Description: 通过加密ID获取请假信息</p>
     * @author 庞先海
     */
    LeaveRecord getStudentLeaveRecordByDesId(String desId);


    /**
     * Created on 2015年11月23
     * <p>Description:导师审核请假</p>
     * @param leaveId
     * @param operateUserId
     * @param status        审核结果
     */
    void tutorExamine(Long leaveId, Long operateUserId, FlowStatus status);

    /**
     * Created on 2015年11月23
     * <p>Description:带队老师审核请假</p>
     * @param leaveId
     * @param operateUserId
     * @param status        审核结果
     */
    void teacherExamine(Long leaveId, Long operateUserId, FlowStatus status);

    /**
     * Created on 2016年07月17
     * <p>Description:删除请假信息</p>
     * @param leaveDesId    请假ID
     * @param operateUserId 操作者/登陆者 用户Id
     */
    void deleteLeaveRecord(String leaveDesId, Long operateUserId);
}
