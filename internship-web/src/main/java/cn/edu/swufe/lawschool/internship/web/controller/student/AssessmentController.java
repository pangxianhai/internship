package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.assessment.service.AssessmentService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.util.ScoreUtil;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2015年11月30
 * <p>Title:       学生实习评价表</p>
 * <p>Description: 学生实习评价表</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/assessment")
public class AssessmentController {

    @Logger
    private Log log;

    @Autowired
    LoginService loginService;

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/send.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    public String tutorSendAssessment (ModelMap modelMap, Long studentId) {
        Student student = studentService.getStudentById(studentId);
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(
                loginService.getLoginUserInfo(), student);
        if (!operateAuthorization.isCanSendAssessment()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("student", student);
        return "student/assessment/sendAssessment";
    }

    @RequestMapping(value = "/send.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object tutorSendAssessmentSubmit (Long studentId, String tutorRemark, String tutorScore) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        authorizationService.checkStudentAuthorization(loginInfo, studentId);
        assessmentService.tutorAssessment(studentId, loginService.getTutorInfo(), tutorRemark,
                                          ScoreUtil.textToScore(tutorScore));
        return true;
    }

    @RequestMapping(value = "/detail/{assessDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE, UserType.STUDENT_CODE })
    public String tutorSendAssessment (ModelMap modelMap, @PathVariable String assessDestId) {
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
        return "student/assessment/assessmentDetail";
    }

    @RequestMapping(value = "/company_review/{assessDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    public String companyAssessment (ModelMap modelMap, @PathVariable String assessDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = loginService.getTutorInfo();
        if (tutor.isLeader()) {
            Assessment assessment = assessmentService.getAssessmentByDestId(assessDestId);
            authorizationService.checkStudentAuthorization(loginInfo, assessment.getStudentId());
            modelMap.put("assessment", assessment);
        } else {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        return "student/assessment/companyReviewAssessment";
    }

    @RequestMapping(value = "/company_review/submit.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Boolean companyAssessmentSubmit (String desId, String companyRemark) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = loginService.getTutorInfo();
        if (tutor.isLeader()) {
            Assessment assessment = assessmentService.getAssessmentByDestId(desId);
            authorizationService.checkStudentAuthorization(loginInfo, assessment.getStudentId());
            assessmentService.companyAssessment(desId, tutor, companyRemark);
        } else {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        return true;
    }

    @RequestMapping(value = "/teacher_confirm.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    @ResponseBody
    public Boolean teacherConfirm (String desId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Teacher teacher = loginService.getTeacherInfo();
        Assessment assessment = assessmentService.getAssessmentByDestId(desId);
        authorizationService.checkStudentAuthorization(loginInfo, assessment.getStudentId());
        assessmentService.teacherConfirm(desId, teacher);
        return true;
    }
}
