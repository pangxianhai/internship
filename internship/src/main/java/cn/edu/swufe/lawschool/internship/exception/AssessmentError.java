package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月29
 * <p>Title:       实习评价错误类型</p>
 * <p>Description: 实习评价错误类型定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class AssessmentError extends ErrorType {
    public final static ErrorType ADD_ASSESSMENT_FAILED = new AssessmentError(140000, "添加实习评价失败");
    public final static ErrorType UPDATE_ASSESSMENT_FAILED = new AssessmentError(140001, "更新实习评价失败");
    public final static ErrorType UPDATE_ASSESSMENT_ID_EMPTY = new AssessmentError(140002, "更新实习评价id为空");
    public final static ErrorType STUDENT_NOT_EXIST = new AssessmentError(140003, "学生不存在");
    public final static ErrorType TUTOR_NOT_MATCH = new AssessmentError(140004, "该同学不是你的学生");
    public final static ErrorType TUTOR_NOT_EXIST = new AssessmentError(140005, "导师不存在");
    public final static ErrorType TEACHER_NOT_EXIST = new AssessmentError(140006, "带队老师不存在");
    public final static ErrorType TEACHER_NOT_MATCH = new AssessmentError(140007, "该同学不是你的学生");

    protected AssessmentError(Integer code, String desc) {
        super(code, desc);
    }
}
