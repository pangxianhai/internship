package cn.edu.swufe.lawschool.internship.company.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.company.mapper.CompanyMapper;
import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.internship.company.service.CompanyService;
import cn.edu.swufe.lawschool.internship.exception.CompanyError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.student.service.StudentService;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       公司服务</p>
 * <p>Description: 公司服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    FlowService flowService;

    @Autowired
    TutorService tutorService;

    public Long addCompany (Company company) {
        company.setCreatedTime(DateUtil.currentMilliseconds());
        company.setUpdatedBy(company.getCreatedBy());
        company.setUpdatedTime(company.getCreatedTime());
        company.setStatus(Status.VALID);
        int count = companyMapper.insert(company);
        if (count < 1) {
            throw new InternshipException(CompanyError.ADD_COMPANY_ERROR);
        }
        return company.getId();
    }

    public List<Company> getCompany (Company company, Page page) {
        return companyMapper.select(company, page);
    }

    public Company getCompanyByDestId (String destId) {
        if (StringUtil.isBlank(destId)) {
            return null;
        }
        Company company = new Company();
        company.setDesId(destId);
        return selectOne(company);
    }

    public void addStudent (Long companyId, String operator) {
        Company company = getCompanyById(companyId);
        Integer num = company.getStudentNumber();
        if (num == null) {
            num = 0;
        }
        Company _addCompany = new Company();
        _addCompany.setId(company.getId());
        _addCompany.setStudentNumber(num + 1);
        update(_addCompany, operator);
    }

    public void minusStudent (Long companyId, String operator) {
        Company company = getCompanyById(companyId);
        Company _minusCompany = new Company();
        _minusCompany.setId(company.getId());
        Integer num = company.getStudentNumber() - 1;
        if (num < 0) {
            num = 0;
        }
        _minusCompany.setStudentNumber(num);
        update(_minusCompany, operator);
    }

    public Company getCompanyById (Long companyId) {
        if (companyId == null) {
            return null;
        }
        Company company = new Company();
        company.setId(companyId);
        return selectOne(company);
    }

    public int update (Company company, String operator) {
        if (company.getId() == null) {
            throw new InternshipException(CompanyError.UPDATE_COMPANY_ID_EMPTY);
        }
        company.setUpdatedTime(DateUtil.currentMilliseconds());
        company.setUpdatedBy(operator);
        int c = companyMapper.update(company);
        if (c <= 0) {
            throw new InternshipException(CompanyError.UPDATE_COMPANY_ERROR);
        }
        return c;
    }

    public void deleteCompany (String companyDestId) {
        if (StringUtil.isEmpty(companyDestId)) {
            throw new InternshipException(CompanyError.DELETE_COMPANY_ID_EMPTY);
        }
        Company company = this.getCompanyByDestId(companyDestId);
        if (company == null) {
            throw new InternshipException(CompanyError.DELETE_COMPANY_NOT_EXIST);
        }
        if (company.getStudentNumber() != null && company.getStudentNumber().intValue() > 0) {
            throw new InternshipException(CompanyError.DELETE_COMPANY_HAS_STUDENT);
        }
        //删除该单位名下所有的导师
        List<Tutor> companyTutorList = tutorService.getCompanyTutor(company.getId());
        if (CollectionUtil.isNotEmpty(companyTutorList)) {
            for (Tutor _t : companyTutorList) {
                tutorService.delete(_t.getDesId());
            }
        }
        //删除公司
        companyMapper.delete(company.getId());
    }

    private Company selectOne (Company company) {
        List<Company> companies = companyMapper.select(company);
        if (CollectionUtil.isNotEmpty(companies)) {
            return companies.get(0);
        }
        return null;
    }
}
