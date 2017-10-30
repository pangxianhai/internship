package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月09
 * <p>Title:       带队老师错误码</p>
 * <p>Description: 带队老师错误码</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class TeacherError extends ErrorType {
    public final static ErrorType ADD_TEACHER_ERROR = new TeacherError(80000, "添加带队老师错误");

    public final static ErrorType UPDATE_TEACHER_ERROR = new TeacherError(80001, "更新带队老师错误");

    public final static ErrorType UPDATE_TEACHER_ID_EMPTY = new TeacherError(80002, "更新带队老师id为空");

    public final static ErrorType HAS_UNIVERSITY_LEADER = new TeacherError(80003, "该学校已经有带队老师负责人了");

    public final static ErrorType TEACHER_NOT_EXIST = new TeacherError(80004, "带队老师不存在");

    public final static ErrorType DELETE_TEACHER_ERROR_BY_HAS_STUDENT = new TeacherError(80005,
                                                                                         "该老师有正在实习的学生，不能删除");

    public final static ErrorType HAS_COLLEGE_LEADER = new TeacherError(80006, "该学院已经有带队老师负责人了");

    public final static ErrorType HAS_SPECIALITY_LEADER = new TeacherError(80007, "该专业已经有带队老师负责人了");

    public final static ErrorType HAS_DEPARTMENT_LEADER = new TeacherError(80008, "该系已经有带队老师负责人了");

    public final static ErrorType TEACHER_TYPE_ERROR = new TeacherError(80009, "带队老师类型错误");

    public final static ErrorType TEACHER_IS_NOT_IN_DEPARTMENT = new TeacherError(80010, "角色和学校信息不匹配");

    protected TeacherError (Integer code, String desc) {
        super(code, desc);
    }
}
