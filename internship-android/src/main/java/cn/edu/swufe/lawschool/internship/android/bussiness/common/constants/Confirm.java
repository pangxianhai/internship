package cn.edu.swufe.lawschool.internship.android.bussiness.common.constants;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

/**
 * Created on 2017年05月03
 * <p>Title:       确认</p>
 * <p>Description: 确认</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Confirm extends BaseType {
    public static final Confirm CONFIRMED = new Confirm(100, "已确认");

    public static final Confirm UNCONFIRMED = new Confirm(101, "未确认");

    protected Confirm (Integer code, String desc) {
        super(code, desc);
    }
}
