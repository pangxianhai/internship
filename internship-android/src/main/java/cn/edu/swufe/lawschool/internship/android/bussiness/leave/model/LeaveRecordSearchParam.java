package cn.edu.swufe.lawschool.internship.android.bussiness.leave.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;

/**
 * Created on 2017年05月12
 * <p>Title:       请假记录查询参数</p>
 * <p>Description: 请假记录查询参数</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveRecordSearchParam extends LeaveRecord {
    /**
     * 分页信息
     */
    Page page;

    public Page getPage () {
        return page;
    }

    public void setPage (Page page) {
        this.page = page;
    }
}
