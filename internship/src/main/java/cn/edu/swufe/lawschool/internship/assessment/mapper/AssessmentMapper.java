package cn.edu.swufe.lawschool.internship.assessment.mapper;

import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       实习评价Mapper</p>
 * <p>Description: 实习评价Mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface AssessmentMapper {

    /**
     * Created on 2015年11月29
     * <p>Description:根据条件查询实习评价信息</p>
     * @author 庞先海
     */
    List<Assessment> select(Assessment assessment);

    /**
     * Created on 2015年11月29
     * <p>Description:根据条件分页查询实习评价信息</p>
     * @author 庞先海
     */
    List<Assessment> select(Assessment assessment, Page page);

    /**
     * Created on 2015年11月29
     * <p>Description:更新实习评价信息</p>
     * @author 庞先海
     */
    int update(Assessment assessment);


    /**
     * Created on 2015年11月29
     * <p>Description:插入实习评价信息</p>
     * @author 庞先海
     */
    int insert(Assessment assessment);
}
