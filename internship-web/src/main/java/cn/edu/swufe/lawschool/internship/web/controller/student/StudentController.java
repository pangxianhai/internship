package cn.edu.swufe.lawschool.internship.web.controller.student;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.StudentError;
import cn.edu.swufe.lawschool.internship.student.model.AcademicianType;
import cn.edu.swufe.lawschool.internship.user.model.OperateAuthorization;
import cn.edu.swufe.lawschool.internship.user.service.AuthorizationService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.model.StudentParam;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.ObjectUtil;
import com.xavier.commons.util.StringUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月08
 * <p>Title:       学生Controller</p>
 * <p>Description: 学生的基本操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Logger
    private Log log;

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
    UserService userService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/application-inter.htm", method = RequestMethod.GET)
    public String applyForInternship (ModelMap modelMap) {
        List<Company> companyList = companyService.getCompany(new Company(), new Page());
        modelMap.put("sexes", SexType.getValues());
        modelMap.put("companies", companyList);
        modelMap.put("academicianTypes", AcademicianType.getValues());
        return "student/applicationInternship";
    }

    @RequestMapping(value = "/application-inter.json", method = RequestMethod.POST)
    @ResponseBody
    public Boolean applyForInternshipSubmit (Student student, UserInfo userInfo) {
        student.setUserInfo(userInfo);
        student.setCreatedBy(student.getStudentNumber());
        studentService.addStudent(student);
        return true;
    }

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    public String getStudentList (ModelMap modelMap, Student student, HttpServletRequest request) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        OperateAuthorization operateAuthorization = authorizationService.getOperateAuthorization(loginInfo,
                                                                                                 null);
        if (!(loginInfo.isStudent() || loginInfo.isLeader() || loginInfo.isSysadmin())) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
        modelMap.put("canChangeCompany", operateAuthorization.isCanChangeCompany());
        modelMap.put("canChangeTutor", operateAuthorization.isCanChangeTutor());
        modelMap.put("canChangeTeacher", operateAuthorization.isCanChangeTeacher());
        modelMap.put("canDeleteStudent", operateAuthorization.isCanDeleteStudent());
        modelMap.put("student", student);
        return "student/studentList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public Object getStudentData (StudentParam studentParam, String beginTimeStr, String endTimeStr,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        authorizationService.resetStudentCondition(loginInfo, studentParam);
        Map<String, Object> data = new HashMap<String, Object>(2);
        Page page = new Page(currentPage, 10, Order.formString("id.DESC"));
        studentParam.setRegisterBeginTime(DateUtil.parseForMills(beginTimeStr));
        studentParam.setRegisterEndTime(DateUtil.parseForMills(endTimeStr));
        List<Student> students = studentService.getStudent(studentParam, page);
        data.put("students", students);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/my_info.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String getStudentInfo (ModelMap modelMap) {
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
        modelMap.put("student", student);
        return "student/myInfo";
    }

    @RequestMapping(value = "/updateCompany.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public int updateCompany (String companyDestId, String studentDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (!teacher.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
        if (!StringUtil.isEmpty(studentDestId)) {
            if (studentDestId.contains(",")) {
                //对于批量分配 如果部分失败 告诉用户成功几个
                int count = 0;
                String destId[] = studentDestId.split(",");
                for (String s : destId) {
                    try {
                        studentService.updateStudentCompany(s, companyDestId, loginInfo.getName());
                        count += 1;
                    } catch (InternshipException e) {
                        log.error("updateCompany failed", e);
                    }
                }
                return count;
            } else {
                studentService.updateStudentCompany(studentDestId, companyDestId, loginInfo.getName());
            }
        }
        return 1;
    }

    @RequestMapping(value = "/updateTutor.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.COMPANY_TUTOR_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public Object updateTutor (String tutorDestId, String studentDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTutor()) {
            Tutor _tutor = loginService.getTutorInfo();
            if (!_tutor.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
        if (!StringUtil.isEmpty(studentDestId)) {
            if (studentDestId.contains(",")) {
                //对于批量分配 如果部分失败 告诉用户成功几个
                int count = 0;
                String destId[] = studentDestId.split(",");
                //检测学生是否属于同一个公司
                Long firstCompanyId = null;
                for (String s : destId) {
                    Student _student = studentService.getStudentByDesId(s);
                    if (firstCompanyId == null) {
                        firstCompanyId = _student.getCompanyId();
                    } else {
                        if (firstCompanyId.equals(_student.getCompanyId())) {
                            continue;
                        } else {
                            throw new InternshipException(
                                    StudentError.UPDATE_STUDENT_ERROR_COMPANY_OF_STUDENT_DIFFERENT);
                        }
                    }
                }
                //每个学生依次修改导师
                for (String s : destId) {
                    try {
                        studentService.updateStudentTutor(tutorDestId, s, loginInfo.getName());
                        count += 1;
                    } catch (InternshipException e) {
                        log.error("updateTeacher failed", e);
                    }
                }
                return count;
            } else {
                studentService.updateStudentTutor(tutorDestId, studentDestId, loginInfo.getName());
            }
        }
        return 1;
    }

    @RequestMapping(value = "/updateTeacher.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.SYS_ADMIN_CODE })
    @ResponseBody
    public Object updateTeacher (String teacherDestId, String studentDestId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher()) {
            Teacher _teacher = loginService.getTeacherInfo();
            if (!_teacher.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
        if (!StringUtil.isEmpty(studentDestId)) {
            if (studentDestId.contains(",")) {
                //对于批量分配 如果部分失败 告诉用户成功几个
                int count = 0;
                String destId[] = studentDestId.split(",");
                for (String s : destId) {
                    try {
                        studentService.updateStudentTeacher(teacherDestId, s, loginInfo.getName());
                        count += 1;
                    } catch (InternshipException e) {
                        log.error("updateTeacher failed", e);
                    }
                }
                return count;
            } else {
                studentService.updateStudentTeacher(teacherDestId, studentDestId, loginInfo.getName());
            }
        }
        return 1;
    }

    @RequestMapping(value = "/updateInfo.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    public String updateStudent (ModelMap modelMap) {
        Student student = loginService.getStudentInfo();
        if (student == null) {
            throw new InternshipException(ErrorType.NO_ACCESS);
        }
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
        modelMap.put("sexes", SexType.getValues());
        modelMap.put("companies", companyService.getCompany(new Company(), new Page()));
        modelMap.put("academicianTypes", AcademicianType.getValues());
        return "student/updateStudent";
    }

    @RequestMapping(value = "/updateInfo.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.STUDENT_CODE })
    @ResponseBody
    public boolean updateStudentInfo (StudentParam studentParam, UserInfo userInfo) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        userInfo.setId(loginInfo.getId());
        userService.update(userInfo, loginInfo.getName());
        Student _student = loginService.getStudentInfo();
        if (_student.getTeacherId() != null) {
            studentParam.setUpdateUniversity(false);
        }
        studentParam.setId(_student.getId());
        studentService.updateStudent(studentParam, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @LoginAccessPermission({ UserType.SYS_ADMIN_CODE, UserType.TEACHER_CODE })
    @ResponseBody
    public int deleteStudent (String destIds) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        if (loginInfo.isTeacher()) {
            Teacher teacher = loginService.getTeacherInfo();
            if (!teacher.isLeader()) {
                throw new InternshipException(ErrorType.NO_ACCESS);
            }
        }
        if (!StringUtil.isEmpty(destIds)) {
            if (destIds.contains(",")) {
                //对于批量删除 如果部分失败 告诉用户成功几个
                int count = 0;
                String destId[] = destIds.split(",");
                for (String s : destId) {
                    try {
                        studentService.deleteStudent(s, loginInfo.getName());
                        count += 1;
                    } catch (InternshipException e) {
                        log.error("deleteStudent failed", e);
                    }
                }
                return count;
            } else {
                studentService.deleteStudent(destIds, loginInfo.getName());
            }
        }
        return 1;
    }
}