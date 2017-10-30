package cn.edu.swufe.lawschool.internship.android.bussiness.assessment.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.constants.Confirm;

/**
 * Created on 2017年05月03
 * <p>Title:       实习评价实体</p>
 * <p>Description: 实习评价实体</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Assessment extends BaseModel {
    /**
     * 学生ID
     */
    Long studentId;

    /**
     * 学生姓名
     */
    String studentName;

    /**
     * 实习单位考评者用户Id
     */
    Long companyReviewerId;

    /**
     * 实习单位考评者姓名
     */
    String companyReviewer;

    /**
     * 实习单位考评意见
     */
    String companyRemark;

    /**
     * 实习单位考评时间
     */
    Long companyRemarkTime;

    /**
     * 导师考评者用户Id
     */
    Long tutorReviewerId;

    /**
     * 导师考评者姓名
     */
    String tutorReviewer;

    /**
     * 导师指导考评意见
     */
    String tutorRemark;

    /**
     * 导师指导建议分数
     */
    Integer tutorScore;

    /**
     * 导师考评时间
     */
    Long tutorRemarkTime;

    /**
     * 带队老师确认者用户ID
     */
    Long teacherConfirmUserId;

    /**
     * 带队老师确认者姓名
     */
    String teacherConfirmUser;

    /**
     * 带队老师是否确认
     */
    Confirm teacherConfirm;

    /**
     * 带队老师确认时间
     */
    Long teacherConfirmTime;

    public Long getStudentId () {
        return studentId;
    }

    public void setStudentId (Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName () {
        return studentName;
    }

    public void setStudentName (String studentName) {
        this.studentName = studentName;
    }

    public Long getCompanyReviewerId () {
        return companyReviewerId;
    }

    public void setCompanyReviewerId (Long companyReviewerId) {
        this.companyReviewerId = companyReviewerId;
    }

    public String getCompanyReviewer () {
        return companyReviewer;
    }

    public void setCompanyReviewer (String companyReviewer) {
        this.companyReviewer = companyReviewer;
    }

    public String getCompanyRemark () {
        return companyRemark;
    }

    public void setCompanyRemark (String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public Long getCompanyRemarkTime () {
        return companyRemarkTime;
    }

    public void setCompanyRemarkTime (Long companyRemarkTime) {
        this.companyRemarkTime = companyRemarkTime;
    }

    public Long getTutorReviewerId () {
        return tutorReviewerId;
    }

    public void setTutorReviewerId (Long tutorReviewerId) {
        this.tutorReviewerId = tutorReviewerId;
    }

    public String getTutorReviewer () {
        return tutorReviewer;
    }

    public void setTutorReviewer (String tutorReviewer) {
        this.tutorReviewer = tutorReviewer;
    }

    public String getTutorRemark () {
        return tutorRemark;
    }

    public void setTutorRemark (String tutorRemark) {
        this.tutorRemark = tutorRemark;
    }

    public Integer getTutorScore () {
        return tutorScore;
    }

    public void setTutorScore (Integer tutorScore) {
        this.tutorScore = tutorScore;
    }

    public Long getTutorRemarkTime () {
        return tutorRemarkTime;
    }

    public void setTutorRemarkTime (Long tutorRemarkTime) {
        this.tutorRemarkTime = tutorRemarkTime;
    }

    public Long getTeacherConfirmUserId () {
        return teacherConfirmUserId;
    }

    public void setTeacherConfirmUserId (Long teacherConfirmUserId) {
        this.teacherConfirmUserId = teacherConfirmUserId;
    }

    public String getTeacherConfirmUser () {
        return teacherConfirmUser;
    }

    public void setTeacherConfirmUser (String teacherConfirmUser) {
        this.teacherConfirmUser = teacherConfirmUser;
    }

    public Confirm getTeacherConfirm () {
        return teacherConfirm;
    }

    public void setTeacherConfirm (
            Confirm teacherConfirm) {
        this.teacherConfirm = teacherConfirm;
    }

    public Long getTeacherConfirmTime () {
        return teacherConfirmTime;
    }

    public void setTeacherConfirmTime (Long teacherConfirmTime) {
        this.teacherConfirmTime = teacherConfirmTime;
    }
}
