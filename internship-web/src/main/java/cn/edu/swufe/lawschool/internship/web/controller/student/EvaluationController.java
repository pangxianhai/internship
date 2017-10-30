package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationGrade;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationResult;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationRule;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationRuleService;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationService;
import cn.edu.swufe.lawschool.internship.exception.EvaluationError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import com.alibaba.fastjson.JSON;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年12月02
 * <p>Title:       学生质量评价表</p>
 * <p>Description: 学生质量评价表Controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/evaluation")
public class EvaluationController {

    @Autowired
    LoginService loginService;

    @Autowired
    StudentService studentService;

    @Autowired
    EvaluationRuleService evaluationRuleService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    EvaluationService evaluationService;

    @RequestMapping(value = "/send/{studentDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    public String teacherSendEvaluation (ModelMap modelMap, @PathVariable String studentDesId) {
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginService.getLoginUserInfo(), student);
        List<EvaluationRule> evaluationRules = evaluationRuleService.getEvaluationRule();
        modelMap.put("evaluationGrades", EvaluationGrade.getValues());
        modelMap.put("evaluationRules", evaluationRules);
        modelMap.put("student", student);
        return "student/evaluation/sendEvaluation";
    }

    @RequestMapping(value = "/send_evaluation_submit.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    @ResponseBody
    public Object teacherSendEvaluationSubmit (String studentDesId, String evaluationResult) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginInfo, student);
        evaluationService.teacherAddStudentEvaluation(student, loginInfo.getId(),
                                                      parseEvaluationResult(evaluationResult));
        return true;
    }

    @RequestMapping(value = "/detail/studentId_{studentDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    public String getEvaluationByStudentDesId (ModelMap modelMap, @PathVariable String studentDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginInfo, student);
        Evaluation evaluation = evaluationService.getEvaluationByStudentId(student.getId());
        modelMap.put("evaluation", evaluation);
        return "student/evaluation/evaluationDetail";
    }

    /**
     * @param evaluationResult 格式为 [{"ruleId":"评价规则加密ID","grade":"100"},{"ruleId":"评价规则加密ID","grade":"101"}]
     *                         ruleId评价规则加密ID grade评价分数,对应:EvaluationGrade
     * @return
     */
    private List<EvaluationResult> parseEvaluationResult (String evaluationResult) {
        if (StringUtil.isBlank(evaluationResult)) {
            throw new InternshipException(EvaluationError.ADD_EVALUATION_CONTENT_EMPTY);
        }
        List<HashMap> evaluationMapList = JSON.parseArray(evaluationResult, HashMap.class);
        //为了执行快 一次查出质量规则
        List<EvaluationRule> evaluationRules = evaluationRuleService.getEvaluationRule();
        Map<String, EvaluationRule> evaluationRuleMap = new HashMap<String, EvaluationRule>(
                evaluationRules.size());
        for (EvaluationRule er : evaluationRules) {
            evaluationRuleMap.put(AESUtil.encrypt(String.valueOf(er.getId())), er);
        }

        List<EvaluationResult> evaluationResultList = new ArrayList<EvaluationResult>();
        for (Map em : evaluationMapList) {
            EvaluationRule evaluationRule = evaluationRuleMap.get(em.get("ruleId"));
            EvaluationResult _evaluationResult = new EvaluationResult();
            _evaluationResult.setSubject(evaluationRule.getSubject());
            _evaluationResult.setEvaluationRule(evaluationRule.getEvaluationRule());
            EvaluationGrade grade = EvaluationGrade.parse(NumberUtil.parseInt(em.get("grade").toString()));
            _evaluationResult.setGrade(grade);
            evaluationResultList.add(_evaluationResult);
        }
        return evaluationResultList;
    }
}
