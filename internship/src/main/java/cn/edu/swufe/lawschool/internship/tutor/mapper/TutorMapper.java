package cn.edu.swufe.lawschool.internship.tutor.mapper;

import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       导师mapper</p>
 * <p>Description: 导师信息的增删改查</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface TutorMapper {
    /**
     * Created on 2015年11月08
     * <p>Description:根据条件查询导师信息</p>
     * @author 庞先海
     */
    List<Tutor> select(Tutor tutor);

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件分页查询导师信息</p>
     * @author 庞先海
     */
    List<Tutor> select(Tutor tutor, Page page);

    /**
     * Created on 2015年11月08
     * <p>Description:插入导师信息</p>
     * @author 庞先海
     */
    int insert(Tutor tutor);

    /**
     * Created on 2015年11月08
     * <p>Description:更新导师信息</p>
     * @author 庞先海
     */
    int update(Tutor tutor);

    /**
     * Created on 2016年07月11
     * <p>Description:删除用户</p>
     * @author 庞先海
     */
    int delete(Long id);
}
