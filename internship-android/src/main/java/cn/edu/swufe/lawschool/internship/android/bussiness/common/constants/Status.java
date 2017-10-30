package cn.edu.swufe.lawschool.internship.android.bussiness.common.constants;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       状态</p>
 * <p>Description: 状态</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Status extends BaseType {
    public final static Status VALID = new Status(100, "有效");

    public final static Status INVALID = new Status(101, "无效");

    public final static Status DELETE = new Status(102, "删除");

    public final static Status CANCEL = new Status(103, "注销");

    public final static Status FREEZE = new Status(104, "冻结");

    protected Status (Integer code, String desc) {
        super(code, desc);
    }

    public static List<Status> getValues () {
        return getValues(Status.class);
    }

    public static Status parse (int code) {
        return parse(getValues(), code);
    }
}
