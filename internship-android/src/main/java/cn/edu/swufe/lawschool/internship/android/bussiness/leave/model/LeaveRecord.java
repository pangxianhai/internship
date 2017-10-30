package cn.edu.swufe.lawschool.internship.android.bussiness.leave.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.TimeInterval;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;
import cn.edu.swufe.lawschool.internship.android.bussiness.student.model.Student;

/**
 * Created on 2015年11月17
 * <p>Title:       请假记录</p>
 * <p>Description: 请假记录</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class LeaveRecord extends BaseModel {

    /**
     * 请假用户ID
     */
    Long userId;

    /**
     * 请假开始时间
     */
    Long beginDay;

    /**
     * 请假开始时间段 上午／下午
     */
    TimeInterval beginTimeInterval;

    /**
     * 请假结束时间
     */
    Long endDay;

    /**
     * 请假结束时间 上午／下午
     */
    TimeInterval endTimeInterval;

    /**
     * 请假类型
     */
    LeaveType leaveType;

    /**
     * 请假原因
     */
    String reason;

    /**
     * 请假状态
     */
    LeaveStatus leaveStatus;

    /**
     * 学生信息
     */
    Student student;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public Long getBeginDay () {
        return beginDay;
    }

    public void setBeginDay (Long beginDay) {
        this.beginDay = beginDay;
    }

    public TimeInterval getBeginTimeInterval () {
        return beginTimeInterval;
    }

    public void setBeginTimeInterval (TimeInterval beginTimeInterval) {
        this.beginTimeInterval = beginTimeInterval;
    }

    public Long getEndDay () {
        return endDay;
    }

    public void setEndDay (Long endDay) {
        this.endDay = endDay;
    }

    public TimeInterval getEndTimeInterval () {
        return endTimeInterval;
    }

    public void setEndTimeInterval (TimeInterval endTimeInterval) {
        this.endTimeInterval = endTimeInterval;
    }

    public LeaveType getLeaveType () {
        return leaveType;
    }

    public void setLeaveType (LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public void setLeaveTypeCode (Integer leaveTypeCode) {
        if (leaveTypeCode != null) {
            this.leaveType = LeaveType.parse(leaveTypeCode.intValue());
        }
    }

    public String getReason () {
        return reason;
    }

    public void setReason (String reason) {
        this.reason = reason;
    }

    public LeaveStatus getLeaveStatus () {
        return leaveStatus;
    }

    public void setLeaveStatus (LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Student getStudent () {
        return student;
    }

    public void setStudent (Student student) {
        this.student = student;
    }

    public Boolean isApplying () {
        return this.leaveStatus.equals(LeaveStatus.APPLYING);
    }
}
