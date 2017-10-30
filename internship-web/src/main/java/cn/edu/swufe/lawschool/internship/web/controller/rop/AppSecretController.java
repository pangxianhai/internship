package cn.edu.swufe.lawschool.internship.web.controller.rop;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.app.model.AppSecret;
import cn.edu.swufe.lawschool.internship.app.service.AppSecretService;
import cn.edu.swufe.lawschool.internship.exception.AppSecretError;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2016年10月13
 * <p>Title:       密钥管理</p>
 * <p>Description: 密钥管理</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/app_secret")
public class AppSecretController {

    @Autowired
    LoginService loginService;

    @Autowired
    AppSecretService appSecretService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String getAppSecretList (ModelMap modelMap, AppSecret appSecret) {
        modelMap.put("appSecret", appSecret);
        modelMap.put("status", Status.getValues());
        return "rop/app_secret/appSecretList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public Object getAppSecretListData (
            AppSecret appSecret, @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.DESC"));
        List<AppSecret> appSecretList = appSecretService.getAppSecret(appSecret, page);
        //任何接口不能暴露私钥
        appSecretList.forEach((secret) -> {
            secret.setPrivateKey(null);
        });
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appSecrets", appSecretList);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String addAppSecret (ModelMap modelMap) {
        String appId = appSecretService.createAppId();
        modelMap.put("defaultAppId", appId);
        modelMap.put("defaultAppSecret", appSecretService.createAppSecret(appId));
        modelMap.put("status", Status.getValues());
        return "rop/app_secret/appSecretAdd";
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean addAppSecretData (AppSecret appSecret) {
        UserInfo loginUser = loginService.getLoginUserInfo();
        appSecretService.insertAppSecret(appSecret, loginUser.getName());
        return true;
    }

    @RequestMapping(value = "/getDefaultAppId.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String getDefaultAppId () {
        return appSecretService.createAppId();
    }

    @RequestMapping(value = "/getDefaultAppSecret.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String getDefaultAppSecret (String appId) {
        return appSecretService.createAppSecret(appId);
    }

    @RequestMapping(value = "/checkAppId.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean checkAppId (String appId) {
        return appSecretService.getAppSecretByAppId(appId) == null;
    }

    @RequestMapping(value = "/valid.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean setAppSecretValid (String appId) {
        UserInfo loginUser = loginService.getLoginUserInfo();
        AppSecret appSecret = appSecretService.getAppSecretByAppId(appId);
        if (appSecret == null) {
            throw new InternshipException(AppSecretError.SECRET_FAILED_NOT_EXIST);
        }
        if (appSecret.getIsInvalid() || appSecret.getIsFreeze()) {
            AppSecret _appSecret = new AppSecret();
            _appSecret.setId(appSecret.getId());
            _appSecret.setAppId(appSecret.getAppId());
            _appSecret.setStatus(Status.VALID);
            appSecretService.updateAppSecret(_appSecret, loginUser.getName());
        } else {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_FAILED_STATUS_ERROR);
        }
        return true;
    }

    @RequestMapping(value = "/cancel.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean cancelAppSecretValid (String appId) {
        UserInfo loginUser = loginService.getLoginUserInfo();
        AppSecret appSecret = appSecretService.getAppSecretByAppId(appId);
        if (appSecret == null) {
            throw new InternshipException(AppSecretError.SECRET_FAILED_NOT_EXIST);
        }
        if (appSecret.getIsValid()) {
            AppSecret _appSecret = new AppSecret();
            _appSecret.setId(appSecret.getId());
            _appSecret.setAppId(appSecret.getAppId());
            _appSecret.setStatus(Status.CANCEL);
            appSecretService.updateAppSecret(_appSecret, loginUser.getName());
        } else {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_FAILED_STATUS_ERROR);
        }
        return true;
    }

    @RequestMapping(value = "/freeze.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean freezeAppSecretValid (String appId) {
        UserInfo loginUser = loginService.getLoginUserInfo();
        AppSecret appSecret = appSecretService.getAppSecretByAppId(appId);
        if (appSecret == null) {
            throw new InternshipException(AppSecretError.SECRET_FAILED_NOT_EXIST);
        }
        if (appSecret.getIsValid()) {
            AppSecret _appSecret = new AppSecret();
            _appSecret.setId(appSecret.getId());
            _appSecret.setAppId(appSecret.getAppId());
            _appSecret.setStatus(Status.FREEZE);
            appSecretService.updateAppSecret(_appSecret, loginUser.getName());
        } else {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_FAILED_STATUS_ERROR);
        }
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public boolean deleteAppSecretValid (String appId) {
        AppSecret appSecret = appSecretService.getAppSecretByAppId(appId);
        if (appSecret == null) {
            throw new InternshipException(AppSecretError.SECRET_FAILED_NOT_EXIST);
        }
        if (appSecret.getIsValid()) {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_FAILED_STATUS_ERROR);
        } else {
            appSecretService.deleteAppSecret(appSecret.getId());
        }
        return true;
    }
}
