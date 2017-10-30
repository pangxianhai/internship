package cn.edu.swufe.lawschool.internship.university.service;

import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2017年03月17
 * <p>Title:       学校部门服务</p>
 * <p>Description: 学校部门服务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface UniversityDepartmentService {

    /**
     * Created on 2017年03月30
     * <p>Description:查询学校部门</p>
     * @author 庞先海
     */
    List<UniversityDepartment> getUniversityDepartment (UniversityDepartment department);

    /**
     * Created on 2017年03月17
     * <p>Description:通过父id查询下一级所有部门信息</p>
     * @param parentId 当parentId为null时查询所有学校
     * @author 庞先海
     */
    List<UniversityDepartment> getUniversityDepartmentByParentId (Long parentId);

    /**
     * Created on 2017年04月04
     * <p>Description:通过父id查询下一级所有部门信息</p>
     * @param parentDesId 加密的父ID 当parentDesId为null时查询所有学校
     * @author 庞先海
     */
    List<UniversityDepartment> getUniversityDepartmentByParentDesId (String parentDesId);

    /**
     * Created on 2017年03月29
     * <p>Description:通过id查询部门信息</p>
     * @param departmentId
     * @author 庞先海
     */
    UniversityDepartment getUniversityDepartmentById (Long departmentId);

    /**
     * Created on 2017年04月06
     * <p>Description:通过加密id查询部门信息</p>
     * @param departmentDesId
     * @author 庞先海
     */
    UniversityDepartment getUniversityDepartmentByDesId (String departmentDesId);

    /**
     * Created on 2017年03月29
     * <p>Description:检测部门层级关系</p>
     * <p>departmentIdList[1]在departmentIdList[0]下 departmentIdList[2]在departmentIdList[1]下 依次类推</p>
     * @param departmentIdList
     * @author 庞先海
     */
    boolean checkDepartmentOrganization (List<Long> departmentIdList);

    /**
     * Created on 2017年03月29
     * <p>Description:添加部门信息</p>
     * @param universityDepartment
     */
    void addUniversityDepartment (UniversityDepartment universityDepartment, String operator);

    /**
     * Created on 2017年03月29
     * <p>Description:更新部门信息</p>
     * @param universityDepartment
     */
    void updateUniversityDepartment (UniversityDepartment universityDepartment, String operator);

}
