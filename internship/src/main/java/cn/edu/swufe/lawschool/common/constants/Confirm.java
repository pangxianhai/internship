package cn.edu.swufe.lawschool.common.constants;

import cn.edu.swufe.lawschool.common.base.BaseType;

/**
 * Created on 2015年11月29
 * <p>Title:       确认</p>
 * <p>Description: 确认</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Confirm extends BaseType {
    public static final Confirm CONFIRMED = new Confirm(100, "已确认");

    public static final Confirm UNCONFIRMED = new Confirm(101, "未确认");

    protected Confirm (Integer code, String desc) {
        super(code, desc);
    }
}
