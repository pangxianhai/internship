package cn.edu.swufe.lawschool.internship.android.bussiness.leave.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       请假状态</p>
 * <p>Description: 请假状态定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class LeaveStatus extends BaseType {
    public final static LeaveStatus APPLYING = new LeaveStatus(100, "审核中");

    public final static LeaveStatus PASS = new LeaveStatus(101, "审核通过");

    public final static LeaveStatus NO_PASS = new LeaveStatus(102, "审核不通过");

    protected LeaveStatus (Integer code, String desc) {
        super(code, desc);
    }

    public static List<LeaveStatus> getValues () {
        return getValues(LeaveStatus.class);
    }

    public static LeaveStatus parse (int code) {
        return parse(getValues(), code);
    }
}
