package cn.edu.swufe.lawschool.internship.user.model;

import java.io.Serializable;

/**
 * Created on 2017年02月10
 * <p>Title:       当前用户具有操作权限列表</p>
 * <p>Description: 当前用户具有操作权限列表</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class OperateAuthorization implements Serializable {

    /**
     * 是否能修改实习但闻
     */
    private boolean canChangeCompany;

    /**
     * 是否能修改实习导师
     */
    private boolean canChangeTutor;

    /**
     * 是否能修改带队老师
     */
    private boolean canChangeTeacher;

    /**
     * 是否能删除学生
     */
    private boolean canDeleteStudent;

    /**
     * 是否能修改学生信息
     */
    private boolean canUpdateStudent;

    /**
     * 是否能发放学生质量评价表
     */
    private boolean canSendEvaluation;

    /**
     * 是否能发放学生实习评价表
     */
    private boolean canSendAssessment;

    /**
     * 是否能评价学生实习评价表
     */
    private boolean canReviewAssessment;

    /**
     * 是否能创建实习报告
     */
    private boolean canCreateReport;

    public boolean isCanChangeCompany () {
        return canChangeCompany;
    }

    public void setCanChangeCompany (boolean canChangeCompany) {
        this.canChangeCompany = canChangeCompany;
    }

    public boolean isCanChangeTutor () {
        return canChangeTutor;
    }

    public void setCanChangeTutor (boolean canChangeTutor) {
        this.canChangeTutor = canChangeTutor;
    }

    public boolean isCanChangeTeacher () {
        return canChangeTeacher;
    }

    public void setCanChangeTeacher (boolean canChangeTeacher) {
        this.canChangeTeacher = canChangeTeacher;
    }

    public boolean isCanDeleteStudent () {
        return canDeleteStudent;
    }

    public void setCanDeleteStudent (boolean canDeleteStudent) {
        this.canDeleteStudent = canDeleteStudent;
    }

    public boolean isCanUpdateStudent () {
        return canUpdateStudent;
    }

    public void setCanUpdateStudent (boolean canUpdateStudent) {
        this.canUpdateStudent = canUpdateStudent;
    }

    public boolean isCanSendEvaluation () {
        return canSendEvaluation;
    }

    public void setCanSendEvaluation (boolean canSendEvaluation) {
        this.canSendEvaluation = canSendEvaluation;
    }

    public boolean isCanSendAssessment () {
        return canSendAssessment;
    }

    public void setCanSendAssessment (boolean canSendAssessment) {
        this.canSendAssessment = canSendAssessment;
    }

    public boolean isCanCreateReport () {
        return canCreateReport;
    }

    public void setCanCreateReport (boolean canCreateReport) {
        this.canCreateReport = canCreateReport;
    }

    public boolean isCanReviewAssessment () {
        return canReviewAssessment;
    }

    public void setCanReviewAssessment (boolean canReviewAssessment) {
        this.canReviewAssessment = canReviewAssessment;
    }
}
