package cn.edu.swufe.lawschool.internship.android.bussiness.tutor.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;
import cn.edu.swufe.lawschool.internship.android.bussiness.company.model.Company;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年05月03
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Tutor extends BaseModel {
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

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

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

    public String getCompanyName () {
        return companyName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
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

    public Company getCompany () {
        return company;
    }

    public void setCompany (Company company) {
        this.company = company;
    }
}
