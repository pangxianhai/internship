package cn.edu.swufe.lawschool.internship.web.controller.user;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UserError;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2015年11月06
 * <p>Title:       登录controller</p>
 * <p>Description: 登录想关的页面和接口</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 *
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Logger
    private Log log;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String loginPage(ModelMap modelMap, String returnUrl) {
        UserInfo userInfo = loginService.getLoginUserInfo();
        modelMap.put("returnUrl", returnUrl);
        if (userInfo != null) {
            return "redirect:" + ServletUtil.redirectWhenLogin(userInfo, returnUrl);
        } else {
            if (ServletUtil.isMobile()) {
                return "redirect:/webApp/login/index.htm";
            } else {
                return "login";
            }
        }
    }

    @RequestMapping(value = "/index.htm", method = RequestMethod.POST)
    public Object userLogin(ModelMap modelMap, String userName, String password, String returnUrl) {
        try {
            UserInfo userInfo = userService.login(userName, password);
            if (userInfo == null) {
                throw new InternshipException(UserError.LOGIN_FAILED);
            }
            loginService.saveLoginInfo(userInfo);
            return "redirect:" + ServletUtil.redirectWhenLogin(userInfo, returnUrl);
        } catch (InternshipException e) {
            modelMap.put("userName", userName);
            modelMap.put("password", password);
            log.error("login failed", e);
            modelMap.put("error", e.getDesc());
        } catch (Exception e) {
            modelMap.put("userName", userName);
            modelMap.put("password", password);
            log.error("login failed", e);
            modelMap.put("error", ErrorType.SYS_ERROR.getDesc());
        }
        return "login";
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout() {
        loginService.logout();
        return "redirect:/";
    }
}
