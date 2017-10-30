package cn.edu.swufe.lawschool.internship.report.mapper;

import cn.edu.swufe.lawschool.internship.report.model.InternshipReport;

import java.util.List;

/**
 * Created on 2015年11月30
 * <p>Title:       实习报告Mapper</p>
 * <p>Description: 实习报告信息的增删改插</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface InternshipReportMapper {

    /**
     * Created on 2015年11月30
     * <p>Description:根据条件查询学生实习报告信息</p>
     * @author 庞先海
     */
    List<InternshipReport> select(InternshipReport report);

    /**
     * Created on 2015年11月30
     * <p>Description:插入实习报告信息</p>
     * @author 庞先海
     */
    int insert(InternshipReport report);

    /**
     * Created on 2015年11月30
     * <p>Description:修改实习报告信息</p>
     * @author 庞先海
     */
    int update(InternshipReport report);
}
