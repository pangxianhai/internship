package cn.edu.swufe.lawschool.internship.attend.service;

import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.attend.model.AttendStatus;
import cn.edu.swufe.lawschool.internship.common.TimeInterval;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月19
 * <p>Title:       签到服务</p>
 * <p>Description: 签到服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface AttendService {

    /**
     * Created on 2015年11月19
     * <p>Description: 学生签到</p>
     * @return
     * @author 庞先海
     */
    void studentAttend(Long userId, Long attendDay, TimeInterval timeInterval);

    /**
     * Created on 2015年11月19
     * <p>Description: 分页获取签到记录</p>
     * @return
     * @author 庞先海
     */
    List<AttendRecord> getAttendRecord(AttendRecordParam attendRecord, Page page);

    /**
     * Created on 2015年11月26
     * <p>Description: 获取一段时间的签到记录</p>
     * @return
     * @author 庞先海
     */
    List<AttendRecord> getAttendRecord(Long userId, Long beginDay, Long endDay);

    /**
     * Created on 2015年11月19
     * <p>Description: 学生签到审核</p>
     * @return
     * @author 庞先海
     */
    void examineStudentAttend(String attendRecordDestId, Long userId, AttendStatus attendStatus, String operator);

    /**
     * Created on 2015年11月29
     * <p>Description: 获取该用户签到数</p>
     * @return
     * @author 庞先海
     */
    Integer getAttendUserCount(Long userId);

    /**
     * Created on 2015年12月23
     * <p>Description: 请假信息push到考勤表中</p>
     * @return
     * @author 庞先海
     */
    void pushLeaveInfo(Long userId, Long attendDay, TimeInterval timeInterval);

    /**
     * Created on 2016年07月17
     * <p>Description: 删除签到记录</p>
     * @return
     * @author 庞先海
     */
    void deleteAttendRecord(String attendDesId, Long operatorUserId);
}
