package cn.edu.swufe.lawschool.internship.flow.service.impl;

import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.exception.FlowError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.flow.mapper.FlowMapper;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2015年11月19
 * <p>Title:       流程服务</p>
 * <p>Description: 流程服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowMapper flowMapper;

    public void addAttendFlow (AttendRecord attendRecord, Integer flowOrder, Long operatorUserId,
                               String operatorName, OperateUserType operateUserType) {
        FlowRecord flow = new FlowRecord();
        flow.setStudentId(attendRecord.getStudent().getId());
        flow.setStudentName(attendRecord.getStudent().getName());
        flow.setFlowType(FlowType.ATTEND);
        flow.setTargetId(attendRecord.getId());
        flow.setFlowStatus(FlowStatus.NEED_EXAMINE);
        flow.setFlowOrder(flowOrder);
        flow.setOperateUserId(operatorUserId);
        flow.setOperateName(operatorName);
        flow.setOperateUserType(operateUserType);
        flow.setCreatedBy(attendRecord.getCreatedBy());
        addFlow(flow);
    }

    public void addLeaveFlow (LeaveRecord leaveRecord, Integer flowOrder, Long operatorUserId,
                              String operatorName, OperateUserType operateUserType) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setStudentId(leaveRecord.getStudent().getId());
        flowRecord.setStudentName(leaveRecord.getStudent().getName());
        flowRecord.setFlowType(FlowType.LEAVE);
        flowRecord.setTargetId(leaveRecord.getId());
        flowRecord.setOperateUserId(operatorUserId);
        flowRecord.setOperateName(operatorName);
        if (flowOrder.intValue() == 1) {
            flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        } else {
            flowRecord.setFlowStatus(FlowStatus.APPLY);
        }
        flowRecord.setFlowOrder(flowOrder);
        flowRecord.setOperateUserType(operateUserType);
        flowRecord.setCreatedBy(leaveRecord.getCreatedBy());
        addFlow(flowRecord);
    }

    public void addInternshipReportFlow (InternshipReport internshipReport, Integer flowOrder,
                                         Long operatorUserId, String operatorName,
                                         OperateUserType operateUserType) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setStudentId(internshipReport.getStudentId());
        flowRecord.setStudentName(internshipReport.getStudentName());
        flowRecord.setFlowType(FlowType.REPORT);
        flowRecord.setTargetId(internshipReport.getId());
        flowRecord.setOperateUserId(operatorUserId);
        flowRecord.setOperateName(operatorName);
        flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        flowRecord.setFlowOrder(flowOrder);
        flowRecord.setOperateUserType(operateUserType);
        flowRecord.setCreatedBy(internshipReport.getCreatedBy());
        addFlow(flowRecord);
    }

    public void addAssessmentFlow (Assessment assessment, Integer flowOrder, Long operatorUserId,
                                   String operatorName, OperateUserType operateUserType) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setStudentId(assessment.getStudentId());
        flowRecord.setStudentName(assessment.getStudentName());
        flowRecord.setFlowType(FlowType.ASSESSMENT);
        flowRecord.setTargetId(assessment.getId());
        flowRecord.setOperateUserId(operatorUserId);
        flowRecord.setOperateName(operatorName);
        if (flowOrder.intValue() == 1) {
            flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        } else {
            flowRecord.setFlowStatus(FlowStatus.APPLY);
        }
        flowRecord.setFlowOrder(flowOrder);
        flowRecord.setOperateUserType(operateUserType);
        flowRecord.setCreatedBy(assessment.getCreatedBy());
        addFlow(flowRecord);
    }

    public void addInternshipNotesFlow (InternshipNotes internshipNotes, FlowType flowType, Integer flowOrder,
                                        Long operatorUserId, String operatorName,
                                        OperateUserType operateUserType) {
        FlowRecord flow = new FlowRecord();
        flow.setStudentId(internshipNotes.getStudentId());
        flow.setStudentName(internshipNotes.getStudentName());
        flow.setFlowType(flowType);
        flow.setTargetId(internshipNotes.getId());
        flow.setFlowStatus(FlowStatus.NEED_EXAMINE);
        flow.setFlowOrder(flowOrder);
        flow.setOperateUserId(operatorUserId);
        flow.setOperateName(operatorName);
        flow.setOperateUserType(operateUserType);
        flow.setCreatedBy(internshipNotes.getStudentName());
        addFlow(flow);
    }

    public List<FlowRecord> getFlowRecord (FlowRecord flowRecord) {
        return flowMapper.select(flowRecord);
    }

    public List<FlowRecord> getFlowRecord (FlowRecord flowRecord, Page page) {
        return flowMapper.select(flowRecord, page);
    }

    public List<FlowRecord> getFlowRecordByTargetId (Long targetId, FlowType flowType) {
        if (targetId == null || flowType == null) {
            return null;
        }
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setTargetId(targetId);
        flowRecord.setFlowType(flowType);
        return getFlowRecord(flowRecord);
    }

    public List<FlowRecord> getFlowRecordByTargetId (String targetDesId, FlowType flowType) {
        if (StringUtil.isBlank(targetDesId) || flowType == null) {
            return null;
        }
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setTargetDestId(targetDesId);
        flowRecord.setFlowType(flowType);
        return getFlowRecord(flowRecord);
    }

    public FlowRecord getNeedExamineFlowRecordByTargetId (Long targetId, FlowType flowType) {
        if (targetId == null || flowType == null) {
            return null;
        }
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setTargetId(targetId);
        flowRecord.setFlowType(flowType);
        flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        return selectOne(flowRecord);
    }

    public List<FlowRecord> getNeedExamineFlowRecord (FlowRecord flowRecord, Page page) {
        flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        return flowMapper.select(flowRecord, page);
    }

    public void examine (Long userId, Long targetId, FlowType flowType, Integer flowOrder,
                         FlowStatus flowStatus) {
        FlowRecord flowRecord = getFlowRecordByTargetId(targetId, flowType, flowOrder);
        if (!flowRecord.getOperateUserId().equals(userId)) {
            throw new InternshipException(FlowError.CANNOT_EXAMINE);
        }
        if (flowOrder > 1) {
            FlowRecord preFlow = getFlowRecordByTargetId(targetId, flowType, flowOrder - 1);
            if (preFlow.getFlowStatus().equals(FlowStatus.NO_PASS)) {
                throw new InternshipException(FlowError.PRE_FLOW_NO_PASS);
            }
        }
        if (flowRecord.getFlowStatus().equals(FlowStatus.APPLY)) {
            throw new InternshipException(FlowError.FLOW_IS_NOT_TO_YOU);
        }
        if (!flowRecord.getFlowStatus().equals(FlowStatus.NEED_EXAMINE)) {
            throw new InternshipException(FlowError.FLOW_HAS_EXAMINE);
        }

        FlowRecord _updateFlow = new FlowRecord();
        _updateFlow.setId(flowRecord.getId());
        _updateFlow.setFlowStatus(flowStatus);
        update(_updateFlow, flowRecord.getOperateName());

        //更新下一步为 待审核
        FlowRecord nexFlowRecord = getFlowRecordByTargetId(targetId, flowType, flowOrder + 1);
        if (nexFlowRecord != null && flowStatus.equals(FlowStatus.PASS)) {
            _updateFlow = new FlowRecord();
            _updateFlow.setId(nexFlowRecord.getId());
            _updateFlow.setFlowStatus(FlowStatus.NEED_EXAMINE);
            update(_updateFlow, flowRecord.getOperateName());
        }

    }

    public List<FlowRecord> getNeedChangeFlowRecord (Long studentId, Long sourceOperateUserId,
                                                     OperateUserType operateUserType) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setStudentId(studentId);
        flowRecord.setOperateUserId(sourceOperateUserId);
        flowRecord.setOperateUserType(operateUserType);
        flowRecord.setFlowStatus(FlowStatus.APPLY);
        List<FlowRecord> flowRecords = flowMapper.select(flowRecord);
        if (flowRecords == null) {
            flowRecords = new ArrayList<FlowRecord>();
        }
        flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        List<FlowRecord> needExamineFlow = flowMapper.select(flowRecord);
        if (needExamineFlow != null) {
            flowRecords.addAll(needExamineFlow);
        }
        return flowRecords;
    }

    public void changeOperateUser (Long studentId, Long sourceOperateUserId, OperateUserType operateUserType,
                                   Long newOperateUserId, String newOperateUserName, String operator) {
        List<FlowRecord> flowRecords = this.getNeedChangeFlowRecord(studentId, sourceOperateUserId, operateUserType);
        if (CollectionUtil.isNotEmpty(flowRecords)) {
            for (FlowRecord fr : flowRecords) {
                FlowRecord u = new FlowRecord();
                u.setId(fr.getId());
                u.setOperateUserId(newOperateUserId);
                u.setOperateName(newOperateUserName);
                update(u, operator);
            }
        }
    }

    public void updateNoNeedExamine (Long targetId, FlowType flowType) {
        List<FlowRecord> flowRecords = getFlowRecordByTargetId(targetId, flowType);
        if (CollectionUtil.isNotEmpty(flowRecords)) {
            for (FlowRecord flowRecord : flowRecords) {
                if (flowRecord.getFlowStatus() == null) {
                    //该步骤已审核过了 无需更改
                    continue;
                }
                FlowRecord f = new FlowRecord();
                f.setId(flowRecord.getId());
                f.setFlowStatus(FlowStatus.NO_NEED_EXAMINE);
                update(f, "sys_tem");
            }
        }
    }

    public void deleteFlowRecord (Long flowRecordId) {
        if (flowRecordId == null) {
            throw new InternshipException(FlowError.FLOW_ID_EMPTY);
        }
        flowMapper.delete(flowRecordId);
    }

    private FlowRecord getFlowRecordByTargetId (Long targetId, FlowType flowType, Integer flowOrder) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setTargetId(targetId);
        flowRecord.setFlowType(flowType);
        flowRecord.setFlowOrder(flowOrder);
        return selectOne(flowRecord);
    }

    private FlowRecord selectOne (FlowRecord flowRecord) {
        List<FlowRecord> flowRecords = flowMapper.select(flowRecord);
        if (CollectionUtil.isNotEmpty(flowRecords)) {
            return flowRecords.get(0);
        }
        return null;
    }

    private void update (FlowRecord flowRecord, String operator) {
        if (flowRecord.getId() == null) {
            throw new InternshipException(FlowError.UPDATE_FLOW_ID_EMPTY);
        }
        flowRecord.setUpdatedBy(operator);
        flowRecord.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = flowMapper.update(flowRecord);
        if (count <= 0) {
            throw new InternshipException(FlowError.UPDATE_FLOW_ERROR);
        }
    }

    private void addFlow (FlowRecord flowRecord) {
        flowRecord.setUpdatedBy(flowRecord.getCreatedBy());
        flowRecord.setCreatedTime(DateUtil.currentMilliseconds());
        flowRecord.setUpdatedTime(flowRecord.getCreatedTime());
        int count = flowMapper.insert(flowRecord);
        if (count <= 0) {
            throw new InternshipException(FlowError.ADD_FLOW_ERROR);
        }
    }

}
