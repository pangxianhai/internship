package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationGrade;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationRule;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationRuleService;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationService;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created on 2016年11月25
 * <p>Title:       学生质量评价表</p>
 * <p>Description: web App 学生质量评价表 controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppEvaluationController")
@RequestMapping(value = "/webApp/student/evaluation")
public class EvaluationController {
    @Autowired
    LoginService loginService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    EvaluationRuleService evaluationRuleService;

    @RequestMapping(value = "/detail/studentId_{studentDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    public String getEvaluationByStudentDesId (ModelMap modelMap, @PathVariable String studentDesId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginInfo, student);
        Evaluation evaluation = evaluationService.getEvaluationByStudentId(student.getId());
        modelMap.put("evaluation", evaluation);
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/evaluation/evaluationDetail";
    }

    @RequestMapping(value = "/send/{studentDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    public String teacherSendEvaluation (ModelMap modelMap, @PathVariable String studentDesId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginInfo, student);
        List<EvaluationRule> evaluationRules = evaluationRuleService.getEvaluationRule();
        modelMap.put("evaluationGrades", EvaluationGrade.getValues());
        modelMap.put("evaluationRules", evaluationRules);
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/evaluation/sendEvaluation";
    }
}
