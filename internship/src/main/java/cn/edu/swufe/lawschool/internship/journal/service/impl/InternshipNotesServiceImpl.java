package cn.edu.swufe.lawschool.internship.journal.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.InternshipNotesError;
import cn.edu.swufe.lawschool.internship.journal.mapper.InternshipNotesMapper;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.journal.model.NotesType;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:       笔记服务</p>
 * <p>Description: 笔记服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipNotesServiceImpl {

    @Autowired
    private InternshipNotesMapper internshipNotesMapper;

    @Autowired
    private StudentService studentService;

    protected void update (InternshipNotes internshipNotes, String operator) {
        if (internshipNotes.getId() == null) {
            throw new InternshipException(InternshipNotesError.NOTES_ID_EMPTY);
        }
        internshipNotes.setUpdatedBy(operator);
        internshipNotes.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = internshipNotesMapper.update(internshipNotes);
        if (count <= 0) {
            throw new InternshipException(InternshipNotesError.UPDATE_NOTES_ERROR);
        }
    }

    protected void addInternshipNotes (InternshipNotes internshipNotes) {
        internshipNotes.setStatus(Status.VALID);
        int count = internshipNotesMapper.insert(internshipNotes);
        if (count <= 0) {
            throw new InternshipException(InternshipNotesError.ADD_NOTES_ERROR);
        }
    }

    protected InternshipNotes writeInternshipNotes (Long userId, Long beginDay, Long endDay,
            String notesContent, String notesReview, NotesType notesType) {
        Student student = studentService.getStudentByUserId(userId);
        studentService.checkStudentEnableInternship(student);
        hasWroteInternshipNotes(student.getId(), beginDay, endDay, notesType);
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setStudentId(student.getId());
        internshipNotes.setStudentName(student.getName());
        internshipNotes.setNotesContent(notesContent);
        internshipNotes.setNotesReview(notesReview);
        internshipNotes.setNotesType(notesType);
        internshipNotes.setBeginDay(beginDay);
        internshipNotes.setEndDay(endDay);
        internshipNotes.setCreatedBy(student.getName());
        addInternshipNotes(internshipNotes);
        return internshipNotes;
    }

    protected List<InternshipNotes> select (InternshipNotes internshipNotes, Page page) {
        List<InternshipNotes> internshipNotesList = internshipNotesMapper.select(internshipNotes, page);
        internshipNotesList.forEach(notes -> {
            notes.setStudent(studentService.getStudentById(notes.getStudentId()));
        });
        return internshipNotesList;
    }

    protected Integer getStudentInternshipNotesCount (Long studentId, NotesType notesType) {
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setStudentId(studentId);
        internshipNotes.setNotesType(notesType);
        Page page = new Page(1, 1, Order.formString("id.ASC"));
        internshipNotesMapper.select(internshipNotes, page);
        return page.getTotalRecord();
    }

    protected InternshipNotes selectOne (InternshipNotes internshipNotes) {
        List<InternshipNotes> internshipNotesList = internshipNotesMapper.select(internshipNotes);
        if (CollectionUtil.isNotEmpty(internshipNotesList)) {
            return internshipNotesList.get(0);
        } else {
            return null;
        }
    }

    private void hasWroteInternshipNotes (Long studentId, Long beginDay, Long endDay, NotesType notesType) {
        InternshipNotes internshipNotes = new InternshipNotes();
        internshipNotes.setStudentId(studentId);
        internshipNotes.setBeginDay(beginDay);
        internshipNotes.setEndDay(endDay);
        internshipNotes.setNotesType(notesType);
        List<InternshipNotes> internshipNotesList = internshipNotesMapper.select(internshipNotes);
        if (CollectionUtil.isNotEmpty(internshipNotesList)) {
            throw new InternshipException(InternshipNotesError.ADD_NOTES_DUPLICATE);
        }
    }
}


