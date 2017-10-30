package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.assessment.service.AssessmentService;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationService;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.StudentError;
import cn.edu.swufe.lawschool.internship.journal.service.DiaryJournalService;
import cn.edu.swufe.lawschool.internship.journal.service.WeeklyJournalService;
import cn.edu.swufe.lawschool.internship.report.service.InternshipReportService;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.model.StudentParam;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月30
 * <p>Title:       学生实习信息Controller</p>
 * <p>Description: 学生实习信息Controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/internship")
public class InternshipInfoController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    StudentService studentService;

    @Autowired
    AttendService attendService;

    @Autowired
    DiaryJournalService diaryJournalService;

    @Autowired
    WeeklyJournalService weeklyJournalService;

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    InternshipReportService internshipReportService;

    @RequestMapping(value = "/info.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    public String internshipStatistics (ModelMap modelMap) {
        return "student/internshipInfo";
    }

    @RequestMapping(value = "/info.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    @ResponseBody
    public Object internshipStatisticsData (StudentParam studentParam,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        authorizationService.resetStudentCondition(loginInfo, studentParam);
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.asc"));
        List<Student> students = studentService.getStudent(studentParam, page);
        if (CollectionUtil.isNotEmpty(students)) {
            students.forEach(student -> {
                studentInternshipStatistics(student);
            });
        }
        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("students", students);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/internshipOfStudent.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.STUDENT_CODE })
    @ResponseBody
    public Object getStudentInternshipStatistics (String studentDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = null;
        if (loginInfo.isStudent()) {
            student = loginService.getStudentInfo();
        } else {
            student = studentService.getStudentByDesId(studentDesId);
        }
        if (student == null) {
            throw new InternshipException(StudentError.STUDENT_NOT_EXIST);
        }
        authorizationService.checkStudentAuthorization(loginInfo, student);
        studentInternshipStatistics(student);
        return student;
    }

    private void studentInternshipStatistics (Student student) {
        student.setDiaryJournalCount(diaryJournalService.getStudentDiaryJournalCount(student.getId()));
        student.setWeeklyJournalCount(weeklyJournalService.getStudentWeeklyJournalCount(student.getId()));
        student.setAssessment(assessmentService.getAssessmentByStudentId(student.getId()));
        student.setEvaluation(evaluationService.getEvaluationByStudentId(student.getId()));
        Integer attendCount = attendService.getAttendUserCount(student.getUserId());
        student.setAttendedCount(attendCount / 2.0);
        student.setInternshipReport(internshipReportService.getStudentInternshipReport(student.getId()));
    }
}
