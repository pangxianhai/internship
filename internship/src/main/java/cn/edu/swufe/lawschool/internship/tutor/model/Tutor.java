package cn.edu.swufe.lawschool.internship.tutor.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import com.xavier.commons.util.encrypt.AESUtil;

/**
 * Created on 2015年11月08
 * <p>Title:       单位导师基础信息</p>
 * <p>Description: 单位导师基础信息</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Tutor extends BaseDO {

    /**
     * 姓名
     */
    String name;

    /**
     * 导师所在部门/科室
     */
    String department;

    /**
     * 导师所在公司
     */
    Long companyId;

    /**
     * 公司名
     */
    String companyName;

    /**
     * 导师用户信息
     */
    Long userId;

    /**
     * 实习导师角色类型
     */
    TutorType tutorType;

    /**
     * 正在实习的学生人数
     */
    Integer studentNumber;

    /**
     * 用户信息
     */
    UserInfo userInfo;

    /**
     * 公司信息
     */
    Company company;

    public String getDepartment () {
        return department;
    }

    public void setDepartment (String department) {
        this.department = department;
    }

    public Long getCompanyId () {
        return companyId;
    }

    public void setCompanyId (Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public TutorType getTutorType () {
        return tutorType;
    }

    public void setTutorType (TutorType tutorType) {
        this.tutorType = tutorType;
    }

    public void setTutorTypeCode (Integer tutorType) {
        if (tutorType != null) {
            this.tutorType = TutorType.parse(tutorType);
        }
    }

    public UserInfo getUserInfo () {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Company getCompany () {
        return company;
    }

    public void setCompany (Company company) {
        this.company = company;
    }

    public Integer getStudentNumber () {
        return studentNumber;
    }

    public void setStudentNumber (Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getCompanyName () {
        return companyName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDestId () {
        return AESUtil.encrypt(String.valueOf(this.getCompanyId()));
    }

    public Boolean isLeader () {
        return this.getTutorType().equals(TutorType.LEADER);
    }

    public Boolean getIsLeader () {
        return this.isLeader();
    }
}
