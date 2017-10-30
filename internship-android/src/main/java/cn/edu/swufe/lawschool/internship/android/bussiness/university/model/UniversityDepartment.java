package cn.edu.swufe.lawschool.internship.android.bussiness.university.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;

/**
 * Created on 2017年05月03
 * <p>Title:       大学部门</p>
 * <p>Description: 大学部门 包括大学 学院 系 专业</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UniversityDepartment extends BaseModel {
    /**
     * 父ID
     */
    Long parentId;

    /**
     * 名称
     */
    String departmentName;

    /**
     * 类型
     */
    UniversityDepartmentType departmentType;

    /**
     * 是否是叶子节点
     */
    boolean isLeafDepartment;

    /**
     * 组织路径
     */
    String departmentPath;

    /**
     * 组织等级
     */
    int level;
}
