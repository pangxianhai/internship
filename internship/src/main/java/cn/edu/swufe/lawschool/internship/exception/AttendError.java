package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月20
 * <p>Title:       签到错误</p>
 * <p>Description: 签到错误定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class AttendError extends ErrorType {
    public final static ErrorType ADD_ATTEND_ERROR = new AttendError(110000, "添加签到错误");

    public final static ErrorType UPDATE_ATTEND_ERROR = new AttendError(110001, "更新签到错误");

    public final static ErrorType ATTEND_ID_EMPTY = new AttendError(110002, "签到ID为空");

    public final static ErrorType ADD_ATTEND_DUPLICATE = new AttendError(110004, "该时段已经签到无需在签到");

    public final static ErrorType ADD_ATTEND_DAY_ERROR = new AttendError(110005, "日期格式错误");

    public final static ErrorType ADD_ATTEND_TIME_INTERVAL_ERROR = new AttendError(110006, "时段格式错误");

    public final static ErrorType ATTEND_NOT_EXIST = new AttendError(110007, "签到信息不存在");

    public final static ErrorType DELETE_ATTEND_BY_NOT_SELF = new AttendError(110008, "您不能删除不是你的签到信息");

    public final static ErrorType DELETE_ATTEND_BY_HAS_EXAMINED = new AttendError(110009, "已经审核的签到记录不能删除");

    protected AttendError (Integer code, String desc) {
        super(code, desc);
    }
}
