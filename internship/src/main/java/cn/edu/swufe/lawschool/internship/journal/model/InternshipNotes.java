package cn.edu.swufe.lawschool.internship.journal.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2015年11月23
 * <p>Title:       实习笔记</p>
 * <p>Description: 实习笔记实体</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipNotes extends BaseDO {

    /**
     * 学生Id
     */
    Long studentId;

    /**
     * 学生姓名
     */
    String studentName;

    /**
     * 学生信息
     */
    Student student;

    /**
     * 笔记开始时间
     */
    Long beginDay;

    /**
     * 笔记结束时间
     */
    Long endDay;

    /**
     * 笔记内容
     */
    String notesContent;

    /**
     * 笔记心得
     */
    String notesReview;

    /**
     * 笔记类型
     */
    NotesType notesType;

    /**
     * 导师评价者用户Id
     */
    Long tutorReviewerId;

    /**
     * 导师评价者姓名
     */
    String tutorReviewer;

    /**
     * 导师评语
     */
    String tutorRemark;

    /**
     * 导师评价时间
     */
    Long tutorRemarkTime;

    /**
     * 带队老师评价用户ID
     */
    Long teacherReviewerId;

    /**
     * 带队老师评价者姓名
     */
    String teacherReviewer;

    /**
     * 带队老师评语
     */
    String teacherRemark;

    /**
     * 带队老师评价时间
     */
    Long teacherRemarkTime;

    public Long getStudentId () {
        return studentId;
    }

    public void setStudentId (Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName () {
        return StringUtil.trimToNull(studentName);
    }

    public void setStudentName (String studentName) {
        this.studentName = studentName;
    }

    public Long getBeginDay () {
        return beginDay;
    }

    public void setBeginDay (Long beginDay) {
        this.beginDay = beginDay;
    }

    public void setBeginDayStr (String beginDay) {
        this.beginDay = DateUtil.parseForMills(beginDay);
    }

    public Long getEndDay () {
        return endDay;
    }

    public void setEndDay (Long endDay) {
        this.endDay = endDay;
    }

    public void setEndDayStr (String endDay) {
        this.endDay = DateUtil.parseForMills(endDay);
    }

    public String getNotesContent () {
        return notesContent;
    }

    public void setNotesContent (String notesContent) {
        this.notesContent = notesContent;
    }

    public String getNotesReview () {
        return notesReview;
    }

    public void setNotesReview (String notesReview) {
        this.notesReview = notesReview;
    }

    public String getTutorRemark () {
        return tutorRemark;
    }

    public void setTutorRemark (String tutorRemark) {
        this.tutorRemark = tutorRemark;
    }

    public NotesType getNotesType () {
        return notesType;
    }

    public void setNotesType (NotesType notesType) {
        this.notesType = notesType;
    }

    public String getTeacherRemark () {
        return teacherRemark;
    }

    public void setTeacherRemark (String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }

    public Long getTutorReviewerId () {
        return tutorReviewerId;
    }

    public void setTutorReviewerId (Long tutorReviewerId) {
        this.tutorReviewerId = tutorReviewerId;
    }

    public Long getTutorRemarkTime () {
        return tutorRemarkTime;
    }

    public void setTutorRemarkTime (Long tutorRemarkTime) {
        this.tutorRemarkTime = tutorRemarkTime;
    }

    public Long getTeacherReviewerId () {
        return teacherReviewerId;
    }

    public void setTeacherReviewerId (Long teacherReviewerId) {
        this.teacherReviewerId = teacherReviewerId;
    }

    public Long getTeacherRemarkTime () {
        return teacherRemarkTime;
    }

    public void setTeacherRemarkTime (Long teacherRemarkTime) {
        this.teacherRemarkTime = teacherRemarkTime;
    }

    public String getTutorReviewer () {
        return tutorReviewer;
    }

    public void setTutorReviewer (String tutorReviewer) {
        this.tutorReviewer = tutorReviewer;
    }

    public String getTeacherReviewer () {
        return teacherReviewer;
    }

    public void setTeacherReviewer (String teacherReviewer) {
        this.teacherReviewer = teacherReviewer;
    }

    public Student getStudent () {
        return student;
    }

    public void setStudent (Student student) {
        this.student = student;
    }
}

