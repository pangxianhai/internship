package cn.edu.swufe.lawschool.internship.android.bussiness.leave.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;

/**
 * Created on 2017年05月12
 * <p>Title:       请假参数</p>
 * <p>Description: 请假参数</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveRecordParam extends LeaveRecord {
    /**
     * 请假开始时间
     */
    String beginDayStr;

    /**
     * 请假开始时间
     */
    String endDayStr;

    public String getBeginDayStr () {
        return beginDayStr;
    }

    public void setBeginDayStr (String beginDayStr) {
        this.beginDayStr = beginDayStr;
    }

    public String getEndDayStr () {
        return endDayStr;
    }

    public void setEndDayStr (String endDayStr) {
        this.endDayStr = endDayStr;
    }
}
