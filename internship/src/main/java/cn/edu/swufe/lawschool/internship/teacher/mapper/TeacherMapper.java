package cn.edu.swufe.lawschool.internship.teacher.mapper;

import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       带队老师mapper</p>
 * <p>Description: 带队老师信息的增删改查</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface TeacherMapper {
    /**
     * Created on 2015年11月08
     * <p>Description:根据条件查询带队老师信息</p>
     * @author 庞先海
     */
    List<Teacher> select (Teacher teacher);

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件分页查询带队老师信息</p>
     * @author 庞先海
     */
    List<Teacher> select (Teacher teacher, Page page);

    /**
     * Created on 2015年11月08
     * <p>Description:插入带队老师信息</p>
     * @author 庞先海
     */
    int insert (Teacher teacher);

    /**
     * Created on 2015年11月08
     * <p>Description:更新带队老师信息</p>
     * @author 庞先海
     */
    int update (Teacher teacher);

    /**
     * Created on 2016年07月12
     * <p>Description: 删除带队老师</p>
     * @return
     * @author 庞先海
     */
    void delete (Long id);
}
