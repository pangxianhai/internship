package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2016年10月13
 * <p>Title:       app密钥错误信息</p>
 * <p>Description: app密钥错误信息</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AppSecretError extends ErrorType {

    public final static ErrorType ADD_SECRET_FAILED = new AppSecretError(180000, "添加密钥失败");

    public final static ErrorType UPDATE_SECRET_ID_EMPTY = new AppSecretError(180001, "修改密钥ID不能为空");

    public final static ErrorType UPDATE_SECRET_FAILED = new AppSecretError(180002, "修改密钥失败");

    public final static ErrorType SECRET_FAILED_NOT_EXIST = new AppSecretError(180003, "密钥不存在");

    public final static ErrorType UPDATE_SECRET_FAILED_STATUS_ERROR = new AppSecretError(180004, "更新密钥状态不对");

    protected AppSecretError (Integer code, String desc) {
        super(code, desc);
    }
}
