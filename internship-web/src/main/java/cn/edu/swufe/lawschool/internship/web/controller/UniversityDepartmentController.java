package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartmentType;
import cn.edu.swufe.lawschool.internship.university.service.UniversityDepartmentService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created on 2017年03月30
 * <p>Title:       学校组织关系</p>
 * <p>Description: 学校组织关系</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/university")
public class UniversityDepartmentController {

    @Autowired
    LoginService loginService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    UniversityDepartmentService universityDepartmentService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String getUniversityDepartmentList (ModelMap modelMap) {
        modelMap.put("departmentTypes", UniversityDepartmentType.getValues());
        return "university/universityList";
    }

    @RequestMapping(value = "/search.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object getUniversityDepartmentListData (
            UniversityDepartment department, String parentDesId, String action) {
        List<UniversityDepartment> universityDepartmentList;
        if (StringUtil.isBlank(parentDesId) && "search".equals(action)) {
            universityDepartmentList = universityDepartmentService.getUniversityDepartment(department);
        } else {
            universityDepartmentList = universityDepartmentService.getUniversityDepartmentByParentDesId(parentDesId);
        }
        if (CollectionUtil.isNotEmpty(universityDepartmentList)) {
            universityDepartmentList.forEach((_department) -> {
                List<UniversityDepartment> children = universityDepartmentService.getUniversityDepartmentByParentId(_department.getId());
                if (CollectionUtil.isEmpty(children)) {
                    _department.setIsLeafDepartment(true);
                } else {
                    _department.setIsLeafDepartment(false);
                }
                UniversityDepartment parent = universityDepartmentService.getUniversityDepartmentById(_department.getParentId());
                while (parent != null) {
                    _department.setLevel(_department.getLevel() + 1);
                    if (StringUtil.isNotBlank(_department.getDepartmentPath())) {
                        _department.setDepartmentPath(parent.getDepartmentName() + "," +
                                                              StringUtil.trimToEmpty(_department.getDepartmentPath()));
                    } else {
                        _department.setDepartmentPath(parent.getDepartmentName());
                    }
                    parent = universityDepartmentService.getUniversityDepartmentById(parent.getParentId());
                }
            });
        }
        return universityDepartmentList;
    }

    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object getUniversityDepartmentByParent (String parentDesId) {
        List<UniversityDepartment> universityDepartmentList = universityDepartmentService.getUniversityDepartmentByParentDesId(parentDesId);
        return universityDepartmentList;
    }

    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String addUniversityDepartment (ModelMap modelMap, String parentDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher() && !loginInfo.isLeader()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }

        if (StringUtil.isNotBlank(parentDesId)) {
            UniversityDepartment department = universityDepartmentService.getUniversityDepartmentByDesId(parentDesId);
            buildParentId(modelMap, department);
            UniversityDepartmentType nextDepartmentType = department.getDepartmentType().nextDepartment();
            if (nextDepartmentType != null) {
                modelMap.put("departmentTypeCode", nextDepartmentType.getCode());
            } else {
                modelMap.put("departmentTypeCode", department.getDepartmentType().getCode());
            }
        }
        modelMap.put("departmentTypes", UniversityDepartmentType.getValues());
        return "university/universityAdd";
    }

    private void buildParentId (ModelMap modelMap, UniversityDepartment department) {
        if (department == null) {
            return;
        }
        if (department.getIsUniversity()) {
            modelMap.put("universityDesId", department.getDesId());
        } else if (department.getIsCollege()) {
            modelMap.put("collegeDesId", department.getDesId());
        } else if (department.getIsDepartment()) {
            modelMap.put("departmentDesId", department.getDesId());
        }
        buildParentId(modelMap, universityDepartmentService.getUniversityDepartmentById(department.getParentId()));
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object addUniversityDepartment (
            UniversityDepartment department, String universityDesId, String collegeDesId,
            String departmentDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher() && !loginInfo.isLeader()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        String parentDesId = departmentDesId;
        if (StringUtil.isBlank(parentDesId)) {
            parentDesId = collegeDesId;
        }
        if (StringUtil.isBlank(parentDesId)) {
            parentDesId = universityDesId;
        }
        department.setParentId(NumberUtil.parseLong(AESUtil.decrypt(parentDesId)));
        return true;
    }

    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object addUniversityDepartment (UniversityDepartment department) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        UniversityDepartment sourceDepartment = universityDepartmentService.getUniversityDepartmentById(department.getId());
        department.setDepartmentType(sourceDepartment.getDepartmentType());
        authorizationService.checkUniversityDepartmentAuthorization(loginInfo, department);

        universityDepartmentService.updateUniversityDepartment(department, loginInfo.getName());
        return true;
    }

}
