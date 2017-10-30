package cn.edu.swufe.lawschool.internship.common;

import cn.edu.swufe.lawschool.common.base.BaseType;

/**
 * Created on 2015年11月19
 * <p>Title:       操作类型</p>
 * <p>Description: 操作类型定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ActionType extends BaseType {

    public final static ActionType UPDATE = new ActionType(1, "更新");

    public final static ActionType INSERT = new ActionType(2, "插入");

    protected ActionType (Integer code, String desc) {
        super(code, desc);
    }
}
