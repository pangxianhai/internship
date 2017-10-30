package cn.edu.swufe.lawschool.internship.android.bussiness.server.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

/**
 * Created on 2017年03月08
 * <p>Title:       访问方式 </p>
 * <p>Description: 访问方式定义</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AccessMethod extends BaseType {
    public static final AccessMethod GET = new AccessMethod(100, "GET");

    public static final AccessMethod POST = new AccessMethod(101, "POST");

    protected AccessMethod (Integer code, String desc) {
        super(code, desc);
    }
}
