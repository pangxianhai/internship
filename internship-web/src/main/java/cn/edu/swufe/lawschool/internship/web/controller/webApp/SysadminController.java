package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2017年11月01
 * <p>Title:       web 系统管理员Controller</p>
 * <p>Description: web 系统管理员相关操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 *
 * @author 庞先海
 * @version 1.0
 */

@Controller("webAppSysadminController")
@RequestMapping(value = "/webApp/sysadmin")
public class SysadminController {
    @Autowired
    LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String sysadminMainPage(ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        UserInfo adminUserInfo = userService.getUserInfoById(loginInfo.getId());
        modelMap.put("userInfo", adminUserInfo);
        return "webApp/sysadmin/sysadminMain";
    }
}
