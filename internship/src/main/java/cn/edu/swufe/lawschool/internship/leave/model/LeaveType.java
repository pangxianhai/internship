package cn.edu.swufe.lawschool.internship.leave.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveType extends BaseType {
    public final static LeaveType SICK = new LeaveType(100, "病假");
    public final static LeaveType BUSINESS = new LeaveType(101, "事假");

    protected LeaveType(Integer code, String desc) {
        super(code, desc);
    }

    public static List<LeaveType> getValues() {
        return getValues(LeaveType.class);
    }

    public static LeaveType parse(int code) {
        return parse(getValues(), code);
    }
}
