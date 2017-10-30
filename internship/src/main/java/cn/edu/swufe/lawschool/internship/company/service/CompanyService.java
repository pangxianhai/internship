package cn.edu.swufe.lawschool.internship.company.service;

import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       公司服务</p>
 * <p>Description: 公司服务类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface CompanyService {

    /**
     * Created on 2015年11月09
     * <p>Description: 添加公司信息</p>
     * @return 单位Id
     * @author 庞先海
     */
    Long addCompany(Company company);

    /**
     * Created on 2015年11月13
     * <p>Description: 获取公司</p>
     * @return
     */
    List<Company> getCompany(Company company, Page page);

    /**
     * Created on 2015年11月16
     * <p>Description: 根据加密Id获取公司详情</p>
     * @return
     */
    Company getCompanyByDestId(String destId);

    /**
     * Created on 2015年11月19
     * <p>Description: 根据Id获取公司详情</p>
     * @return
     */
    Company getCompanyById(Long companyId);


    /**
     * Created on 2015年12月11
     * <p>Description: 公司添加实习学生</p>
     * @param companyId 公司Id
     * @return
     */
    void addStudent(Long companyId, String operator);

    /**
     * Created on 2015年12月11
     * <p>Description: 公司减实习学生</p>
     * @param companyId 公司Id
     * @return
     */
    void minusStudent(Long companyId, String operator);

    /**
     * Created on 2015年12月12
     * <p>Description: 更新公司信息</p>
     * @param company  公司信息
     * @param operator 操作者
     * @return
     */
    int update(Company company, String operator);

    /**
     * Created on 2016年07月17
     * <p>Description: 删除公司信息</p>
     * @param companyDestId 公司加密Id
     * @return
     */
    void deleteCompany(String companyDestId);
}
