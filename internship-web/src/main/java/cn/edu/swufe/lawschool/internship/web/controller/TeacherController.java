package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.TeacherError;
import cn.edu.swufe.lawschool.internship.teacher.model.TeacherType;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月08
 * <p>Title:       带队老师Controller</p>
 * <p>Description: 带队老师的基本操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
    @Logger
    Log log;

    @Autowired
    TeacherService teacherService;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/my_info.htm")
    @LoginAccessPermission(UserType.TEACHER_CODE)
    public String studentList (ModelMap modelMap) {
        Teacher teacher = teacherService.getTeacherByUserId(loginService.getLoginUserInfo().getId());
        if (teacher == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("teacher", teacher);
        return "teacher/teacherInfo";
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String getTeacher (ModelMap modelMap, String action, String studentId, Teacher teacher) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isLeader() && !loginInfo.isSysadmin()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        authorizationService.resetTeacherCondition(loginInfo, teacher);
        modelMap.put("action", action);
        modelMap.put("studentId", studentId);
        modelMap.put("teacher", teacher);
        modelMap.put("roleTypes", TeacherType.getValues());
        modelMap.put("leaderTypes", TeacherType.getLeaderType());
        modelMap.put("currentTeacher", loginService.getTeacherInfo());
        return "teacher/teacherList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Object getTeacherData (Teacher teacher, UserInfo userInfo,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.DESC"));
        teacher.setUserInfo(userInfo);
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isLeader() && !loginInfo.isSysadmin()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        authorizationService.resetTeacherCondition(loginInfo, teacher);
        List<Teacher> teachers = teacherService.getTeacher(teacher, page);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("teachers", teachers);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/add.htm")
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    public String addTeacher (ModelMap modelMap) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isLeader() && !loginInfo.isSysadmin()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (teacher == null) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
            if (teacher.isUniversityLeader()) {
                modelMap.put("roles", Arrays.asList(TeacherType.COLLEGE_LEADER, TeacherType.DEPARTMENT_LEADER,
                                                    TeacherType.SPECIALITY_LEADER, TeacherType.GENERAL));
                modelMap.put("universityDesId", teacher.getUniversityDesId());
            } else if (teacher.isCollegeLeader()) {
                modelMap.put("roles",
                             Arrays.asList(TeacherType.DEPARTMENT_LEADER, TeacherType.SPECIALITY_LEADER,
                                           TeacherType.GENERAL));
                modelMap.put("universityDesId", teacher.getUniversityDesId());
                modelMap.put("collegeDesId", teacher.getCollegeDesId());
            } else if (teacher.isDepartmentLeader()) {
                modelMap.put("roles", Arrays.asList(TeacherType.SPECIALITY_LEADER, TeacherType.GENERAL));
                modelMap.put("universityDesId", teacher.getUniversityDesId());
                modelMap.put("collegeDesId", teacher.getCollegeDesId());
                modelMap.put("departmentDesId", teacher.getDepartmentDesId());
            } else if (teacher.isSpecialityLeader()) {
                modelMap.put("roles", Arrays.asList(TeacherType.GENERAL));
                modelMap.put("universityDesId", teacher.getUniversityDesId());
                modelMap.put("collegeDesId", teacher.getCollegeDesId());
                modelMap.put("departmentDesId", teacher.getDepartmentDesId());
                modelMap.put("specialityDesId", teacher.getSpecialityDesId());
            }
        } else {
            modelMap.put("roles", TeacherType.getValues());
        }
        modelMap.put("sexes", SexType.getValues());
        return "teacher/teacherAdd";
    }

    @RequestMapping(value = "/add.json")
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public Boolean addTeacher (Teacher teacher, UserInfo teacherUser) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (!loginInfo.isLeader() && !loginInfo.isSysadmin()) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        authorizationService.resetTeacherCondition(loginInfo, teacher);
        teacher.setUserInfo(teacherUser);
        teacher.setCreatedBy(loginInfo.getName());
        teacherService.addTeacher(teacher);
        return true;
    }

    @RequestMapping(value = "/checkTeacherHasLeader.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public Object checkTeacherHasLeader (Integer teacherTypeCode, String teacherDesId) {
        return teacherService.getLeaderByTeacherOrganization(TeacherType.parse(teacherTypeCode),
                                                             teacherDesId) == null;
    }

    @RequestMapping(value = "/setLeader.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public boolean setLeader (String teacherDesId, Integer teacherTypeCode) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        TeacherType teacherType = TeacherType.parse(teacherTypeCode);
        teacherService.setTeacherLeader(teacherDesId, teacherType, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/updateInfo.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.TEACHER_CODE)
    public String updateInfo (ModelMap modelMap) {
        Teacher teacher = loginService.getTeacherInfo();
        if (teacher == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("teacher", teacher);
        modelMap.put("sexes", SexType.getValues());
        return "teacher/updateTeacherInfo";
    }

    @RequestMapping(value = "/updateInfo.json", method = RequestMethod.POST)
    @LoginAccessPermission(UserType.TEACHER_CODE)
    @ResponseBody
    public boolean updateInfo (Teacher teacher, UserInfo userInfo) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        userInfo.setId(loginInfo.getId());
        userService.update(userInfo, loginInfo.getName());
        Teacher currentTeacher = loginService.getTeacherInfo();
        teacher.setId(currentTeacher.getId());
        teacherService.updateTeacher(teacher, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public boolean deleteTeacher (String destId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (!teacher.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
        teacherService.delete(destId);
        return true;
    }
}