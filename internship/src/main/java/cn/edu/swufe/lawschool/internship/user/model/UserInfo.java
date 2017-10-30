package cn.edu.swufe.lawschool.internship.user.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.common.constants.SexType;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2015年11月03
 * <p>Title:       用户信息实体</p>
 * <p>Description: 用户信息</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class UserInfo extends BaseDO {

    /**
     * 用户名
     */
    String userName;

    /**
     * 密码
     */
    String password;

    /**
     * 姓名
     */
    String name;

    /**
     * 性别
     */
    SexType sex;

    /**
     * 联系方式
     */
    String phone;

    /**
     * 用户类型
     */
    UserType userType;

    boolean isLeader;

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = StringUtil.trimToNull(userName);
    }

    public String getPassword () {
        return password;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = StringUtil.trimToNull(phone);
    }

    public void setPassword (String password) {
        this.password = StringUtil.trimToNull(password);
    }

    public UserType getUserType () {
        return userType;
    }

    public void setUserType (UserType userType) {
        this.userType = userType;
    }

    public void setUserTypeCode (Integer typeCode) {
        if (typeCode != null) {
            this.setUserType(UserType.parse(typeCode.intValue()));
        }
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = StringUtil.trimToNull(name);
    }

    public SexType getSex () {
        return sex;
    }

    public void setSex (SexType sex) {
        this.sex = sex;
    }

    public void setSexCode (Integer sexCode) {
        this.sex = SexType.parse(sexCode);
    }

    public boolean isTutor () {
        return UserType.COMPANY_TUTOR.equals(userType);
    }

    public boolean getIsTutor () {
        return this.isTutor();
    }

    public boolean isTeacher () {
        return UserType.TEACHER.equals(userType);
    }

    public boolean isSysadmin () {
        return UserType.SYS_ADMIN.equals(userType);
    }

    public boolean getIsSysadmin () {
        return this.isSysadmin();
    }

    public boolean isStudent () {
        return UserType.STUDENT.equals(userType);
    }

    public boolean isLeader () {
        return isLeader;
    }

    public void setLeader (boolean leader) {
        isLeader = leader;
    }
}
