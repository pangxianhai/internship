package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
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
 * Created on 2016年11月22
 * <p>Title:       实习单位controller</p>
 * <p>Description: </p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webCompanyController")
@RequestMapping(value = "/webApp/company")
public class CompanyController {

    @Autowired
    LoginService loginService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "/detail/{companyDesId}.htm", method = RequestMethod.GET)
    public String getCompanyInfo (ModelMap modelMap, String returnUrl, @PathVariable String companyDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("company", companyService.getCompanyByDestId(companyDesId));
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/company/companyDetail";
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission
    public String companyList (ModelMap modelMap, String returnUrl, String action, String studentDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        modelMap.put("action", action);
        modelMap.put("studentDesId", studentDesId);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/company/companyList";
    }
}
