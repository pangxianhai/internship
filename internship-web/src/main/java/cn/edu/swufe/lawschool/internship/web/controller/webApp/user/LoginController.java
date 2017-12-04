package cn.edu.swufe.lawschool.internship.web.controller.webApp.user;

import cn.edu.swufe.lawschool.common.logger.Logger;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2016年10月25
 * <p>Title:       手机登陆页面</p>
 * <p>Description: 手机登陆页面</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppLoginController")
@RequestMapping(value = "/webApp/login")
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
                return "webApp/user/login";
            } else {
                return "redirect:/login/index.htm";
            }
        }
    }

    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody
    public Object userLogin(String userName, String password, String returnUrl) {
        UserInfo userInfo = userService.login(userName, password);
        if (userInfo == null) {
            throw new InternshipException(UserError.LOGIN_FAILED);
        }
        loginService.saveLoginInfo(userInfo);
        return ServletUtil.redirectWhenLogin(userInfo, returnUrl);
    }
}
