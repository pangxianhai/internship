package cn.edu.swufe.lawschool.internship.report.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.InternshipReportError;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.report.mapper.InternshipReportMapper;
import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.report.service.InternshipReportService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月30
 * <p>Title:       实习报告服务</p>
 * <p>Description: 实习报告服务实习</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("internshipReportService")
public class InternshipReportServiceImpl implements InternshipReportService {
    @Autowired
    InternshipReportMapper internshipReportMapper;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    FlowService flowService;

    public String studentCreateInternshipReport (Student student, String subject) {
        studentService.checkStudentEnableInternship(student);
        InternshipReport _internshipReport = getStudentInternshipReport(student.getId());
        if (_internshipReport != null) {
            throw new InternshipException(InternshipReportError.STUDENT_HAS_INTERNSHIP_REPORT);
        }
        InternshipReport internshipReport = new InternshipReport();
        internshipReport.setStudentId(student.getId());
        internshipReport.setStudentName(student.getName());
        internshipReport.setSubject(subject);
        internshipReport.setStatus(Status.VALID);
        internshipReport.setCreatedBy(student.getName());
        int count = internshipReportMapper.insert(internshipReport);
        if (count <= 0) {
            throw new InternshipException(InternshipReportError.ADD_INTERNSHIP_REPORT_ERROR);
        }
        Teacher teacher = teacherService.getTeacherById(student.getTeacherId());
        flowService.addInternshipReportFlow(internshipReport, 1, teacher.getUserId(), teacher.getName(),
                                            OperateUserType.TEACHER);
        return internshipReport.getDesId();
    }

    public void teacherReviewInternshipReport (String desId, Integer reportScore, Teacher teacher,
            String remark) {
        InternshipReport internshipReport = getStudentInternshipByDesId(desId);
        Student student = studentService.getStudentById(internshipReport.getStudentId());
        if (!teacher.getId().equals(student.getTeacherId())) {
            throw new InternshipException(InternshipReportError.TEACHER_NOT_MATCH);
        }
        internshipReport.setReviewerId(teacher.getUserId());
        internshipReport.setReviewer(teacher.getName());
        internshipReport.setReportScore(reportScore);
        internshipReport.setRemark(remark);
        internshipReport.setRemarkTime(DateUtil.currentMilliseconds());
        internshipReport.setUpdatedBy(teacher.getName());
        update(internshipReport);
        flowService.examine(teacher.getUserId(), internshipReport.getId(), FlowType.REPORT, 1,
                            FlowStatus.APPLY);
    }

    public InternshipReport getStudentInternshipByDesId (String desId) {
        if (StringUtil.isEmpty(desId)) {
            return null;
        }
        InternshipReport internshipReport = new InternshipReport();
        internshipReport.setDesId(desId);
        return selectOne(internshipReport);
    }

    public InternshipReport getStudentInternshipReport (Long studentId) {
        if (studentId == null) {
            return null;
        }
        InternshipReport internshipReport = new InternshipReport();
        internshipReport.setStudentId(studentId);
        return selectOne(internshipReport);
    }

    private InternshipReport selectOne (InternshipReport internshipReport) {
        List<InternshipReport> internshipReports = internshipReportMapper.select(internshipReport);
        if (CollectionUtil.isNotEmpty(internshipReports)) {
            return internshipReports.get(0);
        }
        return null;
    }

    private void update (InternshipReport internshipReport) {
        if (internshipReport.getId() == null) {
            throw new InternshipException(InternshipReportError.UPDATE_INTERNSHIP_REPORT_ID_EMPTY);
        }
        int count = internshipReportMapper.update(internshipReport);
        if (count <= 0) {
            throw new InternshipException(InternshipReportError.UPDATE_INTERNSHIP_REPORT_ERROR);
        }
    }

}
