package cn.edu.swufe.lawschool.internship.server.user.service;

import cn.edu.swufe.lawschool.internship.server.user.request.LoginRequest;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import com.xavier.commons.util.NumberUtil;
import com.xavier.rop.annotation.NeedInSessionType;
import com.xavier.rop.annotation.ServiceMethod;
import com.xavier.rop.annotation.ServiceMethodBean;
import com.xavier.rop.context.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 2016年10月11
 * <p>Title:       用户服务</p>
 * <p>Description: 用户服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@ServiceMethodBean
public class UserRopService {

    @Autowired
    UserService userService;

    @ServiceMethod(method = "user.info", version = "1.0", needInSession = NeedInSessionType.YES)
    public UserInfo getUserInfo(LoginRequest loginRequest) {
        Session session = loginRequest.getContext().getSession();
        return userService.getUserInfoById(NumberUtil.parseLong(session.getAttribute("userId").toString()));
    }
}
