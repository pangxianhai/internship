package cn.edu.swufe.lawschool.internship.server.leave.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.Length;
import com.xavier.rop.validation.annotation.NotNull;
import com.xavier.rop.validation.annotation.Pattern;

/**
 * Created on 2017年05月12
 * <p>Title:       请假参数</p>
 * <p>Description: 请假参数</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveApplyRequest extends AbstractRopRequest {
    /**
     * 请假开始时间
     */
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String beginDay;

    /**
     * 请假开始时间段 上午／下午
     */
    @NotNull
    private Integer beginTimerInterval;

    /**
     * 请假结束时间
     */
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String endDay;

    /**
     * 请假结束时间 上午／下午
     */
    @NotNull
    private Integer endTimerInterval;

    /**
     * 请假类型
     */
    @NotNull
    Integer leaveType;

    /**
     * 请假原因
     */
    @Length(min = 2, max = 100)
    String reason;

    public String getBeginDay () {
        return beginDay;
    }

    public void setBeginDay (String beginDay) {
        this.beginDay = beginDay;
    }

    public Integer getBeginTimerInterval () {
        return beginTimerInterval;
    }

    public void setBeginTimerInterval (Integer beginTimerInterval) {
        this.beginTimerInterval = beginTimerInterval;
    }

    public String getEndDay () {
        return endDay;
    }

    public void setEndDay (String endDay) {
        this.endDay = endDay;
    }

    public Integer getEndTimerInterval () {
        return endTimerInterval;
    }

    public void setEndTimerInterval (Integer endTimerInterval) {
        this.endTimerInterval = endTimerInterval;
    }

    public Integer getLeaveType () {
        return leaveType;
    }

    public void setLeaveType (Integer leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason () {
        return reason;
    }

    public void setReason (String reason) {
        this.reason = reason;
    }
}
