package cn.edu.swufe.lawschool.internship.leave.service.impl;

import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.LeaveError;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.leave.mapper.LeaveMapper;
import cn.edu.swufe.lawschool.internship.leave.model.FlowOrder;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.leave.model.LeaveStatus;
import cn.edu.swufe.lawschool.internship.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月22
 * <p>Title:       请假服务</p>
 * <p>Description: 请假服务实现类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    FlowService flowService;

    @Autowired
    TutorService tutorService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AttendService attendService;

    public void studentAskForLeave (LeaveRecord leaveRecord) {
        Student student = studentService.getStudentByUserId(leaveRecord.getUserId());
        if (student.getTutorId() == null) {
            throw new InternshipException(LeaveError.ADD_LEAVE_NO_TUTOR);
        }
        if (student.getTeacherId() == null) {
            throw new InternshipException(LeaveError.ADD_LEAVE_NO_TEACHER);
        }
        this.saveLeaveRecord(leaveRecord);
        Tutor tutor = tutorService.getTutorById(student.getTutorId());
        Teacher teacher = teacherService.getTeacherById(student.getTeacherId());
        leaveRecord.setStudent(student);
        flowService.addLeaveFlow(leaveRecord, FlowOrder.STUDENT_LEAVE_TUTOR, tutor.getUserId(),
                                 tutor.getName(), OperateUserType.TUTOR);
        flowService.addLeaveFlow(leaveRecord, FlowOrder.STUDENT_LEAVE_TEACHER, teacher.getUserId(),
                                 teacher.getName(), OperateUserType.TEACHER);
    }

    public List<LeaveRecord> getStudentLeaveRecord (LeaveRecord leaveRecord, Page page) {
        List<LeaveRecord> leaveRecordList = leaveMapper.selectForStudent(leaveRecord, page);
        leaveRecordList.forEach(_leaveRecord -> {
            _leaveRecord.setStudent(studentService.getStudentByUserId(_leaveRecord.getUserId()));
        });
        return leaveRecordList;
    }

    public LeaveRecord getStudentLeaveRecordByDesId (String desId) {
        if (StringUtil.isEmpty(desId)) {
            return null;
        }
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setDesId(desId);
        return selectOne(leaveRecord);
    }

    public void tutorExamine (Long leaveId, Long operateUserId, FlowStatus status) {
        if (status.equals(FlowStatus.NO_PASS)) {
            LeaveRecord leaveRecord = new LeaveRecord();
            leaveRecord.setId(leaveId);
            leaveRecord.setLeaveStatus(LeaveStatus.NO_PASS);
            update(leaveRecord);
        }
        flowService.examine(operateUserId, leaveId, FlowType.LEAVE, FlowOrder.STUDENT_LEAVE_TUTOR, status);
    }

    public void teacherExamine (Long leaveId, Long operateUserId, FlowStatus status) {
        flowService.examine(operateUserId, leaveId, FlowType.LEAVE, FlowOrder.STUDENT_LEAVE_TEACHER, status);
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setId(leaveId);
        if (status.equals(FlowStatus.PASS)) {
            leaveRecord.setLeaveStatus(LeaveStatus.PASS);
            updateAttend(leaveId);
        } else if (status.equals(FlowStatus.NO_PASS)) {
            leaveRecord.setLeaveStatus(LeaveStatus.NO_PASS);
        } else {
            throw new InternshipException(LeaveError.EXAMINE_STATUS_ERROR);
        }
        update(leaveRecord);
    }

    public void deleteLeaveRecord (String leaveDesId, Long operateUserId) {
        LeaveRecord leaveRecord = this.getStudentLeaveRecordByDesId(leaveDesId);
        if (leaveRecord == null) {
            throw new InternshipException(LeaveError.EXAMINE_NOT_EXIST);
        }
        if (!leaveRecord.getUserId().equals(operateUserId)) {
            throw new InternshipException(LeaveError.DELETE_EXAMINE_ERROR_BY_NO_SELF);
        }
        if (!leaveRecord.getLeaveStatus().equals(LeaveStatus.APPLYING)) {
            throw new InternshipException(LeaveError.DELETE_EXAMINE_ERROR_BY_HAS_EXAMINE);
        }
        //删除请假信息的流程信息
        List<FlowRecord> leaveFlowList = flowService.getFlowRecordByTargetId(leaveRecord.getId(),
                                                                             FlowType.LEAVE);
        if (CollectionUtil.isNotEmpty(leaveFlowList)) {
            for (FlowRecord _f : leaveFlowList) {
                flowService.deleteFlowRecord(_f.getId());
            }
        }
        //删除请假信息
        this.leaveMapper.delete(leaveRecord.getId());
    }

    /**
     * 更新考勤表
     */
    private void updateAttend (Long leaveId) {
        LeaveRecord leaveRecord = getLeaveRecord(leaveId);
        Long i = leaveRecord.getBeginDay();
        while (i <= leaveRecord.getEndDay()) {
            if (i.equals(leaveRecord.getBeginDay())) {
                if (leaveRecord.getBeginTimeInterval().equals(TimeInterval.AM)) {
                    attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.AM);
                }
                attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.PM);
            }
            if (i > leaveRecord.getBeginDay() && i == leaveRecord.getEndDay()) {
                //请假开始天和结束天不是同一天 并且循环到最后一天
                attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.AM);
                if (leaveRecord.getBeginTimeInterval().equals(TimeInterval.PM)) {
                    attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.PM);
                }
            } else {
                attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.AM);
                attendService.pushLeaveInfo(leaveRecord.getUserId(), i, TimeInterval.PM);
            }
            i = DateUtil.plusDays(i, 1);
        }
    }

    private LeaveRecord getLeaveRecord (Long leaveId) {
        if (leaveId == null) {
            return null;
        }
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setId(leaveId);
        return selectOne(leaveRecord);
    }

    private void saveLeaveRecord (LeaveRecord leaveRecord) {
        leaveRecord.setLeaveStatus(LeaveStatus.APPLYING);
        int c = leaveMapper.insert(leaveRecord);
        if (c <= 0) {
            throw new InternshipException(LeaveError.ADD_LEAVE_ERROR);
        }
    }

    private LeaveRecord selectOne (LeaveRecord leaveRecord) {
        List<LeaveRecord> leaveRecords = leaveMapper.selectForStudent(leaveRecord);
        if (CollectionUtil.isNotEmpty(leaveRecords)) {
            LeaveRecord _leaveRecord = leaveRecords.get(0);
            _leaveRecord.setStudent(studentService.getStudentByUserId(leaveRecord.getUserId()));
            return _leaveRecord;
        }
        return null;
    }

    private void update (LeaveRecord leaveRecord) {
        if (leaveRecord.getId() == null) {
            throw new InternshipException(LeaveError.UPDATE_LEAVE_ID_EMPTY);
        }
        int count = leaveMapper.update(leaveRecord);
        if (count <= 0) {
            throw new InternshipException(LeaveError.UPDATE_LEAVE_ERROR);
        }
    }
}
