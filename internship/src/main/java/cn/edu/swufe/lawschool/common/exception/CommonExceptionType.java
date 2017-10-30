package cn.edu.swufe.lawschool.common.exception;

import cn.edu.swufe.lawschool.common.base.BaseType;

/**
 * Created on 2015年11月05
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class CommonExceptionType extends BaseType {

    public final static CommonExceptionType TYPE_NOT_FOUND = new CommonExceptionType(10000, "类型没有发现");

    public final static CommonExceptionType SCORE_TYPE_ERROR = new CommonExceptionType(10001, "分数类型不对");

    protected CommonExceptionType (Integer code, String desc) {
        super(code, desc);
    }
}
