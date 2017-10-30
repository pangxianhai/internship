package cn.edu.swufe.lawschool.internship.android.bussiness.student.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseModel;
import cn.edu.swufe.lawschool.internship.android.bussiness.company.model.Company;
import cn.edu.swufe.lawschool.internship.android.bussiness.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.android.bussiness.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.android.bussiness.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.android.bussiness.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.android.bussiness.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年05月03
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Student extends BaseModel {
    /**
     * 姓名
     */
    String name;

    /**
     * 学号
     */
    String studentNumber;

    /**
     * 年级
     */
    String grade;

    /**
     * 单位实习指导老师
     */
    Long tutorId;

    /**
     * 指导老师姓名
     */
    String tutorName;

    /**
     * 带队老师
     */
    Long teacherId;

    /**
     * 老师名
     */
    String teacherName;

    /**
     * 用户Id
     */
    Long userId;

    /**
     * 学生实习单位Id
     */
    Long companyId;

    /**
     * 单位名称
     */
    String companyName;

    /**
     * 期望实习单位
     */
    String expectCompany;

    /**
     * 实习备注
     */
    String remark;

    /**
     * 用户信息
     */
    UserInfo userInfo;

    /**
     * 该学生的带队老师
     */
    Teacher teacher;

    /**
     * 该学生的实习单位
     */
    Company company;

    /**
     * 该学生的实习导师
     */
    Tutor tutor;

    /**
     * 日记数
     */
    Integer diaryJournalCount;

    /**
     * 周记数
     */
    Integer weeklyJournalCount;

    /**
     * 实习评价
     */
    Assessment assessment;

    /**
     * 学生质量评价
     */
    Evaluation evaluation;

    /**
     * 出勤天数
     */
    Double attendedCount;

    /**
     * 实习报告
     */
    InternshipReport internshipReport;

    /**
     * 所属学校Id
     */
    Long universityId;

    /**
     * 所属学校
     */
    UniversityDepartment university;

    /**
     * 所属学院Id
     */
    Long collegeId;

    /**
     * 所属学院
     */
    UniversityDepartment college;

    /**
     * 所属系Id
     */
    Long departmentId;

    /**
     * 所属系
     */
    UniversityDepartment department;

    /**
     * 所属专业
     */
    Long specialityId;

    /**
     * 所属专业
     */
    UniversityDepartment speciality;

    /**
     * 学位
     */
    AcademicianType academicianType;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getStudentNumber () {
        return studentNumber;
    }

    public void setStudentNumber (String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getGrade () {
        return grade;
    }

    public void setGrade (String grade) {
        this.grade = grade;
    }

    public Long getTutorId () {
        return tutorId;
    }

    public void setTutorId (Long tutorId) {
        this.tutorId = tutorId;
    }

    public String getTutorName () {
        return tutorName;
    }

    public void setTutorName (String tutorName) {
        this.tutorName = tutorName;
    }

    public Long getTeacherId () {
        return teacherId;
    }

    public void setTeacherId (Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName () {
        return teacherName;
    }

    public void setTeacherName (String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId () {
        return companyId;
    }

    public void setCompanyId (Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName () {
        return companyName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public String getExpectCompany () {
        return expectCompany;
    }

    public void setExpectCompany (String expectCompany) {
        this.expectCompany = expectCompany;
    }

    public String getRemark () {
        return remark;
    }

    public void setRemark (String remark) {
        this.remark = remark;
    }

    public UserInfo getUserInfo () {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Teacher getTeacher () {
        return teacher;
    }

    public void setTeacher (Teacher teacher) {
        this.teacher = teacher;
    }

    public Company getCompany () {
        return company;
    }

    public void setCompany (Company company) {
        this.company = company;
    }

    public Tutor getTutor () {
        return tutor;
    }

    public void setTutor (Tutor tutor) {
        this.tutor = tutor;
    }

    public Integer getDiaryJournalCount () {
        return diaryJournalCount;
    }

    public void setDiaryJournalCount (Integer diaryJournalCount) {
        this.diaryJournalCount = diaryJournalCount;
    }

    public Integer getWeeklyJournalCount () {
        return weeklyJournalCount;
    }

    public void setWeeklyJournalCount (Integer weeklyJournalCount) {
        this.weeklyJournalCount = weeklyJournalCount;
    }

    public Assessment getAssessment () {
        return assessment;
    }

    public void setAssessment (
            Assessment assessment) {
        this.assessment = assessment;
    }

    public Evaluation getEvaluation () {
        return evaluation;
    }

    public void setEvaluation (
            Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Double getAttendedCount () {
        return attendedCount;
    }

    public void setAttendedCount (Double attendedCount) {
        this.attendedCount = attendedCount;
    }

    public InternshipReport getInternshipReport () {
        return internshipReport;
    }

    public void setInternshipReport (
            InternshipReport internshipReport) {
        this.internshipReport = internshipReport;
    }

    public Long getUniversityId () {
        return universityId;
    }

    public void setUniversityId (Long universityId) {
        this.universityId = universityId;
    }

    public UniversityDepartment getUniversity () {
        return university;
    }

    public void setUniversity (
            UniversityDepartment university) {
        this.university = university;
    }

    public Long getCollegeId () {
        return collegeId;
    }

    public void setCollegeId (Long collegeId) {
        this.collegeId = collegeId;
    }

    public UniversityDepartment getCollege () {
        return college;
    }

    public void setCollege (
            UniversityDepartment college) {
        this.college = college;
    }

    public Long getDepartmentId () {
        return departmentId;
    }

    public void setDepartmentId (Long departmentId) {
        this.departmentId = departmentId;
    }

    public UniversityDepartment getDepartment () {
        return department;
    }

    public void setDepartment (
            UniversityDepartment department) {
        this.department = department;
    }

    public Long getSpecialityId () {
        return specialityId;
    }

    public void setSpecialityId (Long specialityId) {
        this.specialityId = specialityId;
    }

    public UniversityDepartment getSpeciality () {
        return speciality;
    }

    public void setSpeciality (
            UniversityDepartment speciality) {
        this.speciality = speciality;
    }

    public AcademicianType getAcademicianType () {
        return academicianType;
    }

    public void setAcademicianType (
            AcademicianType academicianType) {
        this.academicianType = academicianType;
    }
}
