package cn.edu.swufe.lawschool.internship.evaluation.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.evaluation.mapper.EvaluationMapper;
import cn.edu.swufe.lawschool.internship.evaluation.mapper.EvaluationResultMapper;
import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationResult;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationService;
import cn.edu.swufe.lawschool.internship.exception.EvaluationError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:        学生质量评价服务</p>
 * <p>Description:  学生质量评价服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationMapper evaluationMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    EvaluationResultMapper evaluationResultMapper;

    public Evaluation getEvaluationByStudentId (Long studentId) {
        if (studentId == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setStudentId(studentId);
        return selectOne(evaluation);
    }

    public void teacherAddStudentEvaluation (Student student, Long teacherUserId,
            List<EvaluationResult> evaluationResults) {
        Teacher teacher = teacherService.getTeacherByUserId(teacherUserId);
        if (student == null) {
            throw new InternshipException(EvaluationError.STUDENT_NOT_MATCH);
        }
        if (teacher == null) {
            throw new InternshipException(EvaluationError.TEACHER_NOT_EXIST);
        }
        if (!student.getTeacherId().equals(teacher.getId())) {
            throw new InternshipException(EvaluationError.TEACHER_NOT_MATCH);
        }
        this.addStudentEvaluation(student, teacher.getUserInfo(), evaluationResults);
    }

    private void addStudentEvaluation (Student student, UserInfo addUserInfo,
            List<EvaluationResult> evaluationResults) {
        if (CollectionUtil.isEmpty(evaluationResults)) {
            throw new InternshipException(EvaluationError.ADD_EVALUATION_CONTENT_EMPTY);
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setStudentId(student.getId());
        evaluation.setStudentName(student.getName());
        evaluation.setEvaluationUserId(addUserInfo.getId());
        evaluation.setEvaluationName(addUserInfo.getName());
        evaluation.setEvaluationTime(DateUtil.currentMilliseconds());
        evaluation.setCreatedBy(addUserInfo.getName());
        evaluation.setStatus(Status.VALID);
        int count = evaluationMapper.insert(evaluation);
        if (count <= 0) {
            throw new InternshipException(EvaluationError.ADD_EVALUATION_FAILED);
        }
        for (EvaluationResult evaluationResult : evaluationResults) {
            evaluationResult.setEvaluationId(evaluation.getId());
            evaluationResult.setStatus(Status.VALID);
            evaluationResult.setCreatedBy(evaluation.getCreatedBy());
            int addEvaluationCount = evaluationResultMapper.insert(evaluationResult);
            if (addEvaluationCount <= 0) {
                throw new InternshipException(EvaluationError.ADD_EVALUATION_FAILED);
            }
        }
    }

    private Evaluation selectOne (Evaluation evaluation) {
        List<Evaluation> evaluations = evaluationMapper.select(evaluation);
        if (CollectionUtil.isNotEmpty(evaluations)) {
            Evaluation _evaluation = evaluations.get(0);
            _evaluation.setEvaluationResult(this.getEvaluationResultByEvaluationId(_evaluation.getId()));
            return _evaluation;
        }
        return null;
    }

    private List<EvaluationResult> getEvaluationResultByEvaluationId (Long evaluationId) {
        EvaluationResult evaluationResult = new EvaluationResult();
        evaluationResult.setEvaluationId(evaluationId);
        return evaluationResultMapper.select(evaluationResult);
    }
}
