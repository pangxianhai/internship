package cn.edu.swufe.lawschool.internship.student.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.internship.assessment.model.Assessment;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;
import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;
import cn.edu.swufe.lawschool.internship.teacher.model.Teacher;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.util.UniversityUtil;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;

/**
 * Created on 2015年11月08
 * <p>Title:       学生信息</p>
 * <p>Description: 学生信息实体</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Student extends BaseDO {

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

    public String getStudentNumber () {
        return StringUtil.trimToNull(studentNumber);
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

    public Long getTeacherId () {
        return teacherId;
    }

    public void setTeacherId (Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo () {
        return userInfo;
    }

    public void setUserInfo (UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getCompanyId () {
        return companyId;
    }

    public void setCompanyId (Long companyId) {
        this.companyId = companyId;
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

    public String getName () {
        return StringUtil.trimToNull(name);
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getTeacherName () {
        return StringUtil.trimToNull(teacherName);
    }

    public void setTeacherName (String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCompanyName () {
        return StringUtil.trimToNull(companyName);
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public String getTutorName () {
        return StringUtil.trimToNull(tutorName);
    }

    public void setTutorName (String tutorName) {
        this.tutorName = tutorName;
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

    public void setAssessment (Assessment assessment) {
        this.assessment = assessment;
    }

    public Evaluation getEvaluation () {
        return evaluation;
    }

    public void setEvaluation (Evaluation evaluation) {
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

    public void setInternshipReport (InternshipReport internshipReport) {
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

    public void setUniversity (UniversityDepartment university) {
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

    public void setCollege (UniversityDepartment college) {
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

    public void setDepartment (UniversityDepartment department) {
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

    public void setSpeciality (UniversityDepartment speciality) {
        this.speciality = speciality;
    }

    public String getUniversityDepartmentInfo () {
        return UniversityUtil.buildUniversityInfo(this.university, this.college, this.department,
                                                  this.speciality);
    }

    public void setUniversityDesId (String universityDesId) {
        if (StringUtil.isNotBlank(universityDesId)) {
            this.universityId = NumberUtil.parseLong(AESUtil.decrypt(universityDesId));
        }
    }

    public String getUniversityDesId () {
        if (this.universityId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.universityId));
    }

    public void setCollegeDesId (String collegeDesId) {
        if (StringUtil.isNotBlank(collegeDesId)) {
            this.collegeId = NumberUtil.parseLong(AESUtil.decrypt(collegeDesId));
        }
    }

    public String getCollegeDesId () {
        if (this.collegeId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.collegeId));
    }

    public void setDepartmentDesId (String departmentDesId) {
        if (StringUtil.isNotBlank(departmentDesId)) {
            this.departmentId = NumberUtil.parseLong(AESUtil.decrypt(departmentDesId));
        }
    }

    public String getDepartmentDesId () {
        if (this.departmentId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.departmentId));
    }

    public void setSpecialityDesId (String specialityDesId) {
        if (StringUtil.isNotBlank(specialityDesId)) {
            this.specialityId = NumberUtil.parseLong(AESUtil.decrypt(specialityDesId));
        }
    }

    public String getSpecialityDesId () {
        if (this.specialityId == null) {
            return StringUtil.trimToEmpty(null);
        }
        return AESUtil.encrypt(String.valueOf(this.specialityId));
    }

    public AcademicianType getAcademicianType () {
        return academicianType;
    }

    public void setAcademicianType (AcademicianType academicianType) {
        this.academicianType = academicianType;
    }

    public void setAcademicianTypeCode (Integer academicianTypeCode) {
        if (academicianTypeCode != null) {
            this.academicianType = AcademicianType.parse(academicianTypeCode);
        }
    }
}
