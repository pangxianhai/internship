package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2015年11月08
 * <p>Title:       系统管理员Controller</p>
 * <p>Description: 系统管理员相关操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/sysadmin")
public class SysadminController {

    @Logger
    private Log log;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/my_info.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String getUserList(ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        UserInfo _userInfo = userService.getUserInfoById(loginInfo.getId());
        modelMap.put("userInfo", _userInfo);
        return "sysadmin/myInfo";
    }

    @RequestMapping(value = "/updateInfo.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String updateInfo(ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        UserInfo _userInfo = userService.getUserInfoById(loginInfo.getId());
        modelMap.put("userInfo", _userInfo);
        modelMap.put("sexes", SexType.getValues());
        return "sysadmin/updateInfo";
    }

    @RequestMapping(value = "/updateInfo.json", method = RequestMethod.POST)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    @ResponseBody
    public boolean updateInfo(UserInfo userInfo) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        userInfo.setId(loginInfo.getId());
        userService.update(userInfo, loginInfo.getName());
        return true;
    }
}
