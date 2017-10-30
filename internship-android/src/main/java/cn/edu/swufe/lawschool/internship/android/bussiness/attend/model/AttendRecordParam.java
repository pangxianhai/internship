package cn.edu.swufe.lawschool.internship.android.bussiness.attend.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;

/**
 * Created on 2017年05月11
 * <p>Title:       attend查询条件类</p>
 * <p>Description: attend查询条件</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendRecordParam extends AttendRecord {
    /**
     * 开始时间
     */
    String beginTime;

    /**
     * 结束时间
     */
    String endTime;

    /**
     * 页码信息
     */
    Page page;

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

    public Page getPage () {
        return page;
    }

    public void setPage (Page page) {
        this.page = page;
    }
}
