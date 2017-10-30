package cn.edu.swufe.lawschool.internship;

import cn.edu.swufe.lawschool.common.constants.SexType;
import cn.edu.swufe.lawschool.internship.assessment.service.AssessmentService;
import cn.edu.swufe.lawschool.internship.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationService;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.model.TeacherType;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.model.TutorType;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static ClassPathXmlApplicationContext context;

    private static StudentService studentService;

    private static UserService userService;

    private static CompanyService companyService;

    private static TutorService tutorService;

    private static TeacherService teacherService;

    private static AttendService attendService;

    private static FlowService flowService;

    private static LeaveService leaveService;

    private static AssessmentService assessmentService;

    private static EvaluationService evaluationService;

    public static void main (String[] args) throws Exception {
                       context = new ClassPathXmlApplicationContext("classpath*:internship-spring.xml");
                        context.start();

                        studentService = context.getBean("studentService", StudentService.class);
        //        userService = context.getBean("userService", UserService.class);
        //        companyService = context.getBean("companyService", CompanyService.class);
        //        tutorService = context.getBean("tutorService", TutorService.class);
        //        teacherService = context.getBean("teacherService", TeacherService.class);
        //        attendService = context.getBean("attendService", AttendService.class);
        //        flowService = context.getBean("flowService", FlowService.class);
        //        leaveService = context.getBean("leaveService", LeaveService.class);
        //        assessmentService = context.getBean("assessmentService", AssessmentService.class);
        //        evaluationService = context.getBean("evaluationService", EvaluationService.class);
    }

    private static void initData () {
        studentRegister();
        addTutor();
        addTeacher();
        addSysadmin();
    }

    private static void studentRegister () {
        UserInfo userInfo = new UserInfo();
        Student student = new Student();
        student.setUserInfo(userInfo);

        userInfo.setPassword("123456");
        userInfo.setPhone("18930932050");
        userInfo.setName("张三");
        userInfo.setSex(SexType.M);

        student.setStudentNumber("201500121322");
        student.setGrade("15级");
        student.setCreatedBy("system");

        studentService.addStudent(student);
    }

    private static void addTutor () {
        Company company = new Company();
        company.setCompanyName("四川人民高级法院");
        company.setCompanyAddress("四川省成都市人民南路1223号");
        company.setInternshipNumber(22);
        company.setInternshipDesc("我做主我说了算");
        company.setPhone("021-999999");
        company.setCreatedBy("system");
        companyService.addCompany(company);

        Tutor tutor = new Tutor();
        tutor.setCompanyId(company.getId());
        tutor.setDepartment("行政科");
        tutor.setTutorType(TutorType.LEADER);
        tutor.setCompanyName(company.getCompanyName());
        tutor.setCreatedBy("system");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("zhangdaoshi1");
        userInfo.setPassword("123456");
        userInfo.setPhone("021-88888888");
        userInfo.setName("张导师1");
        userInfo.setSex(SexType.F);
        tutor.setUserInfo(userInfo);

        tutorService.addTutor(tutor);
    }

    private static void addTeacher () {
        Teacher teacher = new Teacher();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("lilaoshi");
        userInfo.setPassword("123456");
        userInfo.setPhone("17899999999");
        userInfo.setName("李老师");
        userInfo.setSex(SexType.M);

        teacher.setUserInfo(userInfo);
        teacher.setRank("高级教授");
        teacher.setTeacherType(TeacherType.COLLEGE_LEADER);
        teacher.setCreatedBy("system");

        teacherService.addTeacher(teacher);
    }

    private static void addSysadmin () {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("sys_admin");
        userInfo.setPassword("1234");
        userInfo.setName("立峰");
        userInfo.setPhone("18930932050");
        userInfo.setSex(SexType.M);
        userInfo.setUserType(UserType.SYS_ADMIN);
        userInfo.setCreatedBy("system");
        userService.addUser(userInfo);
    }

    private static void login () {
        UserInfo userInfo = userService.login("201500121322", "123456");
        System.out.println(userInfo.getUserType().getDesc());
    }
}

