package cn.edu.swufe.lawschool.internship.web.controller.journal;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.InternshipNotesError;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 2016年07月17
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipNotesBaseController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TutorService tutorService;

    @Autowired
    LoginService loginService;

    @Autowired
    AuthorizationService authorizationService;

    protected void canReviewInternshipNotes (InternshipNotes internshipNotes, UserInfo loginInfo) {
        if (internshipNotes == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        Student student = studentService.getStudentById(internshipNotes.getStudentId());
        if (loginInfo.isTutor()) {
            Tutor tutor = loginService.getTutorInfo();
            if (!tutor.getId().equals(student.getTutorId())) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            if (StringUtil.isNotBlank(internshipNotes.getTutorRemark())) {
                throw new InternshipException(InternshipNotesError.HAS_REVIEWED);
            }
        } else if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (!teacher.getId().equals(student.getTeacherId())) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            if (StringUtil.isNotBlank(internshipNotes.getTeacherRemark())) {
                throw new InternshipException(InternshipNotesError.HAS_REVIEWED);
            }
        } else {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
    }

    /**
     * 学生是否能修改该笔记
     * @param internshipNotes 被修改的笔记
     */
    protected void canUpdateInternshipNotes (InternshipNotes internshipNotes) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (internshipNotes == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        if (!loginInfo.isStudent()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        Student student = loginService.getStudentInfo();
        if (student == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (!internshipNotes.getStudentId().equals(student.getId())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (StringUtil.isNotEmpty(internshipNotes.getTutorRemark())) {
            throw new InternshipException(InternshipNotesError.UPDATE_NOTES_ERROR_BY_HAS_TUTOR_REVIEW);
        }
        if (StringUtil.isNotEmpty(internshipNotes.getTeacherRemark())) {
            throw new InternshipException(InternshipNotesError.UPDATE_NOTES_ERROR_BY_HAS_TEACHER_REVIEW);
        }
    }

    /**
     * 当前登陆用户是否能查看该日志
     * @param internshipNotes
     */
    protected void canCallInternshipNotes (InternshipNotes internshipNotes) {
        if (internshipNotes == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        UserInfo loginInfo = loginService.getLoginUserInfo();
        authorizationService.checkStudentAuthorization(loginInfo, internshipNotes.getStudentId());
    }

    public void addInternshipNotesQueryCondition (InternshipNotes internshipNotes) {
        if (internshipNotes.getStudent() == null) {
            internshipNotes.setStudent(new Student());
        }
        authorizationService.resetStudentCondition(loginService.getLoginUserInfo(),
                                                   internshipNotes.getStudent());
    }
}
