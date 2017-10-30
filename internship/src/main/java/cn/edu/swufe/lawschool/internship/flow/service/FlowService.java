package cn.edu.swufe.lawschool.internship.flow.service;

import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月19
 * <p>Title:       流程服务</p>
 * <p>Description: 流程服务接口</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface FlowService {

    /**
     * Created on 2015年12月09
     * <p>Description: 添加签到流程</p>
     * @return
     * @author 庞先海
     */
    void addAttendFlow (AttendRecord attendRecord, Integer flowOrder, Long operatorUserId,
                        String operatorName, OperateUserType operateUserType);

    /**
     * Created on 2015年12月09
     * <p>Description: 添加签请假流程</p>
     * @return
     * @author 庞先海
     */
    void addLeaveFlow (LeaveRecord leaveRecord, Integer flowOrder, Long operatorUserId, String operatorName,
                       OperateUserType operateUserType);

    /**
     * Created on 2015年12月09
     * <p>Description: 添加实习报告流程</p>
     * @return
     * @author 庞先海
     */
    void addInternshipReportFlow (InternshipReport internshipReport, Integer flowOrder, Long operatorUserId,
                                  String operatorName, OperateUserType operateUserType);

    /**
     * Created on 2015年12月09
     * <p>Description: 添加实习评价流程</p>
     * @return
     * @author 庞先海
     */
    void addAssessmentFlow (Assessment assessment, Integer flowOrder, Long operatorUserId,
                            String operatorName, OperateUserType operateUserType);

    /**
     * Created on 2015年12月09
     * <p>Description: 添加笔记流程</p>
     * @return
     * @author 庞先海
     */
    void addInternshipNotesFlow (InternshipNotes internshipNotes, FlowType flowType, Integer flowOrder,
                                 Long operatorUserId, String operatorName, OperateUserType operateUserType);

    /**
     * Created on 2015年11月21
     * <p>Description: 查询流程</p>
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getFlowRecord (FlowRecord flowRecord);

    /**
     * Created on 2015年12月10
     * <p>Description: 分页查询流程</p>
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getFlowRecord (FlowRecord flowRecord, Page page);

    /**
     * Created on 2015年11月21
     * <p>Description: 根据targetId查询流程</p>
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getFlowRecordByTargetId (Long targetId, FlowType flowType);

    /**
     * Created on 2017年05月17
     * <p>Description: 根据targetId查询流程</p>
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getFlowRecordByTargetId (String targetDesId, FlowType flowType);

    /**
     * Created on 2016年07月17
     * <p>Description: 根据targetId查询需要审核的流程</p>
     * @return
     * @author 庞先海
     */
    FlowRecord getNeedExamineFlowRecordByTargetId (Long targetId, FlowType flowType);

    /**
     * Created on 2015年11月26
     * <p>Description: 获取待审核事务</p>
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getNeedExamineFlowRecord (FlowRecord flowRecord, Page page);

    /**
     * Created on 2015年11月21
     * <p>Description: 审核</p>
     * @return
     * @author 庞先海
     */
    void examine (Long userId, Long targetId, FlowType flowType, Integer order, FlowStatus flowStatus);

    /**
     * 修改流程操作者之前 查询是否有需要修改的流程
     * @param studentId
     * @param sourceOperateUserId
     * @param operateUserType
     * @return
     * @author 庞先海
     */
    List<FlowRecord> getNeedChangeFlowRecord (Long studentId, Long sourceOperateUserId,
                                              OperateUserType operateUserType);

    /**
     * Created on 2015年11月21
     * <p>Description: 修改操作者</p>
     * @return
     * @author 庞先海
     */
    void changeOperateUser (Long studentId, Long sourceOperateUserId, OperateUserType operateUserType,
                            Long newOperateUserId, String newOperateUserName, String operator);

    /**
     * Created on 2015年12月13
     * <p>Description: 该目标修改为无需在审核</p>
     * @return
     * @author 庞先海
     */
    void updateNoNeedExamine (Long targetId, FlowType flowType);

    /**
     * Created on 2016年07月17
     * <p>Description: 删除流程</p>
     * @return
     * @author 庞先海
     */
    void deleteFlowRecord (Long flowRecordId);
}
