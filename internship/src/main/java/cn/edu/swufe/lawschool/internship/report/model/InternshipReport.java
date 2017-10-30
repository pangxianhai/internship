package cn.edu.swufe.lawschool.internship.report.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.util.ScoreUtil;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2015年11月30
 * <p>Title:       实习报告</p>
 * <p>Description: 实习报告实体</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipReport extends BaseDO {

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

    public String getSubject () {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getReportScoreStr () {
        return ScoreUtil.scoreToText(this.reportScore);
    }
}

