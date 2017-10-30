package cn.edu.swufe.lawschool.internship.android.bussiness.common.exception;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

/**
 * Created on 2017年03月07
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class CommonExceptionType extends BaseType {

    public final static CommonExceptionType TYPE_NOT_FOUND = new CommonExceptionType(10000, "类型没有发现");

    public final static CommonExceptionType SYSTEM_ERROR = new CommonExceptionType(10001, "系统错误");

    public final static CommonExceptionType REQUEST_TIMEOUT_ERROR = new CommonExceptionType(10002, "请求网络超时");

    public final static CommonExceptionType RESPONSE_TIMEOUT_ERROR = new CommonExceptionType(10003, "网络响应超时");

    public final static CommonExceptionType NEED_SESSION = new CommonExceptionType(10006, "未登陆");

    protected CommonExceptionType (Integer code, String desc) {
        super(code, desc);
    }
}
