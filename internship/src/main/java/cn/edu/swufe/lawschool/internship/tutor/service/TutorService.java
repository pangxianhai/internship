package cn.edu.swufe.lawschool.internship.tutor.service;

import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       单位导师</p>
 * <p>Description: 单位导师服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface TutorService {
    /**
     * Created on 2015年11月09
     * <p>Description: 添加导师信息</p>
     * @return 导师id
     * @author 庞先海
     */
    Long addTutor(Tutor tutor);

    /**
     * Created on 2015年11月09
     * <p>Description: 分页查询导师信息</p>
     * @return 导师信息列表
     * @author 庞先海
     */
    List<Tutor> getTutor(Tutor tutor, Page page);

    /**
     * Created on 2015年11月17
     * <p>Description: 通过id查询导师信息</p>
     * @return 导师信息
     * @author 庞先海
     */
    Tutor getTutorById(Long tutorId);

    /**
     * Created on 2015年12月11
     * <p>Description: 通过加密id查询导师信息</p>
     * @return 导师信息
     * @author 庞先海
     */
    Tutor getTutorByDesId(String desId);

    /**
     * Created on 2015年11月20
     * <p>Description: 通过用户id查询导师信息</p>
     * @return 导师信息
     * @author 庞先海
     */
    Tutor getTutorByUserId(Long userId);

    /**
     * Created on 2015年11月29
     * <p>Description: 获取该单位负责人</p>
     * @return
     * @author 庞先海
     */
    Tutor getCompanyLeader(Long companyId);

    /**
     * Created on 2015年11月29
     * <p>Description: 获取该单位所有导师</p>
     * @return
     * @author 庞先海
     */
    List<Tutor> getCompanyTutor(Long companyId);

    /**
     * Created on 2015年12月12
     * <p>Description: 导师添加学生</p>
     * @return
     * @author 庞先海
     */
    void addStudent(Long tutorId, String operator);

    /**
     * Created on 2015年12月12
     * <p>Description: 导师减少学生</p>
     * @return
     * @author 庞先海
     */
    void minusStudent(Long tutorId, String operator);


    /**
     * Created on 2015年12月11
     * <p>Description: 该导师设负责人</p>
     * @return
     * @author 庞先海
     */
    void setLeader(Long tutorId, String operator);

    /**
     * Created on 2015年12月11
     * <p>Description: 该导师普通导师</p>
     * @return
     * @author 庞先海
     */
    void setGeneral(Long tutorId, String operator);

    /**
     * Created on 2015年12月13
     * <p>Description: 更新导师信息</p>
     * @return
     * @author 庞先海
     */
    void update(Tutor tutor, String operator);

    /**
     * Created on 2016年07月11
     * <p>Description: 删除导师</p>
     * @return
     * @author 庞先海
     */
    void delete(String destId);
}
