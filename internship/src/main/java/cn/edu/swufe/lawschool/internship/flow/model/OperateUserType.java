package cn.edu.swufe.lawschool.internship.flow.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年12月13
 * <p>Title:       操作者用户类型</p>
 * <p>Description: 操作者用户类型定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class OperateUserType extends BaseType {

    public final static OperateUserType TUTOR = new OperateUserType(100, "导师");
    public final static OperateUserType TEACHER = new OperateUserType(101, "带队老师");
    public final static OperateUserType COMPANY_LEADER = new OperateUserType(101, "单位负责人");

    protected OperateUserType(Integer code, String desc) {
        super(code, desc);
    }

    public static List<OperateUserType> getValues() {
        return getValues(OperateUserType.class);
    }

    public static OperateUserType parse(int code) {
        return parse(getValues(), code);
    }
}
