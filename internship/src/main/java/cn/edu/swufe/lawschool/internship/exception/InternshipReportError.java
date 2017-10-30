package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月30
 * <p>Title:       实习报告错误类型</p>
 * <p>Description: 实习报告错误类型定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipReportError extends ErrorType {

    public final static ErrorType STUDENT_NOT_EXIST = new InternshipNotesError(160000, "学生不存在");
    public final static ErrorType TEACHER_NOT_EXIST = new InternshipNotesError(160001, "该用户不是带队老师");
    public final static ErrorType TEACHER_NOT_MATCH = new InternshipNotesError(160002, "改同学不是你的学生");
    public final static ErrorType ADD_INTERNSHIP_REPORT_ERROR = new InternshipNotesError(160003, "添加实习报告失败");
    public final static ErrorType UPDATE_INTERNSHIP_REPORT_ERROR = new InternshipNotesError(160004, "更新实习报告失败");
    public final static ErrorType UPDATE_INTERNSHIP_REPORT_ID_EMPTY = new InternshipNotesError(160005, "更新实习报告ID为空");
    public final static ErrorType STUDENT_HAS_INTERNSHIP_REPORT = new InternshipNotesError(160006, "您已有实习报告了！");

    protected InternshipReportError(Integer code, String desc) {
        super(code, desc);
    }
}
