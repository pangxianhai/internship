package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.assessment.service.AssessmentService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
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

/**
 * Created on 2016年11月25
 * <p>Title:       学生实习评价表</p>
 * <p>Description: webApp 学生实习评价表 controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppAssessmentController")
@RequestMapping(value = "/webApp/student/assessment")
public class AssessmentController {

    @Autowired
    LoginService loginService;

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/detail/{assessDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE, UserType.STUDENT_CODE })
    public String assessmentDetail (ModelMap modelMap, @PathVariable String assessDestId, String returnUrl) {
        Assessment assessment = assessmentService.getAssessmentByDestId(assessDestId);
        if (assessment == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentById(assessment.getStudentId());
        authorizationService.checkStudentAuthorization(loginInfo, student);
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                                                                                                 student);
        modelMap.put("assessment", assessment);
        modelMap.put("canReviewAssessment", operateAuthorization.isCanReviewAssessment());
        modelMap.put("studentDesId", student.getDesId());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/assessment/assessmentDetail";
    }

    @RequestMapping(value = "/send.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    public String tutorSendAssessment (ModelMap modelMap, String studentDesId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                                                                                                 student);
        if (!operateAuthorization.isCanSendAssessment()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/assessment/sendAssessment";
    }

    @RequestMapping(value = "/company_review/{assessDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    public String companyAssessment (ModelMap modelMap, @PathVariable String assessDestId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Assessment assessment = assessmentService.getAssessmentByDestId(assessDestId);
        Student student = studentService.getStudentById(assessment.getStudentId());
        authorizationService.checkStudentAuthorization(loginInfo, student);
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                                                                                                 student);
        if (!operateAuthorization.isCanReviewAssessment()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("assessment", assessment);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/assessment/companyReviewAssessment";
    }
}
