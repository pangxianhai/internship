package cn.edu.swufe.lawschool.internship.web.controller.webApp.forum;

import cn.edu.swufe.lawschool.internship.bulletin.model.BulletinInfo;
import cn.edu.swufe.lawschool.internship.bulletin.service.BulletinService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2016年11月19
 * <p>Title:       webAPP公告</p>
 * <p>Description: webAPP公告</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller("webAppBulletinController")
@RequestMapping(value = "/webApp/bulletin")
public class BulletinController {

    @Autowired
    BulletinService bulletinService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission
    public String bulletinList (ModelMap modelMap) {
        return "webApp/forum/bulletin/bulletinList";
    }

    @RequestMapping(value = "/detail/{destId}.htm", method = RequestMethod.GET)
    public String detailBulletin (ModelMap modelMap, @PathVariable String destId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        BulletinInfo bulletinInfo = bulletinService.getBulletinByDestId(destId);
        modelMap.put("bulletinInfo", bulletinInfo);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/forum/bulletin/bulletinDetail";
    }

    @RequestMapping(value = "/myBulletin.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String myBulletinList (ModelMap modelMap, BulletinInfo bulletinInfo, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("bulletinInfo", bulletinInfo);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/forum/bulletin/myBulletinList";
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String addBulletin (ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/forum/bulletin/bulletinAdd";
    }
}
