package cn.edu.swufe.lawschool.internship.exception;

import cn.edu.swufe.lawschool.common.exception.CommonExceptionType;

/**
 * Created on 2015年11月05
 * <p>Title:       自定义基础异常类型</p>
 * <p>Description: 自定义基础异常类型</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ErrorType extends CommonExceptionType {

    public final static ErrorType SYS_ERROR = new ErrorType(20000, "系统异常");

    public final static ErrorType NO_ACCESS = new ErrorType(20001, "没有权限");

    public final static ErrorType NO_LOGIN = new ErrorType(20002, "没有登录");

    public final static ErrorType PARSE_PARAM_ERROR = new ErrorType(20003, "解析参数错误");

    public final static ErrorType PAGE_NOT_FUND = new ErrorType(20004, "页面未发现");

    public final static ErrorType SCORE_TYPE_ERROR = new ErrorType(20005, "分数类型不对");

    protected ErrorType (Integer code, String desc) {
        super(code, desc);
    }
}
