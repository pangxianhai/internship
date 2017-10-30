package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月09
 * <p>Title:      用户错误类型</p>
 * <p>Description: 用户错误类型</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class UserError extends ErrorType {

    /**
     * 登录错误
     */
    public final static ErrorType USER_NOT_EXIST = new UserError(60000, "用户不存在");
    public final static ErrorType PASSWORD_ERROR = new UserError(60001, "用户名和密码不匹配");
    public final static ErrorType LOGIN_FAILED = new UserError(60002, "登录失败");
    public final static ErrorType USER_TYPE_EXIST = new UserError(60003, "用户类型不存在");
    public final static ErrorType APP_ONLY_STUDENT = new UserError(60004, "APP暂时只支持学生登录");

    /**
     * 添加错误
     */
    public final static ErrorType ADD_USER_ERROR = new UserError(30000, "添加用户失败");
    public final static ErrorType USER_ID_EMPTY = new UserError(30001, "userID不能为空");
    public final static ErrorType USER_NAME_EMPTY = new UserError(30002, "userName不能为空");
    public final static ErrorType USER_NAME_DUPLICATE = new UserError(30003, "用户名已经使用");

    /**
     * 修改错误
     */
    public final static ErrorType UPDATE_USER_ERROR = new UserError(30100, "更新用户失败");
    public final static ErrorType UPDATE_USER_KEY_ERROR = new UserError(30101, "更新用户不能userId和userName同时为空");
    /**
     * 修改密码
     */
    public final static ErrorType SOURCE_PASSWORD_ERROR = new UserError(30202, "原密码错误");
    public final static ErrorType PASSWORD_AGAIN_ERROR = new UserError(30203, "两次密码输入不一致");

    public final static ErrorType DELETE_USER_ID_EMPTY = new UserError(30300, "删除用户userId为空");
    public final static ErrorType DELETE_USER_FAILED = new UserError(30301, "删除用户失败");


    protected UserError(Integer code, String desc) {
        super(code, desc);
    }
}
