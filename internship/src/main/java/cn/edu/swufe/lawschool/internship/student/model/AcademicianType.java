package cn.edu.swufe.lawschool.internship.student.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年04月12
 * <p>Title:       学位定义</p>
 * <p>Description: 学位定义</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AcademicianType extends BaseType {

    public final static AcademicianType BACHELOR = new AcademicianType(100, "本科");

    public final static AcademicianType MASTER = new AcademicianType(101, "硕士");

    public final static AcademicianType DOCTOR = new AcademicianType(102, "博士");

    protected AcademicianType (int code, String desc) {
        super(code, desc);
    }

    public static List<AcademicianType> getValues () {
        return getValues(AcademicianType.class);
    }

    public static AcademicianType parse (int code) {
        return parse(getValues(), code);
    }
}
