package cn.edu.swufe.lawschool.internship.web.controller.journal;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.service.WeeklyJournalService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月25
 * <p>Title:       周记controller</p>
 * <p>Description: 周记controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/weeklyJournal")
public class WeeklyJournalController extends InternshipNotesBaseController {
    @Logger
    Log log;

    @Autowired
    LoginService loginService;

    @Autowired
    WeeklyJournalService weeklyJournalService;

    @Autowired
    StudentService studentService;

    @Autowired
    AttendService attendService;

    @Autowired
    TutorService tutorService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentWeeklyJournalList (ModelMap modelMap, InternshipNotes internshipNotes) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isStudent()) {
            internshipNotes.setStudentId(loginService.getStudentInfo().getId());
            internshipNotes.setStudentName(loginInfo.getName());
        }
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/weeklyJournalList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object studentWeeklyJournalListDate (InternshipNotes internshipNotes,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        super.addInternshipNotesQueryCondition(internshipNotes);
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("begin_day.DESC"));
        List<InternshipNotes> internshipNoteList = weeklyJournalService.getWeeklyJournal(internshipNotes,
                                                                                         page);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("internshipNotes", internshipNoteList);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/write.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentWriteWeeklyJournal (ModelMap modelMap) {
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        return "student/journal/weeklyJournalWrite";
    }

    @RequestMapping(value = "/write.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Boolean studentWriteWeeklyJournalPost (String beginDay, String endDay, String journalContent) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        weeklyJournalService.studentWriteWeeklyJournal(loginInfo.getId(), DateUtil.parseForMills(beginDay),
                                                       DateUtil.parseForMills(endDay), journalContent);
        return true;
    }

    @RequestMapping(value = "/detail/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentDiaryJournalDetail (ModelMap modelMap, @PathVariable String journalDestId) {
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        super.canCallInternshipNotes(internshipNotes);
        Student diaryStudent = studentService.getStudentById(internshipNotes.getStudentId());

        //周记的时间只精确到天 结束的时期需要加一天才能取到最后一天的签到信息
        List<AttendRecord> attendRecords = attendService.getAttendRecord(diaryStudent.getUserId(),
                                                                         internshipNotes.getBeginDay(),
                                                                         internshipNotes.getEndDay() + 24*3600*1000-1);
        internshipNotes.setStudent(diaryStudent);
        modelMap.put("attendRecords", attendRecords);
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/weeklyJournalDetail";
    }

    @RequestMapping(value = "/update/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentUpdateDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId) {
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        super.canUpdateInternshipNotes(internshipNotes);
        internshipNotes.setStudent(studentService.getStudentById(internshipNotes.getStudentId()));
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/weeklyJournalUpdate";
    }

    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Boolean studentUpdateDiaryJournalPost (String desId, String weeklyContent) {
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(desId);
        UserInfo loginInfo = loginService.getLoginUserInfo();
        super.canUpdateInternshipNotes(internshipNotes);
        weeklyJournalService.updateWeeklyJournal(desId, weeklyContent, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/review/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE })
    public String reviewDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId) {
        InternshipNotes internshipNotes = weeklyJournalService.getWeeklyJournalByDestId(journalDestId);
        UserInfo loginInfo = loginService.getLoginUserInfo();
        super.canReviewInternshipNotes(internshipNotes,loginInfo);
        Student student = studentService.getStudentById(internshipNotes.getStudentId());
        List<AttendRecord> attendRecords = attendService.getAttendRecord(student.getUserId(),
                                                                         internshipNotes.getBeginDay(),
                                                                         internshipNotes.getEndDay());
        internshipNotes.setStudent(student);
        modelMap.put("attendRecords", attendRecords);
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/weeklyJournalReview";
    }

    @RequestMapping(value = "/review.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object tutorReviewDiaryJournal (String desId, String tutorRemark, String teacherRemark) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTutor()) {
            weeklyJournalService.tutorReviewWeeklyJournal(desId, loginInfo.getId(), tutorRemark,
                                                          loginInfo.getName());
        } else if (loginInfo.isTeacher()) {
            weeklyJournalService.teacherReviewWeeklyJournal(desId, loginInfo.getId(), teacherRemark,
                                                            loginInfo.getName());
        }
        return true;
    }

}
