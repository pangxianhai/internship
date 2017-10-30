package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月09
 * <p>Title:       导师错误码</p>
 * <p>Description: 导师错误码定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TutorError extends ErrorType {
    public final static ErrorType ADD_TUTOR_ERROR = new TutorError(70000, "添加导师错误");
    public final static ErrorType UPDATE_TUTOR_ERROR = new TutorError(70001, "更新导师错误");
    public final static ErrorType UPDATE_TUTOR_ID_EMPTY = new TutorError(70002, "更新导师id为空");
    public final static ErrorType COMPANY_HAS_LEADER = new TutorError(70003, "该单位已经有负责人了！");
    public final static ErrorType TUTOR_NOT_EXIST = new TutorError(70004, "该导师不存在！");
    public final static ErrorType DELETE_TUTOR_ERROR_BY_HAS_STUDENT = new TutorError(70005, "导师有学生，不能删除！");
    public final static ErrorType DELETE_TUTOR_ERROR_BY_LEADER = new TutorError(70006, "导师是负责人，不能删除！");

    protected TutorError(Integer code, String desc) {
        super(code, desc);
    }
}
