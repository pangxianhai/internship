package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2015年11月05
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */

@Controller
public class IndexController {

    @Logger
    Log log;

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

    @RequestMapping(value = "/404.htm")
    public String _404Error() {
        if (ServletUtil.isMobile()) {
            return "webApp/404";
        } else {
            return "404";
        }
    }

    @RequestMapping(value = "/500.htm")
    public String _500Error(HttpServletRequest request) {
        if (request.getAttribute("javax.servlet.error.exception") != null) {
            log.error("", (Throwable) request.getAttribute("javax.servlet.error.exception"));
        }
        if (ServletUtil.isMobile()) {
            return "webApp/505";
        } else {
            return "505";
        }
    }
}
