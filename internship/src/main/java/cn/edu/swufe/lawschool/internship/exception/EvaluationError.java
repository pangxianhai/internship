package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月29
 * <p>Title:       质量评价错误定义</p>
 * <p>Description: 质量评价错误定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class EvaluationError extends ErrorType {
    public final static ErrorType ADD_EVALUATION_FAILED = new AssessmentError(150000, "添加实习质量评价失败");

    public final static ErrorType TEACHER_NOT_EXIST = new AssessmentError(150001, "带队老师不存在");

    public final static ErrorType TEACHER_NOT_MATCH = new AssessmentError(150002, "该同学不是你的学生");

    public final static ErrorType STUDENT_NOT_MATCH = new AssessmentError(150003, "学生不存在");

    public final static ErrorType ADD_EVALUATION_CONTENT_EMPTY = new AssessmentError(150004, "实习质量评价内容为空");

    protected EvaluationError (Integer code, String desc) {
        super(code, desc);
    }
}
