package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.report.service.InternshipReportService;
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

/**
 * Created on 2016年11月26
 * <p>Title:       实习报告</p>
 * <p>Description: web app 实习报告 controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppInternshipReportController")
@RequestMapping(value = "/webApp/student/report")
public class InternshipReportController {

    @Autowired
    LoginService loginService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    InternshipReportService internshipReportService;

    @RequestMapping(value = "/detail/{reportDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String getInternshipReport (ModelMap modelMap, @PathVariable String reportDesId,
            String returnUrl) {
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
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/internshipReport/internshipReport";
    }

    @RequestMapping(value = "/create.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentCreateInternshipReport (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        modelMap.put("action", "create");
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/internshipReport/internshipReport";
    }
}
