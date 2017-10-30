package cn.edu.swufe.lawschool.internship.company.mapper;

import cn.edu.swufe.lawschool.internship.company.model.Company;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       实习单位mapper</p>
 * <p>Description: 实习单位信息的增删改查</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface CompanyMapper {
    /**
     * Created on 2015年11月08
     * <p>Description:根据条件查询单位信息</p>
     * @author 庞先海
     */
    List<Company> select(Company company);

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件分页查询单位信息</p>
     * @author 庞先海
     */
    List<Company> select(Company company, Page page);

    /**
     * Created on 2015年11月08
     * <p>Description:插入单位信息</p>
     * @author 庞先海
     */
    int insert(Company company);

    /**
     * Created on 2015年11月08
     * <p>Description:更新单位信息</p>
     * @author 庞先海
     */
    int update(Company company);

    /**
     * Created on 2016年07月17
     * <p>Description:删除实习单位</p>
     * @author 庞先海
     */
    int delete(Long companyId);
}
