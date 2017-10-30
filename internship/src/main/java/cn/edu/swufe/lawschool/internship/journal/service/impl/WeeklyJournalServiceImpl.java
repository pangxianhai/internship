package cn.edu.swufe.lawschool.internship.journal.service.impl;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.InternshipNotesError;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.model.NotesType;
import cn.edu.swufe.lawschool.internship.journal.service.WeeklyJournalService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:        周记服务</p>
 * <p>Description:  周记服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("weeklyJournalService")
public class WeeklyJournalServiceImpl extends InternshipNotesServiceImpl implements WeeklyJournalService {

    @Autowired
    FlowService flowService;

    @Autowired
    TutorService tutorService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    public void studentWriteWeeklyJournal (Long userId, Long beginDay, Long endDay, String notesContent) {
        writeInternshipNotes(userId, beginDay, endDay, notesContent, null, NotesType.WEEKLY_JOURNAL);
    }

    public InternshipNotes getWeeklyJournalByDestId (String journalDestId) {
        if (StringUtil.isEmpty(journalDestId)) {
            return null;
        }
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setDesId(journalDestId);
        internshipNotes.setNotesType(NotesType.WEEKLY_JOURNAL);
        return selectOne(internshipNotes);
    }

    public List<InternshipNotes> getWeeklyJournal (InternshipNotes internshipNotes, Page page) {
        internshipNotes.setNotesType(NotesType.WEEKLY_JOURNAL);
        return select(internshipNotes, page);
    }

    public void updateWeeklyJournal (String destId, String weeklyContent, String updateBy) {
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setDesId(destId);
        internshipNotes.setNotesContent(weeklyContent);
        update(internshipNotes, updateBy);
    }

    public Integer getStudentWeeklyJournalCount (Long studentId) {
        return getStudentInternshipNotesCount(studentId, NotesType.WEEKLY_JOURNAL);
    }

    public void tutorReviewWeeklyJournal (String internshipDesId, Long tutorUserId, String tutorRemark,
            String updateBy) {
        InternshipNotes _internshipNotes = getWeeklyJournalByDestId(internshipDesId);
        if (_internshipNotes == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        Tutor tutor = tutorService.getTutorByUserId(tutorUserId);
        if (tutor == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        Student student = studentService.getStudentById(_internshipNotes.getStudentId());
        if (!student.getTutorId().equals(tutor.getId())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (StringUtil.isEmpty(tutorRemark)) {
            throw new InternshipException(InternshipNotesError.REVIEW_CONTENT_EMPTY);
        }
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setId(_internshipNotes.getId());
        internshipNotes.setTutorReviewerId(tutor.getUserId());
        internshipNotes.setTutorReviewer(tutor.getName());
        internshipNotes.setTutorRemark(tutorRemark);
        internshipNotes.setTutorRemarkTime(DateUtil.currentMilliseconds());
        update(internshipNotes, updateBy);
    }

    public void teacherReviewWeeklyJournal (String internshipDesId, Long teacherUserId, String teacherRemark,
            String updateBy) {
        InternshipNotes _internshipNotes = getWeeklyJournalByDestId(internshipDesId);
        if (_internshipNotes == null) {
            throw new InternshipException(ErrorType.PAGE_NOT_FUND);
        }
        Teacher teacher = teacherService.getTeacherByUserId(teacherUserId);
        if (teacher == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        Student student = studentService.getStudentById(_internshipNotes.getStudentId());
        if (!student.getTeacherId().equals(teacher.getId())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (StringUtil.isEmpty(teacherRemark)) {
            throw new InternshipException(InternshipNotesError.REVIEW_CONTENT_EMPTY);
        }
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setId(_internshipNotes.getId());
        internshipNotes.setTeacherReviewerId(teacher.getUserId());
        internshipNotes.setTeacherReviewer(teacher.getName());
        internshipNotes.setTeacherRemark(teacherRemark);
        internshipNotes.setTeacherRemarkTime(DateUtil.currentMilliseconds());
        update(internshipNotes, updateBy);
    }
}
