package cn.edu.swufe.lawschool.internship.android.bussiness.flow.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;

/**
 * Created on 2017年05月03
 * <p>Title:       流程记录</p>
 * <p>Description: 流程记录</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class FlowRecord extends BaseModel {

    /**
     * 学生ID
     */
    Long studentId;

    /**
     * 学生姓名
     */
    String studentName;

    /**
     * 流程类型
     */
    FlowType flowType;

    /**
     * 流程顺序
     */
    Integer flowOrder;

    /**
     * 目标Id 对应签到Id／请假Id
     */
    Long targetId;

    /**
     * 目标加密Id
     */
    String targetDestId;

    /**
     * 该步骤状态
     */
    FlowStatus flowStatus;

    /**
     * 操作者的用户id
     */
    Long operateUserId;

    /**
     * 操作者姓名
     */
    String operateName;

    /**
     * 操作者用户类型
     */
    OperateUserType operateUserType;

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

    public FlowType getFlowType () {
        return flowType;
    }

    public void setFlowType (FlowType flowType) {
        this.flowType = flowType;
    }

    public Integer getFlowOrder () {
        return flowOrder;
    }

    public void setFlowOrder (Integer flowOrder) {
        this.flowOrder = flowOrder;
    }

    public Long getTargetId () {
        return targetId;
    }

    public void setTargetId (Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetDestId () {
        return targetDestId;
    }

    public void setTargetDestId (String targetDestId) {
        this.targetDestId = targetDestId;
    }

    public FlowStatus getFlowStatus () {
        return flowStatus;
    }

    public void setFlowStatus (FlowStatus flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Long getOperateUserId () {
        return operateUserId;
    }

    public void setOperateUserId (Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateName () {
        return operateName;
    }

    public void setOperateName (String operateName) {
        this.operateName = operateName;
    }

    public OperateUserType getOperateUserType () {
        return operateUserType;
    }

    public void setOperateUserType (OperateUserType operateUserType) {
        this.operateUserType = operateUserType;
    }

    public boolean isPass () {
        return FlowStatus.PASS.equals(this.flowStatus);
    }

    public boolean isNoPass () {
        return FlowStatus.NO_PASS.equals(this.flowStatus);
    }

    public boolean isApply () {
        return FlowStatus.APPLY.equals(this.flowStatus);
    }

    public boolean isNeedExamine () {
        return FlowStatus.NEED_EXAMINE.equals(this.flowStatus);
    }
}
