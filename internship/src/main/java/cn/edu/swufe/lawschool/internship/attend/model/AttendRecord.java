package cn.edu.swufe.lawschool.internship.attend.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import com.xavier.commons.util.DateUtil;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       签到记录</p>
 * <p>Description: 签到记录</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class AttendRecord extends BaseDO {

    /**
     * 签到用户Id
     */
    Long userId;

    /**
     * 签到时间 到天
     */
    Long attendDay;

    /**
     * 签到时间显示
     */
    String attendDayShow;

    /**
     * 签到时段 上午／下午
     */
    TimeInterval timeInterval;

    /**
     * 签到状态
     */
    AttendStatus attendStatus;

    /**
     * 学生信息
     */
    Student student;

    /**
     * 流程列表
     */
    List<FlowRecord> flowRecords;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public Long getAttendDay () {
        return attendDay;
    }

    public void setAttendDay (Long attendDay) {
        this.attendDay = attendDay;
        this.attendDayShow = DateUtil.format(DateUtil.millisecondsToDate(this.attendDay));
    }

    public TimeInterval getTimeInterval () {
        return timeInterval;
    }

    public void setTimeInterval (TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public AttendStatus getAttendStatus () {
        return attendStatus;
    }

    public void setAttendStatus (AttendStatus attendStatus) {
        this.attendStatus = attendStatus;
    }

    public Student getStudent () {
        return student;
    }

    public void setStudent (Student student) {
        this.student = student;
    }

    public List<FlowRecord> getFlowRecords () {
        return flowRecords;
    }

    public void setFlowRecords (List<FlowRecord> flowRecords) {
        this.flowRecords = flowRecords;
    }

    public Boolean getIsApply () {
        return AttendStatus.APPLY.equals(attendStatus);
    }

    public Boolean getIsAttended () {
        return AttendStatus.ATTENDED.equals(attendStatus);
    }

    public Boolean getIsLeaved () {
        return AttendStatus.LEAVE.equals(attendStatus);
    }

    public String getAttendDayShow () {
        return attendDayShow;
    }

    public void setAttendDayShow (String attendDayShow) {
        this.attendDayShow = attendDayShow;
    }
}