package cn.edu.swufe.lawschool.internship.teacher.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.util.UniversityUtil;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;

/**
 * Created on 2015年11月08
 * <p>Title:       带队老师信息</p>
 * <p>Description:带队老师信息</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Teacher extends BaseDO {

    /**
     * 姓名
     */
    String name;

    /**
     * 带队老师职称
     */
    String rank;

    /**
     * 学校ID
     */
    Long universityId;

    /**
     * 学校信息
     */
    UniversityDepartment university;

    /**
     * 学院ID
     */
    Long collegeId;

    /**
     * 学院信息
     */
    UniversityDepartment college;

    /**
     * 系名称
     */
    Long departmentId;

    /**
     * 系信息
     */
    UniversityDepartment department;

    /**
     * 专业ID
     */
    Long specialityId;

    /**
     * 专业信息
     */
    UniversityDepartment speciality;

    /**
     * 用户Id
     */
    Long userId;

    /**
     * 带队老师角色类型
     */
    TeacherType teacherType;

    /**
     * 正在实习的学生人数
     */
    Integer studentNumber;

    /**
     * 用户信息
     */
    UserInfo userInfo;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getRank () {
        return rank;
    }

    public void setRank (String rank) {
        this.rank = rank;
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public TeacherType getTeacherType () {
        return teacherType;
    }

    public void setTeacherType (TeacherType teacherType) {
        this.teacherType = teacherType;
    }

    public void setTeacherTypeCode (Integer teacherType) {
        if (teacherType != null) {
            this.teacherType = TeacherType.parse(teacherType);
        }
    }

    public UserInfo getUserInfo () {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getStudentNumber () {
        return studentNumber;
    }

    public void setStudentNumber (Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Long getUniversityId () {
        return universityId;
    }

    public void setUniversityId (Long universityId) {
        this.universityId = universityId;
    }

    public Long getDepartmentId () {
        return departmentId;
    }

    public void setDepartmentId (Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCollegeId () {
        return collegeId;
    }

    public void setCollegeId (Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getSpecialityId () {
        return specialityId;
    }

    public void setSpecialityId (Long specialityId) {
        this.specialityId = specialityId;
    }

    public boolean isUniversityLeader () {
        return TeacherType.UNIVERSITY_LEADER.equals(this.teacherType);
    }

    public boolean isCollegeLeader () {
        return TeacherType.COLLEGE_LEADER.equals(this.teacherType);
    }

    public boolean isDepartmentLeader () {
        return TeacherType.DEPARTMENT_LEADER.equals(this.teacherType);
    }

    public boolean isSpecialityLeader () {
        return TeacherType.SPECIALITY_LEADER.equals(this.teacherType);
    }

    public boolean isLeader () {
        return this.isUniversityLeader() || this.isCollegeLeader() || this.isDepartmentLeader() ||
                this.isSpecialityLeader();
    }

    public UniversityDepartment getUniversity () {
        return university;
    }

    public void setUniversity (UniversityDepartment university) {
        this.university = university;
    }

    public UniversityDepartment getCollege () {
        return college;
    }

    public void setCollege (UniversityDepartment college) {
        this.college = college;
    }

    public UniversityDepartment getDepartment () {
        return department;
    }

    public void setDepartment (UniversityDepartment department) {
        this.department = department;
    }

    public UniversityDepartment getSpeciality () {
        return speciality;
    }

    public void setSpeciality (UniversityDepartment speciality) {
        this.speciality = speciality;
    }

    public String getUniversityDepartmentInfo () {
        return UniversityUtil.buildUniversityInfo(this.university, this.college, this.department,
                                                  this.speciality);
    }

    public void setUniversityDesId (String universityDesId) {
        if (StringUtil.isNotBlank(universityDesId)) {
            this.universityId = NumberUtil.parseLong(AESUtil.decrypt(universityDesId));
        }
    }

    public void setCollegeDesId (String collegeDesId) {
        if (StringUtil.isNotBlank(collegeDesId)) {
            this.collegeId = NumberUtil.parseLong(AESUtil.decrypt(collegeDesId));
        }
    }

    public void setDepartmentDesId (String departmentDesId) {
        if (StringUtil.isNotBlank(departmentDesId)) {
            this.departmentId = NumberUtil.parseLong(AESUtil.decrypt(departmentDesId));
        }
    }

    public void setSpecialityDesId (String specialityDesId) {
        if (StringUtil.isNotBlank(specialityDesId)) {
            this.specialityId = NumberUtil.parseLong(AESUtil.decrypt(specialityDesId));
        }
    }

    public String getUniversityDesId () {
        if (this.universityId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.universityId));
    }

    public String getCollegeDesId () {
        if (this.collegeId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.collegeId));
    }

    public String getDepartmentDesId () {
        if (this.departmentId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.departmentId));
    }

    public String getSpecialityDesId () {
        if (this.specialityId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.specialityId));
    }
}