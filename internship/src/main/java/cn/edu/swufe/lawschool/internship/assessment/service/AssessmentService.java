package cn.edu.swufe.lawschool.internship.assessment.service;

import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;

/**
 * Created on 2015年11月29
 * <p>Title:       实习评价服务</p>
 * <p>Description: 实习评价服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface AssessmentService {

    /**
     * Created on 2015年11月29
     * <p>Description: 通过学生Id获取学生实习评价</p>
     * @return
     */
    Assessment getAssessmentByStudentId(Long studentId);

    /**
     * Created on 2015年12月01
     * <p>Description: 通过加密ID获取实习评价表</p>
     * @return
     */
    Assessment getAssessmentByDestId(String destId);

    /**
     * Created on 2015年11月29
     * <p>Description: 导师评价</p>
     * @return
     */
    void tutorAssessment(Long studentId, Tutor tutor, String tutorRemark, Integer tutorScore);

    /**
     * Created on 2015年11月29
     * <p>Description: 公司评价(公司负责人评价)</p>
     * @param assessmentDesId assessment加密Id
     * @return
     */
    void companyAssessment(String assessmentDesId, Tutor tutor, String companyRemark);

    /**
     * Created on 2015年11月29
     * <p>Description: 带队老师确认</p>
     * @return
     */
    void teacherConfirm(String assessmentDesId, Teacher teacher);
}
