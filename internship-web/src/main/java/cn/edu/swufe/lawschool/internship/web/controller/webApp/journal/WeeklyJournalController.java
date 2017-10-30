package cn.edu.swufe.lawschool.internship.web.controller.webApp.journal;

import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.service.WeeklyJournalService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.controller.journal.InternshipNotesBaseController;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created on 2016年11月10
 * <p>Title:       webApp周记controller</p>
 * <p>Description: webApp周记controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppWeeklyJournalController")
@RequestMapping(value = "/webApp/student/weeklyJournal")
public class WeeklyJournalController extends InternshipNotesBaseController {

    @Autowired
    LoginService loginService;

    @Autowired
    WeeklyJournalService weeklyJournalService;

    @Autowired
    StudentService studentService;

    @Autowired
    AttendService attendService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String weeklyJournalList (ModelMap modelMap, InternshipNotes internshipNotes, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/weeklyJournal/weeklyJournalList";
    }

    @RequestMapping(value = "/detail/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentDiaryJournalDetail (ModelMap modelMap, @PathVariable String journalDestId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        super.canCallInternshipNotes(internshipNotes);
        Student diaryStudent = studentService.getStudentById(internshipNotes.getStudentId());
        List<AttendRecord> attendRecords = attendService.getAttendRecord(diaryStudent.getUserId(),
                                                                         internshipNotes.getBeginDay(),
                                                                         internshipNotes.getEndDay() + 24*3600*1000-1);
        modelMap.put("attendRecords", attendRecords);
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        try {
            super.canReviewInternshipNotes(internshipNotes, loginInfo);
            modelMap.put("canReview", true);
        } catch (Exception e) {
            modelMap.put("canReview", false);
        }
        return "webApp/journal/weeklyJournal/weeklyJournalDetail";
    }

    @RequestMapping(value = "/write.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentWriteWeeklyJournal (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/weeklyJournal/weeklyJournalWrite";
    }

    @RequestMapping(value = "/update/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentUpdateDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        super.canUpdateInternshipNotes(internshipNotes);
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/weeklyJournal/weeklyJournalUpdate";
    }

    @RequestMapping(value = "/review/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE })
    public String reviewDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId,
            String returnUrl) {
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        UserInfo loginInfo = loginService.getLoginUserInfo();
        super.canReviewInternshipNotes(internshipNotes, loginInfo);
        Student student = studentService.getStudentById(internshipNotes.getStudentId());
        List<AttendRecord> attendRecords = attendService.getAttendRecord(student.getUserId(),
                                                                         internshipNotes.getBeginDay(),
                                                                         internshipNotes.getEndDay());
        modelMap.put("attendRecords", attendRecords);
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/weeklyJournal/weeklyJournalReview";
    }

}
