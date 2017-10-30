package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月09
 * <p>Title:       实习单位操作错误码</p>
 * <p>Description: 实习单位操作错误码</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class CompanyError extends ErrorType {
    public final static ErrorType ADD_COMPANY_ERROR = new CompanyError(50000, "添加实习单位失败");

    public final static ErrorType UPDATE_COMPANY_ERROR = new CompanyError(50001, "更新实习单位失败");

    public final static ErrorType UPDATE_COMPANY_ID_EMPTY = new CompanyError(50002, "更新实习单位ID为空");

    public final static ErrorType DELETE_COMPANY_ID_EMPTY = new CompanyError(50003, "删除实习单位ID为空");

    public final static ErrorType DELETE_COMPANY_NOT_EXIST = new CompanyError(50004, "删除实习单位错误，单位不存在");

    public final static ErrorType DELETE_COMPANY_HAS_STUDENT = new CompanyError(50005, "删除实习单位错误，单位有学生正在实习");

    protected CompanyError (Integer code, String desc) {
        super(code, desc);
    }
}
