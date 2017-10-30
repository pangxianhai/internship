package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.report.service.InternshipReportService;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.util.ScoreUtil;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2015年12月03
 * <p>Title:       实习报告</p>
 * <p>Description: 实习报告Controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/report")
public class InternshipReportController {

    @Autowired
    LoginService loginService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    InternshipReportService internshipReportService;

    @RequestMapping(value = "/create.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentCreateInternshipReport (ModelMap modelMap) {
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        modelMap.put("action", "create");
        return "student/internshipReport/internshipReport";
    }

    @RequestMapping(value = "/create.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public String studentCreateInternshipReportSubmit (String subject) {
        Student student = loginService.getStudentInfo();
        return internshipReportService.studentCreateInternshipReport(student, subject);
    }

    @RequestMapping(value = "/detail/{reportDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String getInternshipReport (ModelMap modelMap, @PathVariable String reportDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipReport internshipReport = internshipReportService.getStudentInternshipByDesId(reportDesId);
        Student student = studentService.getStudentById(internshipReport.getStudentId());
        authorizationService.checkStudentAuthorization(loginInfo, student);
        if (loginInfo.isTeacher()) {
            if (internshipReport.getReviewerId() == null) {
                modelMap.put("action", "teacherReview");
            }
        }
        modelMap.put("internshipReport", internshipReport);
        modelMap.put("student", student);
        return "student/internshipReport/internshipReport";
    }

    @RequestMapping(value = "/teacher_review.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    @ResponseBody
    public boolean teacherReviewInternshipReport (String desId, String teacherReview, String reportScore) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Teacher teacher = loginService.getTeacherInfo();
        InternshipReport internshipReport = internshipReportService.getStudentInternshipByDesId(desId);
        authorizationService.checkStudentAuthorization(loginInfo, internshipReport.getStudentId());
        internshipReportService.teacherReviewInternshipReport(desId, ScoreUtil.textToScore(reportScore),
                                                              teacher, teacherReview);
        return true;
    }
}
