package cn.edu.swufe.lawschool.internship.android.bussiness.evaluation.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价规则</p>
 * <p>Description: 学生质量评价规则实体</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class EvaluationRule extends BaseModel {
    /**
     * 项目
     */
    String subject;

    /**
     * 评价准则
     */
    String evaluationRule;

    public String getSubject () {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getEvaluationRule () {
        return evaluationRule;
    }

    public void setEvaluationRule (String evaluationRule) {
        this.evaluationRule = evaluationRule;
    }
}

