package cn.edu.swufe.lawschool.internship.server.service.impl;

import cn.edu.swufe.lawschool.internship.server.service.RopLoginService;
import cn.edu.swufe.lawschool.internship.server.session.InternshipRopContext;
import cn.edu.swufe.lawschool.internship.server.session.InternshipSession;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import com.xavier.commons.util.NumberUtil;
import com.xavier.rop.context.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2016年10月21
 * <p>Title:       rop登录服务</p>
 * <p>Description: rop登录服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Service("ropLoginService")
public class RopLoginServiceImpl implements RopLoginService {

    @Autowired
    UserService userService;

    public void saveLoginUserInfo(UserInfo userInfo) {
        InternshipSession session = new InternshipSession();
        session.setAttribute("userId", String.valueOf(userInfo.getId()));
        InternshipRopContext.getRequest().getContext().addSession(session);
    }

    public UserInfo getLoginUserInfo() {
        Session session = InternshipRopContext.getRequest().getContext().getSession();
        return userService.getUserInfoById(NumberUtil.parseLong(session.getAttribute("userId").toString()));
    }
}
