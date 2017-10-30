package cn.edu.swufe.lawschool.internship.assessment.service.impl;

import cn.edu.swufe.lawschool.common.constants.Confirm;
import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.assessment.mapper.AssessmentMapper;
import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.assessment.service.AssessmentService;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.AssessmentError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.model.OperateUserType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.student.model.Student;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.teacher.service.TeacherService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       实习评价服务</p>
 * <p>Description: 实习评价服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("assessmentService")
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    AssessmentMapper assessmentMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    TutorService tutorService;

    @Autowired
    FlowService flowService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TeacherService teacherService;

    public Assessment getAssessmentByStudentId (Long studentId) {
        if (studentId == null) {
            return null;
        }
        Assessment assessment = new Assessment();
        assessment.setStudentId(studentId);
        return selectOne(assessment);
    }

    public Assessment getAssessmentByDestId (String destId) {
        if (StringUtil.isEmpty(destId)) {
            return null;
        }
        Assessment assessment = new Assessment();
        assessment.setDesId(destId);
        return selectOne(assessment);
    }

    public void tutorAssessment (Long studentId, Tutor tutor, String tutorRemark, Integer tutorScore) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new InternshipException(AssessmentError.STUDENT_NOT_EXIST);
        }
        Assessment assessment = new Assessment();
        assessment.setStudentId(student.getId());
        assessment.setStudentName(student.getName());
        assessment.setTutorReviewerId(tutor.getUserId());
        assessment.setTutorReviewer(tutor.getName());
        assessment.setTutorRemark(tutorRemark);
        assessment.setTutorScore(tutorScore);
        assessment.setTutorRemarkTime(DateUtil.currentMilliseconds());
        assessment.setTeacherConfirm(Confirm.UNCONFIRMED);
        assessment.setCreatedBy(tutor.getName());
        assessment.setStatus(Status.VALID);
        insertAssessment(assessment);
        Tutor companyLeader = tutorService.getCompanyLeader(tutor.getCompanyId());
        flowService.addAssessmentFlow(assessment, 1, companyLeader.getUserId(), companyLeader.getName(),
                                      OperateUserType.COMPANY_LEADER);
        Teacher teacher = teacherService.getTeacherById(student.getTeacherId());
        flowService.addAssessmentFlow(assessment, 2, teacher.getUserId(), teacher.getName(),
                                      OperateUserType.TEACHER);
    }

    public void companyAssessment (String assessmentDesId, Tutor tutor, String companyRemark) {
        Assessment assessment = getAssessmentByDestId(assessmentDesId);
        assessment.setCompanyRemark(companyRemark);
        assessment.setCompanyReviewerId(tutor.getUserId());
        assessment.setCompanyReviewer(tutor.getName());
        assessment.setCompanyRemarkTime(DateUtil.currentMilliseconds());
        assessment.setUpdatedBy(tutor.getName());
        update(assessment);
        flowService.examine(tutor.getUserId(), assessment.getId(), FlowType.ASSESSMENT, 1, FlowStatus.PASS);
    }

    public void teacherConfirm (String assessmentDesId, Teacher teacher) {
        Assessment assessment = new Assessment();
        assessment.setDesId(assessmentDesId);
        assessment.setTeacherConfirm(Confirm.CONFIRMED);
        assessment.setTeacherConfirmUserId(teacher.getUserId());
        assessment.setTeacherConfirmUser(teacher.getName());
        assessment.setTeacherConfirmTime(DateUtil.currentMilliseconds());
        update(assessment);
        flowService.examine(teacher.getUserId(), assessment.getId(), FlowType.ASSESSMENT, 2, FlowStatus.PASS);
    }

    private Assessment selectOne (Assessment assessment) {
        List<Assessment> assessments = assessmentMapper.select(assessment);
        if (CollectionUtil.isNotEmpty(assessments)) {
            return assessments.get(0);
        }
        return null;
    }

    private void insertAssessment (Assessment assessment) {
        int count = assessmentMapper.insert(assessment);
        if (count <= 0) {
            throw new InternshipException(AssessmentError.ADD_ASSESSMENT_FAILED);
        }
    }

    private void update (Assessment assessment) {
        if (assessment.getStudentId() == null && assessment.getId() == null) {
            throw new InternshipException(AssessmentError.UPDATE_ASSESSMENT_ID_EMPTY);
        }
        int count = assessmentMapper.update(assessment);
        if (count <= 0) {
            throw new InternshipException(AssessmentError.UPDATE_ASSESSMENT_FAILED);
        }
    }
}
