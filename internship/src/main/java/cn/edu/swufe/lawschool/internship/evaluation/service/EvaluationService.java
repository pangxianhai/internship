package cn.edu.swufe.lawschool.internship.evaluation.service;

import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationResult;
import cn.edu.swufe.lawschool.internship.student.model.Student;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价服务</p>
 * <p>Description: 学生质量评价服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface EvaluationService {
    /**
     * Created on 2015年11月29
     * <p>Description: 获取该学生的质量评价</p>
     * @return
     */
    Evaluation getEvaluationByStudentId (Long studentId);

    /**
     * Created on 2015年11月29
     * <p>Description: 带队老师添加学生的质量评价</p>
     * @param student           学生
     * @param teacherUserId     带队老师y用户ID
     * @param evaluationResults 质量评价结果
     * @return
     */
    void teacherAddStudentEvaluation (Student student, Long teacherUserId,
            List<EvaluationResult> evaluationResults);
}
