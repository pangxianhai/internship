package cn.edu.swufe.lawschool.internship.evaluation.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       评价等级</p>
 * <p>Description: 评价等级定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class EvaluationGrade extends BaseType {
    public final static EvaluationGrade A = new EvaluationGrade(100, "A");

    public final static EvaluationGrade B = new EvaluationGrade(101, "B");

    public final static EvaluationGrade C = new EvaluationGrade(102, "C");

    public final static EvaluationGrade D = new EvaluationGrade(103, "D");

    public final static EvaluationGrade E = new EvaluationGrade(104, "E");

    public EvaluationGrade () {

    }

    protected EvaluationGrade (Integer code, String desc) {
        super(code, desc);
    }

    public static List<EvaluationGrade> getValues () {
        return getValues(EvaluationGrade.class);
    }

    public static EvaluationGrade parse (Integer code) {
        return parse(getValues(), code);
    }
}
