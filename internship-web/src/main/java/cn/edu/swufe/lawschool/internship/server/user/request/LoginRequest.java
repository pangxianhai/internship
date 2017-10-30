package cn.edu.swufe.lawschool.internship.server.user.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.Length;

/**
 * Created on 2016年10月11
 * <p>Title:       登陆参数</p>
 * <p>Description: 登陆参数</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LoginRequest extends AbstractRopRequest {

    @Length(min = 2, max = 30)
    private String userName;

    @Length(min = 2, max = 4096)
    private String password;

    /**
     * 存储公钥私钥的key
     */
    @Length(min = 2, max = 4096)
    private String key;

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

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }
}
