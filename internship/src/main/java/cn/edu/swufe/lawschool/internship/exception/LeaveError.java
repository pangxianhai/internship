package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月22
 * <p>Title:       请假错误</p>
 * <p>Description: 请假错误定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class LeaveError extends ErrorType {
    public static ErrorType ADD_LEAVE_ERROR = new LeaveError(120000, "添加请假记录失败");
    public static ErrorType UPDATE_LEAVE_ERROR = new LeaveError(120001, "更新请假记录失败");
    public static ErrorType UPDATE_LEAVE_ID_EMPTY = new LeaveError(120002, "更新请假记录ID为空");
    public static ErrorType ADD_LEAVE_NO_TUTOR = new LeaveError(120003, "你还没分配实习导师");
    public static ErrorType ADD_LEAVE_NO_TEACHER = new LeaveError(120004, "你还没分配带队老师");
    public static ErrorType EXAMINE_STATUS_ERROR = new LeaveError(120005, "审核请假状态错误");
    public static ErrorType EXAMINE_NOT_EXIST = new LeaveError(120006, "该请假记录不存在");
    public static ErrorType DELETE_EXAMINE_ERROR_BY_NO_SELF = new LeaveError(120007, "只能删除自己的请假记录");
    public static ErrorType DELETE_EXAMINE_ERROR_BY_HAS_EXAMINE = new LeaveError(120008, "该请假已经被审核了，不能删除");

    protected LeaveError(Integer code, String desc) {
        super(code, desc);
    }
}
