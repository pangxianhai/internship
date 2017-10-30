package cn.edu.swufe.lawschool.internship.flow.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;

/**
 * Created on 2015年11月17
 * <p>Title:       流程记录</p>
 * <p>Description: 流程记录</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class FlowRecord extends BaseDO {

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

    public FlowType getFlowType () {
        return flowType;
    }

    public void setFlowType (FlowType flowType) {
        this.flowType = flowType;
    }

    public void setFlowTypeCode (Integer flowType) {
        if (flowType != null) {
            setFlowType(FlowType.parse(flowType));
        }
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
        if (StringUtil.isEmpty(targetDestId) && targetId != null) {
            targetDestId = AESUtil.encrypt(String.valueOf(targetId));
        }
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
        this.studentName = StringUtil.trimToNull(studentName);
    }

    public String getTargetDestId () {
        if (StringUtil.isEmpty(targetDestId) && targetId != null) {
            targetDestId = AESUtil.encrypt(String.valueOf(targetId));
        }
        return targetDestId;
    }

    public void setTargetDestId (String targetDestId) {
        if (StringUtil.isNotEmpty(targetDestId)) {
            this.targetId = NumberUtil.parseLong(AESUtil.decrypt(targetDestId));
            if (this.targetId != null) {
                this.targetDestId = targetDestId;
            }
        }
    }

    public OperateUserType getOperateUserType () {
        return operateUserType;
    }

    public void setOperateUserType (OperateUserType operateUserType) {
        this.operateUserType = operateUserType;
    }

    public Boolean getIsLeave () {
        return FlowType.LEAVE.equals(flowType);
    }

    public Boolean getIsAttend () {
        return FlowType.ATTEND.equals(flowType);
    }

    public Boolean getIsWeeklyJournal () {
        return FlowType.WEEKLY_JOURNAL.equals(flowType);
    }

    public Boolean getIsAssessment () {
        return FlowType.ASSESSMENT.equals(flowType);
    }

    public Boolean getIsReport () {
        return FlowType.REPORT.equals(flowType);
    }

    public Boolean getIsDiaryJournal () {
        return FlowType.DIARY_JOURNAL.equals(flowType);
    }

    public Boolean getIsPass () {
        return FlowStatus.PASS.equals(flowStatus);
    }

    public Boolean getIsNoPass () {
        return FlowStatus.NO_PASS.equals(flowStatus);
    }

    public Boolean getIsApply () {
        return FlowStatus.APPLY.equals(flowStatus);
    }

    public Boolean getIsNeedExamine () {
        return FlowStatus.NEED_EXAMINE.equals(flowStatus);
    }
}