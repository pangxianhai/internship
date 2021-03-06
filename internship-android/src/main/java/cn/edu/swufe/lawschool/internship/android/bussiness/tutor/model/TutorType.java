package cn.edu.swufe.lawschool.internship.android.bussiness.tutor.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       导师类型定义</p>
 * <p>Description: 导师类型定义</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TutorType extends BaseType {
    public final static TutorType GENERAL = new TutorType(100, "普通");

    public final static TutorType LEADER = new TutorType(101, "负责人");

    protected TutorType (Integer code, String desc) {
        super(code, desc);
    }

    public static List<TutorType> getValues () {
        return getValues(TutorType.class);
    }

    public static TutorType parse (int code) {
        return parse(getValues(), code);
    }
}
