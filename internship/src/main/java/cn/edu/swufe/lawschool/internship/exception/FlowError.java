package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月19
 * <p>Title:       流程错误</p>
 * <p>Description: 流程错误定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class FlowError extends ErrorType {
    public final static ErrorType ADD_FLOW_ERROR = new FlowError(100000, "添加流程错误");
    public final static ErrorType ADD_FLOW_OPERATOR_ERROR = new FlowError(100001, "添加流程操作者错误");
    public final static ErrorType PRE_FLOW_NO_PASS = new FlowError(100002, "上一步审核未通过，你无需在审核");
    public final static ErrorType CANNOT_EXAMINE = new FlowError(100003, "你没有权限审核");
    public final static ErrorType FLOW_IS_NOT_TO_YOU = new FlowError(100004, "流程还未到你");
    public final static ErrorType UPDATE_FLOW_ERROR = new FlowError(100005, "更新流程失败");
    public final static ErrorType UPDATE_FLOW_ID_EMPTY = new FlowError(100006, "更新流程ID为空");
    public final static ErrorType FLOW_HAS_EXAMINE = new FlowError(100007, "你已审核过了，无需在审核");
    public final static ErrorType FLOW_ID_EMPTY = new FlowError(100008, "流程ID为空");

    protected FlowError(Integer code, String desc) {
        super(code, desc);
    }
}
