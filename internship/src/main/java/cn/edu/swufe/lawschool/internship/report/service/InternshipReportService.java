package cn.edu.swufe.lawschool.internship.report.service;

import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;

/**
 * Created on 2015年11月30
 * <p>Title:       实习报告服务</p>
 * <p>Description: 实习报告服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface InternshipReportService {

    /**
     * Created on 2015年12月03
     * <p>Description: 学生创建实习报告</p>
     * @return 返回实习报告desId
     * @author 庞先海
     */
    String studentCreateInternshipReport(Student student, String subject);

    /**
     * Created on 2015年12月03
     * <p>Description: 带队老师点评实习报告</p>
     * @param reportScore 报告分数
     * @param teacher     带队老师
     * @author 庞先海
     */
    void teacherReviewInternshipReport(String desId, Integer reportScore, Teacher teacher, String remark);

    /**
     * Created on 2015年11月30
     * <p>Description: 获取学生的实习报告</p>
     * @author 庞先海
     */
    InternshipReport getStudentInternshipReport(Long studentId);

    /**
     * Created on 2015年12月03
     * <p>Description: 通过加密Id获取实习报告信息</p>
     * @author 庞先海
     */
    InternshipReport getStudentInternshipByDesId(String desId);
}
