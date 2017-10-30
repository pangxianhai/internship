package cn.edu.swufe.lawschool.internship.university.mapper;

import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2017年03月18
 * <p>Title:       学校mapper</p>
 * <p>Description: 学校mapper</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface UniversityDepartmentMapper {

    /**
     * Created on 2017年03月18
     * <p>Description: 查询</p>
     * @param department
     * @return
     */
    List<UniversityDepartment> select (UniversityDepartment department);

    /**
     * Created on 2017年03月18
     * <p>Description: 分页查询</p>
     * @param department
     * @return
     */
    List<UniversityDepartment> select (UniversityDepartment department, Page page);

    /**
     * Created on 2017年03月18
     * <p>Description: 插入</p>
     * @param department
     * @return
     */
    int insert (UniversityDepartment department);

    /**
     * Created on 2017年03月18
     * <p>Description: 更新</p>
     * @param department
     * @return
     */
    int update (UniversityDepartment department);
}
