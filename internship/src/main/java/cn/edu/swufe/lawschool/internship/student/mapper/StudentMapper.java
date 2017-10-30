package cn.edu.swufe.lawschool.internship.student.mapper;

import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.model.StudentParam;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       学生mapper</p>
 * <p>Description: 学生信息的增删改查</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface StudentMapper {

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件查询学生信息</p>
     * @author 庞先海
     */
    List<Student> select (Student student);

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件分页查询学生信息</p>
     * @author 庞先海
     */
    List<Student> select (StudentParam studentParam, Page page);

    /**
     * Created on 2015年11月08
     * <p>Description:插入学生信息</p>
     * @author 庞先海
     */
    int insert (Student student);

    /**
     * Created on 2015年11月08
     * <p>Description:更新学生信息</p>
     * @author 庞先海
     */
    int update (StudentParam studentParam);

    /**
     * Created on 2016年07月13
     * <p>Description:删除学生</p>
     * @author 庞先海
     */
    int delete (Long id);
}
