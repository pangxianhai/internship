package cn.edu.swufe.lawschool.internship.attend.service.impl;

import cn.edu.swufe.lawschool.internship.attend.mapper.AttendMapper;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.attend.model.AttendStatus;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.internship.exception.AttendError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月19
 * <p>Title:       签到服务</p>
 * <p>Description: 签到服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("attendService")
public class AttendServiceImpl implements AttendService {

    @Autowired
    private AttendMapper attendMapper;

    @Autowired
    private FlowService flowService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private UserService userService;

    public void studentAttend (Long userId, Long attendDay, TimeInterval timeInterval) {
        if (getAttendRecord(userId, attendDay, timeInterval) != null) {
            throw new InternshipException(AttendError.ADD_ATTEND_DUPLICATE);
        }
        UserInfo userInfo = userService.getUserInfoById(userId);
        AttendRecord attendRecord = addAttendInfo(userInfo, attendDay, timeInterval);
        //组装流程信息
        Student student = studentService.getStudentByUserId(attendRecord.getUserId());
        attendRecord.setStudent(student);
        studentService.checkStudentEnableInternship(student);
        Tutor tutor = tutorService.getTutorById(student.getTutorId());
        flowService.addAttendFlow(attendRecord, 1, tutor.getUserId(), tutor.getName(), OperateUserType.TUTOR);
    }

    public List<AttendRecord> getAttendRecord (AttendRecordParam attendRecord, Page page) {
        List<AttendRecord> attendRecordList = attendMapper.selectForStudent(attendRecord, page);
        attendRecordList.forEach(attendRecord1 -> {
            attendRecord1.setStudent(studentService.getStudentByUserId(attendRecord1.getUserId()));
        });
        return attendRecordList;
    }

    public List<AttendRecord> getAttendRecord (Long userId, Long beginDay, Long endDay) {
        AttendRecordParam param = new AttendRecordParam();
        param.setAttendBeginDay(beginDay);
        param.setAttendEndDay(endDay);
        param.setUserId(userId);
        return attendMapper.selectForStudent(param);
    }

    public void examineStudentAttend (
            String attendRecordDestId, Long userId, AttendStatus attendStatus, String operator) {
        AttendRecord attendRecord = getAttendRecordByDestId(attendRecordDestId);
        flowService.examine(userId, attendRecord.getId(), FlowType.ATTEND, 1, FlowStatus.PASS);
        AttendRecord _updateRecord = new AttendRecord();
        _updateRecord.setId(attendRecord.getId());
        _updateRecord.setAttendStatus(attendStatus);
        update(_updateRecord, operator);
    }

    public Integer getAttendUserCount (Long userId) {
        AttendRecordParam attendRecordParam = new AttendRecordParam();
        attendRecordParam.setUserId(userId);
        attendRecordParam.setAttendStatus(AttendStatus.ATTENDED);
        Page page = new Page(1, 1, Order.formString("id.ASC"));
        attendMapper.selectForStudent(attendRecordParam, page);
        return page.getTotalRecord();
    }

    public void pushLeaveInfo (Long userId, Long attendDay, TimeInterval timeInterval) {
        AttendRecord attendRecord = getAttendRecord(userId, attendDay, timeInterval);
        if (attendRecord != null) {
            if (attendRecord.getIsLeaved() || attendRecord.getIsAttended()) {
                // do nothing
            } else {
                AttendRecord u = new AttendRecord();
                u.setId(attendRecord.getId());
                u.setAttendStatus(AttendStatus.LEAVE);
                update(u, "sys_tem");
                if (attendRecord.getIsApply()) {
                    flowService.updateNoNeedExamine(u.getId(), FlowType.ATTEND);
                }
            }
        } else {
            AttendRecord _attendRecord = new AttendRecord();
            _attendRecord.setUserId(userId);
            _attendRecord.setAttendDay(attendDay);
            _attendRecord.setTimeInterval(timeInterval);
            _attendRecord.setAttendStatus(AttendStatus.LEAVE);
            _attendRecord.setCreatedBy("sys_tem");
            attendMapper.insert(_attendRecord);
        }
    }

    public void deleteAttendRecord (String attendDesId, Long operatorUserId) {
        if (StringUtil.isEmpty(attendDesId)) {
            throw new InternshipException(AttendError.ATTEND_ID_EMPTY);
        }
        AttendRecord attendRecord = this.getAttendRecordByDestId(attendDesId);
        if (attendRecord == null) {
            throw new InternshipException(AttendError.ATTEND_NOT_EXIST);
        }
        if (!attendRecord.getUserId().equals(operatorUserId)) {
            throw new InternshipException(AttendError.DELETE_ATTEND_BY_NOT_SELF);
        }
        if (!attendRecord.getIsApply()) {
            throw new InternshipException(AttendError.DELETE_ATTEND_BY_HAS_EXAMINED);
        }
        //删除流程信息
        List<FlowRecord> flowRecords = flowService.getFlowRecordByTargetId(attendRecord.getId(), FlowType.ATTEND);
        if (CollectionUtil.isNotEmpty(flowRecords)) {
            for (FlowRecord _f : flowRecords) {
                flowService.deleteFlowRecord(_f.getId());
            }
        }
        //删除签到信息
        this.attendMapper.delete(attendRecord.getId());
    }

    private AttendRecord getAttendRecord (Long userId, Long attendDay, TimeInterval timeInterval) {
        AttendRecordParam attend = new AttendRecordParam();
        attend.setUserId(userId);
        attend.setAttendDay(attendDay);
        attend.setTimeInterval(timeInterval);
        return selectOne(attend);
    }

    private AttendRecord addAttendInfo (UserInfo userInfo, Long attendDay, TimeInterval timeInterval) {
        AttendRecord attendRecord = new AttendRecord();
        attendRecord.setUserId(userInfo.getId());
        attendRecord.setAttendDay(attendDay);
        attendRecord.setTimeInterval(timeInterval);
        attendRecord.setAttendStatus(AttendStatus.APPLY);
        attendRecord.setCreatedBy(userInfo.getName());
        attendRecord.setCreatedTime(DateUtil.currentMilliseconds());
        attendRecord.setUpdatedBy(attendRecord.getCreatedBy());
        attendRecord.setUpdatedTime(attendRecord.getCreatedTime());
        int count = attendMapper.insert(attendRecord);
        if (count <= 0) {
            throw new InternshipException(AttendError.ADD_ATTEND_ERROR);
        }
        return attendRecord;
    }

    private AttendRecord getAttendRecordByDestId (String destId) {
        if (StringUtil.isEmpty(destId)) {
            return null;
        }
        AttendRecordParam attendRecord = new AttendRecordParam();
        attendRecord.setDesId(destId);
        return selectOne(attendRecord);
    }

    private AttendRecord selectOne (AttendRecordParam attendRecord) {
        List<AttendRecord> attendRecords = attendMapper.selectForStudent(attendRecord);
        if (CollectionUtil.isNotEmpty(attendRecords)) {
            AttendRecord _attendRecord = attendRecords.get(0);
            _attendRecord.setStudent(studentService.getStudentByUserId(attendRecord.getUserId()));
            return _attendRecord;
        }
        return null;
    }

    private void update (AttendRecord attendRecord, String operator) {
        if (attendRecord.getId() == null) {
            throw new InternshipException(AttendError.ATTEND_ID_EMPTY);
        }
        attendRecord.setUpdatedBy(operator);
        attendRecord.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = attendMapper.update(attendRecord);
        if (count <= 0) {
            throw new InternshipException(AttendError.UPDATE_ATTEND_ERROR);
        }
    }
}
