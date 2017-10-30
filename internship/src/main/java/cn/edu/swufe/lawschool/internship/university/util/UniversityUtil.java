package cn.edu.swufe.lawschool.internship.university.util;

import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2017年04月07
 * <p>Title:       学校工具类</p>
 * <p>Description: </p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UniversityUtil {

    /**
     * Created on 2017年04月07
     * <p>Description:  构建一个学生或老师所在学校部门的展示信息</p>
     * @return
     */
    public static String buildUniversityInfo (UniversityDepartment... departments) {
        String _departmentStr = "";
        for (UniversityDepartment department : departments) {
            if (department != null) {
                _departmentStr += department.getDepartmentName() + " ";
            }
        }
        return StringUtil.trimToEmpty(_departmentStr);
    }
}
