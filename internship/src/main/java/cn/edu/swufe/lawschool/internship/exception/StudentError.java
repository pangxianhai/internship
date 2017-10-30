package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月09
 * <p>Title:       学生错误类型</p>
 * <p>Description: 学生错误类型</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class StudentError extends ErrorType {
    public final static ErrorType ADD_STUDENT_ERROR = new UserError(40000, "添加学生失败");

    public final static ErrorType UPDATE_STUDENT_ID_EMPTY = new UserError(40001, "更新学生Id不能为空");

    public final static ErrorType UPDATE_STUDENT_ERROR = new UserError(40002, "更新学生信息失败");

    public final static ErrorType STUDENT_NOT_EXIST = new UserError(40003, "学生不存在");

    public final static ErrorType NO_TUTOR = new UserError(40004, "未分配实习导师");

    public final static ErrorType NO_TEACHER = new UserError(40005, "未分配带队老师");

    public final static ErrorType NO_COMPANY = new UserError(40006, "未分配实习单位");

    public final static ErrorType DELETE_STUDENT_ERROR_BY_HAS_INTERNSHIP = new UserError(40007,
                                                                                         "学生已经开始实习，不能删除");

    public final static ErrorType UPDATE_STUDENT_ERROR_NEW_COMPANY_NO_LEADER = new UserError(40008,
                                                                                             "更新学生信息失败，该学生在原单位负责人有未审核的信息，新单位没有负责人");

    public final static ErrorType UPDATE_STUDENT_ERROR_COMPANY_DIFFERENT = new UserError(40009,
                                                                                         "更新学生导师失败，学生所在单位和导师所在单位不一致");

    public final static ErrorType UPDATE_STUDENT_ERROR_COMPANY_OF_STUDENT_DIFFERENT = new UserError(40010,
                                                                                                    "批量修改学生导师出错，所有学生必须同属于同一公司才能执行该操作");

    public final static ErrorType UNIVERSITY_NO_MATCH = new UserError(40011, "学生和老师不在同一个学校及院系");

    protected StudentError (Integer code, String desc) {
        super(code, desc);
    }
}
