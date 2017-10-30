package cn.edu.swufe.lawschool.internship.university.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2017年03月17
 * <p>Title:       大学部门</p>
 * <p>Description: 大学部门 包括大学 学院 系 专业</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UniversityDepartment extends BaseDO {
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

    public Long getParentId () {
        return parentId;
    }

    public void setParentId (Long parentId) {
        this.parentId = parentId;
    }

    public String getDepartmentName () {
        return departmentName;
    }

    public void setDepartmentName (String departmentName) {
        this.departmentName = departmentName;
    }

    public UniversityDepartmentType getDepartmentType () {
        return departmentType;
    }

    public void setDepartmentType (UniversityDepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public void setDepartmentTypeCode (Integer departmentTypeCode) {
        this.departmentType = UniversityDepartmentType.parse(departmentTypeCode);
    }

    public boolean getIsLeafDepartment () {
        return isLeafDepartment;
    }

    public void setIsLeafDepartment (boolean leafDepartment) {
        isLeafDepartment = leafDepartment;
    }

    public String getDepartmentPath () {
        return departmentPath;
    }

    public void setDepartmentPath (String departmentPath) {
        this.departmentPath = departmentPath;
    }

    public boolean getIsUniversity () {
        return this.departmentType.equals(UniversityDepartmentType.UNIVERSITY);
    }

    public boolean getIsCollege () {
        return this.departmentType.equals(UniversityDepartmentType.COLLEGE);
    }

    public boolean getIsDepartment () {
        return this.departmentType.equals(UniversityDepartmentType.DEPARTMENT);
    }

    public boolean getIsSpeciality () {
        return this.departmentType.equals(UniversityDepartmentType.SPECIALITY);
    }

    public int getLevel () {
        return level;
    }

    public void setLevel (int level) {
        this.level = level;
    }
}
