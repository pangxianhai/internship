package cn.edu.swufe.lawschool.internship.server.user.response;

import cn.edu.swufe.lawschool.internship.user.model.UserInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created on 2016年10月11
 * <p>Title:       登陆返回结果</p>
 * <p>Description: 登陆返回结果</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "login_info")
public class LoginResponse implements Serializable {

    @XmlElement
    String sessionId;

    @XmlElement
    UserInfo userInfo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
