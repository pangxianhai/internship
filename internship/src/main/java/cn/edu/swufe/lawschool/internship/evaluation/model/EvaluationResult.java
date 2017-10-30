package cn.edu.swufe.lawschool.internship.evaluation.model;

/**
 * Created on 2017年02月17
 * <p>Title:       学生质量评价结果</p>
 * <p>Description: 学生质量评价结果实体</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class EvaluationResult extends EvaluationRule {

    /**
     * 学生质量评价ID
     */
    Long evaluationId;

    /**
     * 评价等级
     */
    EvaluationGrade grade;

    public Long getEvaluationId () {
        return evaluationId;
    }

    public void setEvaluationId (Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public EvaluationGrade getGrade () {
        return grade;
    }

    public void setGrade (EvaluationGrade grade) {
        this.grade = grade;
    }
}
