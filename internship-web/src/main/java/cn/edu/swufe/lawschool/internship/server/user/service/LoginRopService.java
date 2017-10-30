package cn.edu.swufe.lawschool.internship.server.user.service;

import cn.edu.swufe.lawschool.internship.SystemInfo;
import cn.edu.swufe.lawschool.internship.app.service.AppSecretService;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UserError;
import cn.edu.swufe.lawschool.internship.server.service.RopLoginService;
import cn.edu.swufe.lawschool.internship.server.user.request.LoginRequest;
import cn.edu.swufe.lawschool.internship.server.user.response.LoginResponse;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import com.xavier.commons.util.encrypt.RSAUtil;
import com.xavier.rop.annotation.NeedInSessionType;
import com.xavier.rop.annotation.ServiceMethod;
import com.xavier.rop.annotation.ServiceMethodBean;
import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.request.SimpleRopRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016年10月11
 * <p>Title:       登陆服务</p>
 * <p>Description: 登陆服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@ServiceMethodBean
public class LoginRopService {

    @Autowired
    RopLoginService ropLoginService;

    @Autowired
    UserService userService;

    @Autowired
    AppSecretService appSecretService;

    @ServiceMethod(method = "user.login", version = "1.0", needInSession = NeedInSessionType.NO)
    public LoginResponse userLogin (LoginRequest loginRequest) {
        String privateKey = appSecretService.getAppSecretByAppId(loginRequest.getContext().getAppId()).getPrivateKey();
        String password = RSAUtil.decryptByPrivate(loginRequest.getPassword(), privateKey, SystemInfo.CHARSET_NAME);
        UserInfo userInfo = userService.login(loginRequest.getUserName(), password);
        if (userInfo == null) {
            throw new InternshipException(UserError.LOGIN_FAILED);
        }
        if (!userInfo.isStudent()) {
            throw new InternshipException(UserError.APP_ONLY_STUDENT);
        }

        ropLoginService.saveLoginUserInfo(userInfo);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSessionId(loginRequest.getContext().getSessionId());
        loginResponse.setUserInfo(userInfo);
        return loginResponse;
    }
}
