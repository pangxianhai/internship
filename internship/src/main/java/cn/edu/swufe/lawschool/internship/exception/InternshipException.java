package cn.edu.swufe.lawschool.internship.exception;

import cn.edu.swufe.lawschool.common.exception.CommonException;

/**
 * Created on 2015年11月05
 * <p>Title:       自定义异常</p>
 * <p>Description: 自定义异常</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipException extends CommonException {

    public InternshipException(ErrorType exceptionType) {
        super(exceptionType);
    }

    public InternshipException(ErrorType exceptionType, String message) {
        super(exceptionType, message);
    }

    public InternshipException(ErrorType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
