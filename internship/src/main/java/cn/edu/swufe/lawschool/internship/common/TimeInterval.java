package cn.edu.swufe.lawschool.internship.common;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       时段</p>
 * <p>Description: 时段定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class TimeInterval extends BaseType {
    public final static TimeInterval AM = new TimeInterval(100, "上午");
    public final static TimeInterval PM = new TimeInterval(101, "下午");

    protected TimeInterval(Integer code, String desc) {
        super(code, desc);
    }

    public static List<TimeInterval> getValues() {
        return getValues(TimeInterval.class);
    }

    public static TimeInterval parse(int code) {
        return parse(getValues(), code);
    }
}
