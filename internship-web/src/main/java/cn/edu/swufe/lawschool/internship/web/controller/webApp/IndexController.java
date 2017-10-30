package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2016年10月25
 * <p>Title:       webApp index</p>
 * <p>Description: webApp index</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppIndexController")
@RequestMapping(value = "/webApp")
public class IndexController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/")
    public String index() {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo != null) {
            return "redirect:" + ServletUtil.redirectWhenLogin(loginInfo, null);
        }
        if (ServletUtil.isMobile()) {
            return "redirect:/webApp/login/index.htm";
        } else {
            return "redirect:/login/index.htm";
        }
    }

    @RequestMapping(value = "")
    public String _index() {
        return index();
    }
}