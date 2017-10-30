package cn.edu.swufe.lawschool.internship.server.attend.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.Length;

/**
 * Created on 2016年10月19
 * <p>Title:       查询签到信息request</p>
 * <p>Description: 查询签到信息request</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendParamRequest extends AbstractRopRequest {

    /**
     * 开始时间
     */
    @Length(max = 20)
    String beginTime;

    /**
     * 结束时间
     */
    @Length(max = 20)
    String endTime;

    Integer attendStatusCode;

    //    /**
    //     * 用户Id
    //     */
    //    String userDesId;

    //    /**
    //     * 单位id
    //     */
    //    Long companyId;
    //
    //    /**
    //     * 导师id
    //     */
    //    Long tutorId;
    //
    //    /**
    //     * 带队老师id
    //     */
    //    Long teacherId;

    /**
     * 当前页数
     */
    Integer currentPage;

    /**
     * 每页大小
     */
    Integer pageSize;

    public String getBeginTime () {
        return beginTime;
    }

    public void setBeginTime (String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime () {
        return endTime;
    }

    public void setEndTime (String endTime) {
        this.endTime = endTime;
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

    public Integer getAttendStatusCode () {
        return attendStatusCode;
    }

    public void setAttendStatusCode (Integer attendStatusCode) {
        this.attendStatusCode = attendStatusCode;
    }
}
