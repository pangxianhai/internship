package cn.edu.swufe.lawschool.common.constants;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       性别类型</p>
 * <p>Description: 性别类型</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class SexType extends BaseType {
    public static final SexType M = new SexType(100, "男");
    public static final SexType F = new SexType(101, "女");

    public SexType() {
    }

    protected SexType(Integer code, String desc) {
        super(code, desc);
    }

    public static List<SexType> getValues() {
        return getValues(SexType.class);
    }

    public static SexType parse(int code) {
        return parse(getValues(), code);
    }
}
