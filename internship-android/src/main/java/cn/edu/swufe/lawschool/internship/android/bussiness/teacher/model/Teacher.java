package cn.edu.swufe.lawschool.internship.android.bussiness.teacher.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;
import cn.edu.swufe.lawschool.internship.android.bussiness.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年05月03
 * <p>Title:       带队老师信息</p>
 * <p>Description: 带队老师信息</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Teacher extends BaseModel {
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

    public Long getUniversityId () {
        return universityId;
    }

    public void setUniversityId (Long universityId) {
        this.universityId = universityId;
    }

    public UniversityDepartment getUniversity () {
        return university;
    }

    public void setUniversity (
            UniversityDepartment university) {
        this.university = university;
    }

    public Long getCollegeId () {
        return collegeId;
    }

    public void setCollegeId (Long collegeId) {
        this.collegeId = collegeId;
    }

    public UniversityDepartment getCollege () {
        return college;
    }

    public void setCollege (
            UniversityDepartment college) {
        this.college = college;
    }

    public Long getDepartmentId () {
        return departmentId;
    }

    public void setDepartmentId (Long departmentId) {
        this.departmentId = departmentId;
    }

    public UniversityDepartment getDepartment () {
        return department;
    }

    public void setDepartment (
            UniversityDepartment department) {
        this.department = department;
    }

    public Long getSpecialityId () {
        return specialityId;
    }

    public void setSpecialityId (Long specialityId) {
        this.specialityId = specialityId;
    }

    public UniversityDepartment getSpeciality () {
        return speciality;
    }

    public void setSpeciality (
            UniversityDepartment speciality) {
        this.speciality = speciality;
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

    public void setTeacherType (
            TeacherType teacherType) {
        this.teacherType = teacherType;
    }

    public Integer getStudentNumber () {
        return studentNumber;
    }

    public void setStudentNumber (Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public UserInfo getUserInfo () {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
