package cn.edu.swufe.lawschool.internship.web.controller.journal;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.service.DiaryJournalService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
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
 * Created on 2015年11月24
 * <p>Title:       日志controller</p>
 * <p>Description: 日志controller</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student/diaryJournal")
public class DiaryJournalController extends InternshipNotesBaseController {

    @Logger
    Log log;

    @Autowired
    DiaryJournalService diaryJournalService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentDiaryJournalList (ModelMap modelMap, InternshipNotes internshipNotes) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isStudent()) {
            internshipNotes.setStudentId(loginService.getStudentInfo().getId());
            internshipNotes.setStudentName(loginInfo.getName());
        }
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/diaryJournalList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object studentDiaryJournalListData (InternshipNotes internshipNotes,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        super.addInternshipNotesQueryCondition(internshipNotes);
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("begin_day.DESC"));
        List<InternshipNotes> internshipNoteList = diaryJournalService.getDiaryJournal(internshipNotes, page);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("internshipNotes", internshipNoteList);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/write.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentWriteDiaryJournalToPage (ModelMap modelMap) {
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        return "student/journal/diaryJournalWrite";
    }

    @RequestMapping(value = "/write.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public Boolean studentWriteDiaryJournal (String journalDay, String journalContent, String journalReview) {
        UserInfo userInfo = loginService.getLoginUserInfo();
        diaryJournalService.studentWriteDiaryJournal(userInfo.getId(), DateUtil.parseForMills(journalDay),
                                                     journalContent, journalReview);
        return true;
    }

    @RequestMapping(value = "/detail/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentDiaryJournalDetail (ModelMap modelMap, @PathVariable String journalDestId) {
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canCallInternshipNotes(internshipNotes);
        internshipNotes.setStudent(studentService.getStudentById(internshipNotes.getStudentId()));
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/diaryJournalDetail";
    }

    @RequestMapping(value = "/update/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentUpdateDiaryJournalToPage (ModelMap modelMap, @PathVariable String journalDestId) {
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canUpdateInternshipNotes(internshipNotes);
        internshipNotes.setStudent(studentService.getStudentById(internshipNotes.getStudentId()));
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/diaryJournalUpdate";
    }

    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public Object studentUpdateDiaryJournal (String journalDestId, String journalContent,
            String journalReview) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes _internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canUpdateInternshipNotes(_internshipNotes);
        diaryJournalService.updateDiaryJournal(journalDestId, journalContent, journalReview,
                                               loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/review/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    public String teacherReviewDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canReviewInternshipNotes(internshipNotes,loginInfo);
        internshipNotes.setStudent(studentService.getStudentById(internshipNotes.getStudentId()));
        modelMap.put("internshipNotes", internshipNotes);
        return "student/journal/diaryJournalReview";
    }

    @RequestMapping(value = "/review.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    public Object teacherReviewDiaryJournal (String desId, String teacherRemark) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        diaryJournalService.teacherReviewDiaryJournal(desId, loginInfo.getId(), teacherRemark,
                                                      loginInfo.getName());
        return true;
    }
}
