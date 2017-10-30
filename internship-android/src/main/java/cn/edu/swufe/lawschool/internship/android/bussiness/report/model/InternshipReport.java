package cn.edu.swufe.lawschool.internship.android.bussiness.report.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;

/**
 * Created on 2017年05月03
 * <p>Title:       实习报告</p>
 * <p>Description: 实习报告</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipReport extends BaseModel {
    /**
     * 实习报告题目
     */
    String subject;

    /**
     * 学生ID
     */
    Long studentId;

    /**
     * 学生姓名
     */
    String studentName;

    /**
     * 实习报告成绩
     */
    Integer reportScore;

    /**
     * 评阅者(带队老师)用户ID
     */
    Long reviewerId;

    /**
     * 评阅者(带队老师)姓名
     */
    String reviewer;

    /**
     * 评语
     */
    String remark;

    /**
     * 评阅时间
     */
    Long remarkTime;

    public String getSubject () {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

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

    public Integer getReportScore () {
        return reportScore;
    }

    public void setReportScore (Integer reportScore) {
        this.reportScore = reportScore;
    }

    public Long getReviewerId () {
        return reviewerId;
    }

    public void setReviewerId (Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewer () {
        return reviewer;
    }

    public void setReviewer (String reviewer) {
        this.reviewer = reviewer;
    }

    public String getRemark () {
        return remark;
    }

    public void setRemark (String remark) {
        this.remark = remark;
    }

    public Long getRemarkTime () {
        return remarkTime;
    }

    public void setRemarkTime (Long remarkTime) {
        this.remarkTime = remarkTime;
    }
}
