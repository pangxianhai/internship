package cn.edu.swufe.lawschool.internship.journal.service.impl;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.InternshipNotesError;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.model.NotesType;
import cn.edu.swufe.lawschool.internship.journal.service.DiaryJournalService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:       日记服务</p>
 * <p>Description: 日记服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("diaryJournalService")
public class DiaryJournalServiceImpl extends InternshipNotesServiceImpl implements DiaryJournalService {

    @Autowired
    FlowService flowService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    public void studentWriteDiaryJournal (Long userId, Long day, String notesContent, String notesReview) {
        writeInternshipNotes(userId, day, day, notesContent, notesReview, NotesType.DIARY_JOURNAL);
    }

    public InternshipNotes getDiaryJournalByDestId (String journalDestId) {
        if (StringUtil.isEmpty(journalDestId)) {
            return null;
        }
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setDesId(journalDestId);
        internshipNotes.setNotesType(NotesType.DIARY_JOURNAL);
        return selectOne(internshipNotes);
    }

    public List<InternshipNotes> getDiaryJournal (InternshipNotes internshipNotes, Page page) {
        internshipNotes.setNotesType(NotesType.DIARY_JOURNAL);
        return select(internshipNotes, page);
    }

    public void updateDiaryJournal (String destId, String journalContent, String journalReview,
            String operator) {
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setDesId(destId);
        internshipNotes.setNotesContent(journalContent);
        internshipNotes.setNotesReview(journalReview);
        update(internshipNotes, operator);
    }

    public Integer getStudentDiaryJournalCount (Long studentId) {
        return getStudentInternshipNotesCount(studentId, NotesType.DIARY_JOURNAL);
    }

    public void teacherReviewDiaryJournal (String internshipDesId, Long teacherUserId, String teacherRemark,
            String operator) {
        InternshipNotes _internshipNotes = getDiaryJournalByDestId(internshipDesId);
        if (_internshipNotes == null) {
            throw new InternshipException(InternshipNotesError.NOTES_NOT_EXIST);
        }
        Teacher teacher = teacherService.getTeacherByUserId(teacherUserId);
        if (teacher == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        Student student = studentService.getStudentById(_internshipNotes.getStudentId());
        if (!student.getTeacherId().equals(teacher.getId())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setId(_internshipNotes.getId());
        internshipNotes.setTeacherReviewerId(teacher.getUserId());
        internshipNotes.setTeacherReviewer(teacher.getName());
        internshipNotes.setTeacherRemark(teacherRemark);
        internshipNotes.setTeacherRemarkTime(DateUtil.currentMilliseconds());
        update(internshipNotes, operator);
    }
}
