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
public class LeaveSearchParamRequest extends AbstractRopRequest {

    /**
     * 加密ID
     */
    String desId;

    /**
     * 请假类型
     */
    Integer leaveType;

    /**
     * 请假状态
     */
    Integer leaveStatus;

    /**
     * 当前页数
     */
    Integer currentPage;

    /**
     * 每页大小
     */
    Integer pageSize;

    public Integer getLeaveType () {
        return leaveType;
    }

    public void setLeaveType (Integer leaveType) {
        this.leaveType = leaveType;
    }

    public Integer getCurrentPage () {
        return currentPage;
    }

    public void setCurrentPage (Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize () {
        return pageSize;
    }

    public void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDesId () {
        return desId;
    }

    public void setDesId (String desId) {
        this.desId = desId;
    }

    public Integer getLeaveStatus () {
        return leaveStatus;
    }

    public void setLeaveStatus (Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
