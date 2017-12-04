package cn.edu.swufe.lawschool.internship.web.controller.webApp.student;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.student.model.AcademicianType;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2016年11月19
 * <p>Title:       webApp student controller</p>
 * <p>Description: webApp student controller</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webAppStudentController")
@RequestMapping(value = "/webApp/student")
public class StudentController {

    @Autowired
    LoginService loginService;

    @Autowired
    CompanyService companyService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TutorService tutorService;

    @Autowired
    AuthorizationService authorizationService;

    /**
     * 学生个人中心
     *
     * @return
     */
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    @LoginAccessPermission({UserType.STUDENT_CODE})
    public String studentMain(ModelMap modelMap) {
        Student student = studentService.getStudentByUserId(loginService.getLoginUserInfo().getId());
        if (student.getCompanyId() != null) {
            student.setCompany(companyService.getCompanyById(student.getCompanyId()));
        }
        if (student.getTutorId() != null) {
            student.setTutor(tutorService.getTutorById(student.getTutorId()));
        }
        if (student.getTeacherId() != null) {
            student.setTeacher(teacherService.getTeacherById(student.getTeacherId()));
        }
        modelMap.put("student", student);
        return "webApp/student/studentMain";
    }

    @RequestMapping(value = "/studentInfo.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.STUDENT_CODE)
    public String studentInfo(ModelMap modelMap) {
        Student student = loginService.getStudentInfo();
        if (student.getCompanyId() != null) {
            student.setCompany(companyService.getCompanyById(student.getCompanyId()));
        }
        if (student.getTutorId() != null) {
            student.setTutor(tutorService.getTutorById(student.getTutorId()));
        }
        if (student.getTeacherId() != null) {
            student.setTeacher(teacherService.getTeacherById(student.getTeacherId()));
        }
        modelMap.put("sexes", SexType.getValues());
        modelMap.put("companies", companyService.getCompany(new Company(), new Page()));
        modelMap.put("academicianTypes", AcademicianType.getValues());
        modelMap.put("student", student);
        return "webApp/student/studentInfo";
    }

    @RequestMapping(value = "/detail/{studentDesId}.htm", method = RequestMethod.GET)
    @LoginAccessPermission
    public String studentDetail(ModelMap modelMap, @PathVariable String studentDesId, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Student student = studentService.getStudentByDesId(studentDesId);
        authorizationService.checkStudentAuthorization(loginInfo, student);
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                student);
        modelMap.put("sexes", SexType.getValues());
        modelMap.put("student", student);
        modelMap.put("companies", companyService.getCompany(new Company(), new Page()));
        modelMap.put("canChangeCompany", operateAuthorization.isCanChangeCompany());
        modelMap.put("canChangeTutor", operateAuthorization.isCanChangeTutor());
        modelMap.put("canChangeTeacher", operateAuthorization.isCanChangeTeacher());
        modelMap.put("canDeleteStudent", operateAuthorization.isCanDeleteStudent());
        modelMap.put("canUpdateStudent", operateAuthorization.isCanUpdateStudent());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/studentDetail";
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE})
    public String getStudentList(ModelMap modelMap, Student student, String returnUrl, String showHeaderLeft) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                null);
        if (!(loginInfo.isStudent() || loginInfo.isLeader() || loginInfo.isSysadmin())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        if (StringUtil.isEmpty(showHeaderLeft)) {
            showHeaderLeft = "true";
        }
        modelMap.put("canChangeCompany", operateAuthorization.isCanChangeCompany());
        modelMap.put("canChangeTutor", operateAuthorization.isCanChangeTutor());
        modelMap.put("canChangeTeacher", operateAuthorization.isCanChangeTeacher());
        modelMap.put("canDeleteStudent", operateAuthorization.isCanDeleteStudent());
        modelMap.put("student", student);
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        modelMap.put("showHeaderLeft", showHeaderLeft);
        return "webApp/student/studentList";
    }

    /**
     * 申请实习
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/application-inter.htm", method = RequestMethod.GET)
    public String applyForInternship(ModelMap modelMap, String returnUrl) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        List<Company> companyList = companyService.getCompany(new Company(), new Page());
        modelMap.put("sexes", SexType.getValues());
        modelMap.put("companies", companyList);
        modelMap.put("academicianTypes", AcademicianType.getValues());
        modelMap.put("returnUrl", ServletUtil.redirectWhenLogin(loginInfo, returnUrl));
        return "webApp/student/applicationInternship";
    }
}
