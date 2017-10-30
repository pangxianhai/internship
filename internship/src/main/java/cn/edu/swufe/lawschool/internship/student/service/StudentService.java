package cn.edu.swufe.lawschool.internship.student.service;

import cn.edu.swufe.lawschool.internship.common.ActionType;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.model.StudentParam;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       学生服务</p>
 * <p>Description: 学生服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface StudentService {
    /**
     * Created on 2015年11月09
     * <p>Description: 添加学生信息</p>
     * @return 学生Id
     * @author 庞先海
     */
    Long addStudent(Student student);

    /**
     * Created on 2015年11月09
     * <p>Description: 分页查询学生信息</p>
     * @return 学生信息列表
     * @author 庞先海
     */
    List<Student> getStudent(StudentParam studentParam, Page page);

    /**
     * Created on 2015年11月19
     * <p>Description: 通过ID获取学生信息</p>
     * @return 学生信息
     * @author 庞先海
     */
    Student getStudentById(Long id);

    /**
     * Created on 2015年12月02
     * <p>Description: 通过加密ID获取学生信息</p>
     * @return 学生信息
     * @author 庞先海
     */
    Student getStudentByDesId(String desId);

    /**
     * Created on 2015年11月19
     * <p>Description: 通过用户ID获取学生信息</p>
     * @return 学生信息
     * @author 庞先海
     */
    Student getStudentByUserId(Long userId);

    /**
     * Created on 2015年11月19
     * 更新学生公司信息
     * @param studentDestId 学生加密Id
     * @param companyDesId
     * @author 庞先海
     */
    void updateStudentCompany(String studentDestId, String companyDesId, String operator);

    /**
     * Created on 2015年11月19
     * 更新学生公司带队老师
     * @param studentDestId 学生加密Id
     * @param teacherDestId 导师加密Id
     * @author 庞先海
     */
    void updateStudentTeacher(String teacherDestId, String studentDestId, String operator);

    /**
     * Created on 2015年11月19
     * 更新学生公司实习导师信息
     * @param studentDestId 学生加密Id
     * @param tutorDestId   导师加密Id
     * @author 庞先海
     */
    void updateStudentTutor(String tutorDestId, String studentDestId, String operator);

    /**
     * Created on 2015年12月07
     * <p>Description: 检测学生是否有实习资格</p>
     * @return
     * @author 庞先海
     */
    boolean checkStudentEnableInternship(Student student);

    /**
     * Created on 2015年12月26
     * <p>Description: 更新学生信息</p>
     * @return
     * @author 庞先海
     */
    int updateStudent(StudentParam studentParam, String operator);

    /**
     * Created on 2016年07月13
     * <p>Description: 删除学生</p>
     * @return
     * @author 庞先海
     */
    void deleteStudent(String destId, String operator);
}
