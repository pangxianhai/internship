package cn.edu.swufe.lawschool.internship.android.bussiness.attend.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       时段</p>
 * <p>Description: 时段定义</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TimeInterval extends BaseType {
    public final static TimeInterval AM = new TimeInterval(100, "上午");

    public final static TimeInterval PM = new TimeInterval(101, "下午");

    protected TimeInterval (Integer code, String desc) {
        super(code, desc);
    }

    public static List<TimeInterval> getValues () {
        return getValues(TimeInterval.class);
    }

    public static TimeInterval parse (int code) {
        return parse(getValues(), code);
    }
}
