package cn.edu.swufe.lawschool.internship.leave.mapper;

import cn.edu.swufe.lawschool.internship.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       请假Mapper</p>
 * <p>Description: 请假Mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface LeaveMapper {
    /**
     * Created on 2015年11月17
     * <p>Description:根据条件查询请假信息</p>
     * @author 庞先海
     */
    List<LeaveRecord> selectForStudent(LeaveRecord leaveRecord);

    /**
     * Created on 2015年11月17
     * <p>Description:根据条件分页查询请假信息</p>
     * @author 庞先海
     */
    List<LeaveRecord> selectForStudent(LeaveRecord leaveRecord, Page page);

    /**
     * Created on 2015年11月17
     * <p>Description:添加请假信息</p>
     * @author 庞先海
     */
    int insert(LeaveRecord leaveRecord);

    /**
     * Created on 2015年11月17
     * <p>Description:修改请假信息</p>
     * @author 庞先海
     */
    int update(LeaveRecord leaveRecord);

    /**
     * Created on 2016年07月17
     * <p>Description:删除请假信息</p>
     * @author 庞先海
     */
    int delete(Long leaveRecordId);
}
