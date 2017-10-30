package cn.edu.swufe.lawschool.internship.user.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月04
 * <p>Title:       用户类型</p>
 * <p>Description: 用户类型</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public final class UserType extends BaseType {

    public final static int DEFAULT = 0;

    public final static int SYS_ADMIN_CODE = 100;
    public final static int COMPANY_TUTOR_CODE = 101;
    public final static int TEACHER_CODE = 102;
    public final static int STUDENT_CODE = 103;

    public final static UserType SYS_ADMIN = new UserType(SYS_ADMIN_CODE, "系统管理员");
    public final static UserType COMPANY_TUTOR = new UserType(COMPANY_TUTOR_CODE, "单位导师");
    public final static UserType TEACHER = new UserType(TEACHER_CODE, "带队老师");
    public final static UserType STUDENT = new UserType(STUDENT_CODE, "学生");

    public UserType() {

    }

    protected UserType(Integer code, String desc) {
        super(code, desc);
    }

    public static List<UserType> getValues() {
        return getValues(UserType.class);
    }

    public static UserType parse(int code) {
        return parse(getValues(), code);
    }

}
