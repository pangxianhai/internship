package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2017年03月18
 * <p>Title:       学校错误定义</p>
 * <p>Description: 学校错误定义</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UniversityDepartmentError extends ErrorType {

    public final static ErrorType ADD_DEPARTMENT_FAILED = new UniversityDepartmentError(190000, "添加部门失败");

    public final static ErrorType UPDATE_DEPARTMENT_FAILED = new UniversityDepartmentError(190001, "修改部门失败");

    public final static ErrorType UPDATE_DEPARTMENT_ID_EMPTY = new UniversityDepartmentError(190002,
                                                                                             "修改部门ID为空");

    public final static ErrorType ORGANIZATION_ERROR = new UniversityDepartmentError(190003, "组织关系错误");

    public final static ErrorType ADD_DEPARTMENT_PARENT_NOT_EXIST = new UniversityDepartmentError(190004,
                                                                                                  "父部门不存在");

    protected UniversityDepartmentError (Integer code, String desc) {
        super(code, desc);
    }
}
