package cn.edu.swufe.lawschool.common.exception;

/**
 * Created on 2015年11月05
 * <p>Title:       基础异常</p>
 * <p>Description:  基础异常</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class CommonException extends RuntimeException {

    private Integer code;
    private String desc;

    public CommonException(CommonExceptionType exceptionType) {
        super(exceptionType.getDesc());
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public CommonException(CommonExceptionType exceptionType, String message) {
        super(message);
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public CommonException(CommonExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.code = exceptionType.getCode();
        this.desc = exceptionType.getDesc();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
