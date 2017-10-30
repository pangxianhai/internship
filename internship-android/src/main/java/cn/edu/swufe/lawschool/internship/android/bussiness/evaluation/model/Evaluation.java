package cn.edu.swufe.lawschool.internship.android.bussiness.evaluation.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       学生质量评价</p>
 * <p>Description: 学生质量评价</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Evaluation extends BaseModel {
    /**
     * 学生Id
     */
    Long studentId;

    /**
     * 学生姓名
     */
    String studentName;

    /**
     * 评价者用户Id
     */
    Long evaluationUserId;

    /**
     * 评价者姓名
     */
    String evaluationName;

    /**
     * 评价者时间
     */
    Long evaluationTime;

    /**
     * 评价结果
     */
    List<EvaluationResult> evaluationResult;

    public Long getStudentId () {
        return studentId;
    }

    public void setStudentId (Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName () {
        return studentName;
    }

    public void setStudentName (String studentName) {
        this.studentName = studentName;
    }

    public Long getEvaluationUserId () {
        return evaluationUserId;
    }

    public void setEvaluationUserId (Long evaluationUserId) {
        this.evaluationUserId = evaluationUserId;
    }

    public String getEvaluationName () {
        return evaluationName;
    }

    public void setEvaluationName (String evaluationName) {
        this.evaluationName = evaluationName;
    }

    public Long getEvaluationTime () {
        return evaluationTime;
    }

    public void setEvaluationTime (Long evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public List<EvaluationResult> getEvaluationResult () {
        return evaluationResult;
    }

    public void setEvaluationResult (
            List<EvaluationResult> evaluationResult) {
        this.evaluationResult = evaluationResult;
    }
}
