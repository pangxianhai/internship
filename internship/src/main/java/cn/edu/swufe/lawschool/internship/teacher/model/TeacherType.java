package cn.edu.swufe.lawschool.internship.teacher.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2017年03月28
 * <p>Title:       老师类型定义</p>
 * <p>Description: 老师类型定义</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TeacherType extends BaseType {
    public final static TeacherType GENERAL = new TeacherType(100, "普通");

    public final static TeacherType COLLEGE_LEADER = new TeacherType(101, "学院负责人");

    public final static TeacherType UNIVERSITY_LEADER = new TeacherType(102, "学校负责人");

    public final static TeacherType DEPARTMENT_LEADER = new TeacherType(103, "系负责人");

    public final static TeacherType SPECIALITY_LEADER = new TeacherType(104, "专业负责人");

    protected TeacherType (Integer code, String desc) {
        super(code, desc);
    }

    /**
     * 当老师是否比@teacherType级别更高
     * @param teacherType
     * @return
     */
    public boolean higher (TeacherType teacherType) {
        if (this.equals(GENERAL) || teacherType.equals(GENERAL)) {
            return this.getCode() > teacherType.getCode();
        } else {
            return this.getCode() < teacherType.getCode();
        }
    }

    public static List<TeacherType> getValues () {
        return getValues(TeacherType.class);
    }

    public static TeacherType parse (int code) {
        return parse(getValues(), code);
    }

    public static List<TeacherType> getLeaderType () {
        return Arrays.asList(UNIVERSITY_LEADER, COLLEGE_LEADER, DEPARTMENT_LEADER, SPECIALITY_LEADER);
    }
}
