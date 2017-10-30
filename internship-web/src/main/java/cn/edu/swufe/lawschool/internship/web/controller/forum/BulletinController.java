package cn.edu.swufe.lawschool.internship.web.controller.forum;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.bulletin.model.BulletinInfo;
import cn.edu.swufe.lawschool.internship.bulletin.service.BulletinService;
import cn.edu.swufe.lawschool.internship.exception.BulletinError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2016年06月24
 * <p>Title:       公告</p>
 * <p>Description: 公告</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bulletin")
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/all.json", method = RequestMethod.POST)
    @LoginAccessPermission
    @ResponseBody
    public Object getAllBulletin () {
        BulletinInfo bulletinInfo = new BulletinInfo();
        bulletinInfo.setStatus(Status.VALID);
        return bulletinService.getBulletinInfo(bulletinInfo, new Page(Order.formString("created_time.DESC")));
    }

    @RequestMapping(value = "/myBulletin.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String myBulletinList (ModelMap modelMap, BulletinInfo bulletinInfo) {
        modelMap.put("bulletinInfo", bulletinInfo);
        return "bulletin/bulletinList";
    }

    @RequestMapping(value = "/myBulletin.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public Object myBulletinListData (BulletinInfo bulletinInfo,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginUserInfo = loginService.getLoginUserInfo();
        bulletinInfo.setUserId(loginUserInfo.getId());
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("created_time.DESC"));
        List<BulletinInfo> bulletinInfoList = bulletinService.getBulletinInfo(bulletinInfo, page);
        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("bulletinInfoList", bulletinInfoList);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String addBulletin (ModelMap modelMap) {
        return "bulletin/bulletinAdd";
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object addBulletinAction (BulletinInfo bulletinInfo) {
        UserInfo userInfo = loginService.getLoginUserInfo();
        bulletinInfo.setUserId(userInfo.getId());
        bulletinInfo.setCreatedBy(userInfo.getName());
        bulletinInfo.setStatus(Status.VALID);
        bulletinService.addBulletinInfo(bulletinInfo);
        return true;
    }

    @RequestMapping(value = "/detail/{destId}.htm", method = RequestMethod.GET)
    public String detailBulletin (ModelMap modelMap, @PathVariable String destId) {
        BulletinInfo bulletinInfo = bulletinService.getBulletinByDestId(destId);
        modelMap.put("bulletinInfo", bulletinInfo);
        return "bulletin/bulletinDetail";
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission
    public boolean deleteBulletin (String bulletinDesId) {
        UserInfo loginUserInfo = loginService.getLoginUserInfo();
        BulletinInfo bulletinInfo = bulletinService.getBulletinByDestId(bulletinDesId);
        if (bulletinInfo == null) {
            throw new InternshipException(BulletinError.BULLETIN_NOT_EXIST);
        }
        if (!bulletinInfo.getUserId().equals(loginUserInfo.getId())) {
            throw new InternshipException(BulletinError.NO_ACCESS);
        }
        bulletinService.deleteBulletinInfo(bulletinInfo.getId());
        return true;
    }
}
