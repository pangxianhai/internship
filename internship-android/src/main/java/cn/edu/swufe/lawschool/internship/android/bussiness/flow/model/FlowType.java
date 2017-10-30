package cn.edu.swufe.lawschool.internship.android.bussiness.flow.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       流程类型</p>
 * <p>Description: 流程类型记录</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class FlowType extends BaseType {
    public final static FlowType LEAVE = new FlowType(100, "请假");

    public final static FlowType ATTEND = new FlowType(101, "签到");

    public final static FlowType WEEKLY_JOURNAL = new FlowType(102, "周记");

    public final static FlowType ASSESSMENT = new FlowType(103, "实习评价");

    public final static FlowType REPORT = new FlowType(104, "实习报告");

    public final static FlowType DIARY_JOURNAL = new FlowType(105, "日记");

    protected FlowType (Integer code, String desc) {
        super(code, desc);
    }

    public static List<FlowType> getValues () {
        return getValues(FlowType.class);
    }

    public static FlowType parse (int code) {
        return parse(getValues(), code);
    }
}