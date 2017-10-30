package cn.edu.swufe.lawschool.internship.flow.mapper;

import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月17
 * <p>Title:       flow mapper</p>
 * <p>Description: flow mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface FlowMapper {
    /**
     * Created on 2015年11月17
     * <p>Description:根据条件查询流程信息</p>
     * @author 庞先海
     */
    List<FlowRecord> select(FlowRecord record);

    /**
     * Created on 2015年11月17
     * <p>Description:根据条件分页查询流程信息</p>
     * @author 庞先海
     */
    List<FlowRecord> select(FlowRecord record, Page page);

    /**
     * Created on 2015年11月17
     * <p>Description:插入流程信息</p>
     * @author 庞先海
     */
    int insert(FlowRecord flowRecord);

    /**
     * Created on 2015年11月17
     * <p>Description:更新流程信息</p>
     * @author 庞先海
     */
    int update(FlowRecord flowRecord);

    /**
     * Created on 2016年07月17
     * <p>Description:删除流程信息</p>
     * @author 庞先海
     */
    int delete(Long flowRecordId);
}
