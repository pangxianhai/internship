package cn.edu.swufe.lawschool.internship.android.bussiness.user.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.constants.SexType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created on 2017年03月07
 * <p>Title:       用户信息</p>
 * <p>Description: 用户信息</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UserInfo implements Serializable {

    /**
     * sessionId
     */

    String sessionId;

    /**
     * userId
     */
    @SerializedName("id")
    Long userId;

    /**
     * 用户加密ID
     */
    @SerializedName("desId")
    String userDesId;

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
     * 电话号码
     */
    String phone;

    /**
     * 用户类型
     */
    UserType userType;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public String getSessionId () {
        return sessionId;
    }

    public void setSessionId (String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public SexType getSex () {
        return sex;
    }

    public void setSex (SexType sex) {
        this.sex = sex;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public UserType getUserType () {
        return userType;
    }

    public void setUserType (UserType userType) {
        this.userType = userType;
    }

    public String getUserDesId () {
        return userDesId;
    }

    public void setUserDesId (String userDesId) {
        this.userDesId = userDesId;
    }

    public boolean isStudent () {
        return this.userType.equals(UserType.STUDENT);
    }

    public boolean isTutor () {
        return UserType.COMPANY_TUTOR.equals(userType);
    }

    public boolean isTeacher () {
        return UserType.TEACHER.equals(userType);
    }

    public boolean isSysadmin () {
        return UserType.SYS_ADMIN.equals(userType);
    }

}
