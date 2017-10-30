package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.TutorError;
import cn.edu.swufe.lawschool.internship.tutor.model.TutorType;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import org.apache.commons.logging.Log;
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
 * Created on 2015年11月08
 * <p>Title:       单位实习导师Controller</p>
 * <p>Description: 单位实习导师相关操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/tutor")
public class TutorController {
    @Logger
    Log log;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    TutorService tutorService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE })
    public String getTutor (ModelMap modelMap, Tutor tutor, UserInfo userInfo, String action,
            String studentId) {
        tutor.setUserInfo(userInfo);
        Company company = companyService.getCompanyById(tutor.getCompanyId());
        if (company != null) {
            tutor.setCompanyName(company.getCompanyName());
        }
        modelMap.put("action", action);
        modelMap.put("studentId", studentId);
        modelMap.put("tutor", tutor);
        modelMap.put("roleTypes", TutorType.getValues());
        return "tutor/tutorList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public Object getTutorData (Tutor tutor, UserInfo userInfo,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isSysadmin()) {
            Tutor currentTutor = loginService.getTutorInfo();
            if (!currentTutor.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            } else {
                tutor.setCompanyId(currentTutor.getCompanyId());
            }
        }
        Map<String, Object> data = new HashMap<String, Object>();
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.DESC"));
        tutor.setUserInfo(userInfo);
        List<Tutor> tutors = tutorService.getTutor(tutor, page);
        data.put("tutors", tutors);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/my_info.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.COMPANY_TUTOR_CODE)
    public String myInfo (ModelMap modelMap) {
        Tutor tutor = loginService.getTutorInfo();
        if (tutor == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("tutor", tutor);
        return "tutor/tutorInfo";
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    public String addTutor (ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTutor()) {
            Tutor tutor = loginService.getTutorInfo();
            if (tutor == null) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            if (!tutor.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        } else if (loginInfo.isSysadmin()) {
            modelMap.put("roles", TutorType.getValues());
            modelMap.put("companies", companyService.getCompany(new Company(), new Page()));
        }
        modelMap.put("sexes", SexType.getValues());
        return "tutor/tutorAdd";
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public boolean addTutor (Tutor tutor, UserInfo tutorUser) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Company company;
        if (loginInfo.isTutor()) {
            Tutor _tutor = loginService.getTutorInfo();
            if (_tutor == null) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            if (!_tutor.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            company = companyService.getCompanyById(_tutor.getCompanyId());
            tutor.setTutorType(TutorType.GENERAL);
            tutor.setCompanyId(company.getId());
            tutor.setCompanyName(company.getCompanyName());
        } else if (loginInfo.isSysadmin()) {
            company = companyService.getCompanyById(tutor.getCompanyId());
            tutor.setCompanyId(company.getId());
            tutor.setCompanyName(company.getCompanyName());
            if (tutor.isLeader()) {
                if (tutorService.getCompanyLeader(tutor.getCompanyId()) != null) {
                    throw new InternshipException(TutorError.COMPANY_HAS_LEADER);
                }
            }
        }
        tutor.setUserInfo(tutorUser);
        tutor.setCreatedBy(loginInfo.getName());
        tutorService.addTutor(tutor);
        return true;
    }

    @RequestMapping(value = "/checkCompanyHasLeader.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public boolean checkCompanyHasLeader (String tutorDesId) {
        Tutor tutor = tutorService.getTutorByDesId(tutorDesId);
        return tutorService.getCompanyLeader(tutor.getCompanyId()) == null;
    }

    @RequestMapping(value = "/setLeader.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public boolean setLeader (String tutorDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Tutor tutor = tutorService.getTutorByDesId(tutorDesId);
        Tutor leader = tutorService.getCompanyLeader(tutor.getCompanyId());
        tutorService.setLeader(tutor.getId(), loginInfo.getName());
        if (leader != null) {
            tutorService.setGeneral(leader.getId(), loginInfo.getName());
        }
        return true;
    }

    @RequestMapping(value = "/updateInfo.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.COMPANY_TUTOR_CODE)
    public String updateTutorInfo (ModelMap modelMap) {
        Tutor tutor = loginService.getTutorInfo();
        if (tutor == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("tutor", tutor);
        modelMap.put("sexes", SexType.getValues());
        return "tutor/updateTutorInfo";
    }

    @RequestMapping(value = "/updateInfo.json", method = RequestMethod.POST)
    @LoginAccessPermission(UserType.COMPANY_TUTOR_CODE)
    @ResponseBody
    public boolean updateTutorInfo (Tutor tutor, UserInfo tutorUserInfo) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        tutorUserInfo.setId(loginInfo.getId());
        userService.update(tutorUserInfo, loginInfo.getName());
        Tutor _tutor = loginService.getTutorInfo();
        tutor.setId(_tutor.getId());
        tutorService.update(tutor, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE })
    @ResponseBody
    public boolean deleteTutor (String destId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isSysadmin() || loginInfo.isTutor()) {
            if (loginInfo.isTutor()) {
                Tutor currentTutor = loginService.getTutorInfo();
                if (!currentTutor.isLeader()) {
                    throw new InternshipException(ErrorType.NO_ACCESS);
                }
            }
            tutorService.delete(destId);
        } else {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        return true;
    }
}
