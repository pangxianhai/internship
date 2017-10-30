package cn.edu.swufe.lawschool.internship.company.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2015年11月08
 * <p>Title:       实习公司/实习单位/实习基地信息</p>
 * <p>Description: 实习公司/实习单位/实习基地信息</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Company extends BaseDO {
    /**
     * 单位名字
     */
    private String companyName;
    /**
     * 单位地址
     */
    private String companyAddress;
    /**
     * 单位联系方式
     */
    private String phone;
    /**
     * 单位能容纳实习人数
     */
    private Integer internshipNumber;
    /**
     * 单位实习管理办法
     */
    private String internshipDesc;
    /**
     * 正在实习的学生人数
     */
    private Integer studentNumber;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = StringUtil.trimToNull(companyName);
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = StringUtil.trimToNull(companyAddress);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = StringUtil.trimToNull(phone);
    }

    public Integer getInternshipNumber() {
        return internshipNumber;
    }

    public void setInternshipNumber(Integer internshipNumber) {
        this.internshipNumber = internshipNumber;
    }

    public String getInternshipDesc() {
        return internshipDesc;
    }

    public void setInternshipDesc(String internshipDesc) {
        this.internshipDesc = StringUtil.trimToNull(internshipDesc);
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }
}
