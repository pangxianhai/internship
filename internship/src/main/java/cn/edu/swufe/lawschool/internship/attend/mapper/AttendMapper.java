package cn.edu.swufe.lawschool.internship.attend.mapper;

import cn.edu.swufe.lawschool.internship.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       签到记录操作</p>
 * <p>Description: 签到记录增删改查</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface AttendMapper {

    /**
     * Created on 2015年11月17
     * <p>Description:根据条件查询签到信息</p>
     * @author 庞先海
     */
    List<AttendRecord> selectForStudent(AttendRecordParam attendRecord);

    /**
     * Created on 2015年11月17
     * <p>Description:根据条件分页查询签到信息</p>
     * @author 庞先海
     */
    List<AttendRecord> selectForStudent(AttendRecordParam attendRecord, Page page);

    /**
     * Created on 2015年11月17
     * <p>Description:添加签到信息</p>
     * @author 庞先海
     */
    int insert(AttendRecord attendRecord);

    /**
     * Created on 2015年11月17
     * <p>Description:修改签到信息</p>
     * @author 庞先海
     */
    int update(AttendRecord attendRecord);

    /**
     * Created on 2016年07月17
     * <p>Description:删除签到记录</p>
     * @author 庞先海
     */
    int delete(Long attendId);
}
