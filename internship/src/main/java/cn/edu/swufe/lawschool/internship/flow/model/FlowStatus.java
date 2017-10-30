package cn.edu.swufe.lawschool.internship.flow.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class FlowStatus extends BaseType {
    public final static FlowStatus PASS = new FlowStatus(100, "通过");
    public final static FlowStatus NO_PASS = new FlowStatus(101, "未通过");
    public final static FlowStatus APPLY = new FlowStatus(102, "申请中");
    public final static FlowStatus NEED_EXAMINE = new FlowStatus(103, "待审核");
    public final static FlowStatus NO_NEED_EXAMINE = new FlowStatus(104, "无需在审核");

    protected FlowStatus(Integer code, String desc) {
        super(code, desc);
    }

    public static List<FlowStatus> getValues() {
        return getValues(FlowStatus.class);
    }

    public static FlowStatus parse(int code) {
        return parse(getValues(), code);
    }
}
