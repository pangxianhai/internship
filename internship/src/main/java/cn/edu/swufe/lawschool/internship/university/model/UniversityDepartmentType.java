package cn.edu.swufe.lawschool.internship.university.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年03月29
 * <p>Title:       大学部门类型定义</p>
 * <p>Description: 大学部门类型定义</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UniversityDepartmentType extends BaseType {

    public final static UniversityDepartmentType UNIVERSITY = new UniversityDepartmentType(100, "大学");

    public final static UniversityDepartmentType COLLEGE = new UniversityDepartmentType(101, "学院");

    public final static UniversityDepartmentType DEPARTMENT = new UniversityDepartmentType(102, "系");

    public final static UniversityDepartmentType SPECIALITY = new UniversityDepartmentType(103, "专业");

    protected UniversityDepartmentType (Integer code, String desc) {
        super(code, desc);
    }

    public UniversityDepartmentType nextDepartment () {
        return parse(this.getCode() + 1);
    }

    public static List<UniversityDepartmentType> getValues () {
        return getValues(UniversityDepartmentType.class);
    }

    public static UniversityDepartmentType parse (Integer code) {
        if (code == null) {
            return null;
        }
        return parse(getValues(), code);
    }
}
