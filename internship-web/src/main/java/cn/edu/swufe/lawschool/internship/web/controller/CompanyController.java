package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.StringUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月13
 * <p>Title:       公司控制类</p>
 * <p>Description: 公司控制类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController {
    @Logger
    private Log log;

    @Autowired
    CompanyService companyService;

    @Autowired
    LoginService loginService;

    @Autowired
    TutorService tutorService;

    @Autowired
    TeacherService teacherService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String getCompanyList (ModelMap modelMap, Company company, String action, String studentId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo != null) {
            if (loginInfo.isTutor()) {
                Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
                company.setId(tutor.getCompanyId());
                company.setCompanyName(tutor.getCompanyName());
            }
        }
        modelMap.put("company", company);
        modelMap.put("action", action);
        modelMap.put("studentId", studentId);
        return "company/companyList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    public Object getCompanyListData (Company company,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo != null) {
            if (loginInfo.isTutor()) {
                Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
                company.setId(tutor.getCompanyId());

            }
        }
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.DESC"));
        List<Company> companies = companyService.getCompany(company, page);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("companies", companies);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String addCompany (Company company) {
        return "company/addCompany";
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    @ResponseBody
    public Object addCompanySubmit (Company company) {
        UserInfo userInfo = loginService.getLoginUserInfo();
        company.setCreatedBy(userInfo.getName());
        companyService.addCompany(company);
        return true;
    }

    @RequestMapping(value = "/detail/{destId}.htm", method = RequestMethod.GET)
    public String getCompanyDetail (ModelMap modelMap, @PathVariable String destId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Company company = companyService.getCompanyByDestId(destId);
        if (loginInfo != null) {
            if (loginInfo.isTutor()) {
                Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
                if (tutor.isLeader()) {
                    if (tutor.getCompanyId().equals(company.getId())) {
                        modelMap.put("canUpdate", true);
                    }
                }
            } else if (loginInfo.isSysadmin()) {
                modelMap.put("canUpdate", true);
            }
        }
        modelMap.put("company", company);
        return "company/companyDetail";
    }

    @RequestMapping(value = "/update/{destId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    public String updateCompany (ModelMap modelMap, @PathVariable String destId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Company company = companyService.getCompanyByDestId(destId);
        boolean canUpdate = false;
        if (loginInfo.isTutor()) {
            Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
            if (tutor.isLeader()) {
                if (tutor.getCompanyId().equals(company.getId())) {
                    canUpdate = true;
                }
            }
        } else if (loginInfo.isSysadmin()) {
            canUpdate = true;
        }
        if (!canUpdate) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("company", company);
        return "company/companyUpdate";
    }

    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public String updateCompany (Company company) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTutor()) {
            Tutor tutor = tutorService.getTutorByUserId(loginInfo.getId());
            if (!tutor.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            company.setId(tutor.getCompanyId());
        }
        companyService.update(company, loginInfo.getName());
        return company.getDesId();
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public Object deleteCompany (String companyDestId) {
        if (StringUtil.isNotBlank(companyDestId)) {
            if (companyDestId.contains(",")) {
                //对于批量删除 如果部分失败 告诉用户成功几个
                int count = 0;
                String destId[] = companyDestId.split(",");
                for (String s : destId) {
                    try {
                        companyService.deleteCompany(s);
                        count += 1;
                    } catch (InternshipException e) {
                        log.error("deleteCompany failed", e);
                    }
                }
                return count;
            } else {
                companyService.deleteCompany(companyDestId);
            }
        }
        return 1;
    }
}
