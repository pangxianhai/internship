package cn.edu.swufe.lawschool.internship.teacher.service;

import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.model.TeacherType;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       带队老师服务</p>
 * <p>Description: 带队老师服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface TeacherService {

    /**
     * Created on 2015年11月09
     * <p>Description: 添加带队老师信息</p>
     * @return 老师Id
     * @author 庞先海
     */
    Long addTeacher (Teacher teacher);

    /**
     * Created on 2015年11月09
     * <p>Description: 分页查询带队老师信息</p>
     * @return 带队老师信息列表
     * @author 庞先海
     */
    List<Teacher> getTeacher (Teacher teacher, Page page);

    /**
     * Created on 2015年11月17
     * <p>Description: 根据id获取带队老师信息</p>
     * @return 带队老师信息
     * @author 庞先海
     */
    Teacher getTeacherById (Long teacherId);

    /**
     * Created on 2015年12月12
     * <p>Description: 根据加密id获取带队老师信息</p>
     * @return 带队老师信息
     * @author 庞先海
     */
    Teacher getTeacherByDesId (String desId);

    /**
     * Created on 2015年12月12
     * <p>Description: 获取带队老师负责人</p>
     * @param teacherType  带队老师类型(负责人类型)
     * @param teacherDesId 带队老师加密ID
     * @return 带队老师信息
     * @author 庞先海
     */
    Teacher getLeaderByTeacherOrganization (TeacherType teacherType, String teacherDesId);

    /**
     * Created on 2015年11月18
     * <p>Description: 根据用户id获取带队老师信息</p>
     * @return 带队老师信息
     * @author 庞先海
     */
    Teacher getTeacherByUserId (Long userId);

    /**
     * Created on 2015年12月12
     * <p>Description: 带队减少添加实习学生</p>
     * @return
     * @author 庞先海
     */
    void minusStudent (Long teacherId, String operator);

    /**
     * Created on 2015年12月12
     * <p>Description: 带队老师添加实习学生</p>
     * @return
     * @author 庞先海
     */
    void addStudent (Long teacherId, String operator);
    //
    //    /**
    //     * Created on 2015年12月12
    //     * <p>Description: 修改老师类型</p>
    //     * @param teacherId   老师Id
    //     * @param teacherType 老师类型
    //     * @return
    //     * @author 庞先海
    //     */
    //    void updateTeacherType (Long teacherId, TeacherType teacherType, String operator);

    /**
     * Created on 2017年03月30
     * <p>Description: 设置老师位负责人</p>
     * @param teacherDesId 老师Id
     * @param teacherType  老师类型
     * @return
     * @author 庞先海
     */
    void setTeacherLeader (String teacherDesId, TeacherType teacherType, String operator);

    /**
     * Created on 2015年12月12
     * <p>Description: 更新带队老师信息</p>
     * @return
     * @author 庞先海
     */
    void updateTeacher (Teacher teacher, String operator);

    /**
     * Created on 2016年07月12
     * <p>Description: 删除带队老师</p>
     * @return
     * @author 庞先海
     */
    void delete (String destId);
}
