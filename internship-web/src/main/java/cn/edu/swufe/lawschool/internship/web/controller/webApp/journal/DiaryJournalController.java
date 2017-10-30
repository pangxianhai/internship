package cn.edu.swufe.lawschool.internship.web.controller.webApp.journal;

import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.service.DiaryJournalService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
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

/**
 * Created on 2016年11月10
 * <p>Title:       webAPP日记controller</p>
 * <p>Description: webAPP日记controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppDiaryJournalController")
@RequestMapping(value = "/webApp/student/diaryJournal")
public class DiaryJournalController extends InternshipNotesBaseController {

    @Autowired
    DiaryJournalService diaryJournalService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String diaryJournalList (ModelMap modelMap, InternshipNotes internshipNotes, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/diaryJournal/diaryJournalList";
    }

    @RequestMapping(value = "/detail/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE, UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String studentDiaryJournalDetail (ModelMap modelMap, @PathVariable String journalDestId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canCallInternshipNotes(internshipNotes);
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/diaryJournal/diaryJournalDetail";
    }

    @RequestMapping(value = "/write.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentWriteDiaryJournalToPage (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = loginService.getStudentInfo();
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/diaryJournal/diaryJournalWrite";
    }

    @RequestMapping(value = "/update/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String studentUpdateDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId,
            String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canUpdateInternshipNotes(internshipNotes);
        modelMap.put("internshipNotes", internshipNotes);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/journal/diaryJournal/diaryJournalUpdate";
    }

    @RequestMapping(value = "/review/{journalDestId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE })
    public String teacherReviewDiaryJournal (ModelMap modelMap, @PathVariable String journalDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        InternshipNotes internshipNotes = diaryJournalService.getDiaryJournalByDestId(journalDestId);
        super.canReviewInternshipNotes(internshipNotes, loginInfo);
        modelMap.put("internshipNotes", internshipNotes);
        return "webApp/journal/diaryJournal/diaryJournalReview";
    }

}
