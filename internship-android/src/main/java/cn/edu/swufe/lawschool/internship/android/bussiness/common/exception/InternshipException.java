package cn.edu.swufe.lawschool.internship.android.bussiness.common.exception;

/**
 * Created on 2017年03月07
 * <p>Title:       基础异常</p>
 * <p>Description:  基础异常</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipException extends RuntimeException {

    private Integer code;

    private String desc;

    public InternshipException (CommonExceptionType exceptionType) {
        super(exceptionType.getDesc());
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public InternshipException (Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public InternshipException (CommonExceptionType exceptionType, String message) {
        super(message);
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public InternshipException (CommonExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public Integer getCode () {
        return this.code;
    }

    public String getDesc () {
        return this.desc;
    }
}
