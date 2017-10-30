package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2016年06月24
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class BulletinError extends ErrorType {
    public final static ErrorType ADD_BULLETIN_ERROR = new BulletinError(170000, "添加公告失败");
    public final static ErrorType UPDATE_BULLETIN_ERROR = new BulletinError(170001, "更新公告失败");
    public final static ErrorType UPDATE_BULLETIN_ID_EMPTY = new BulletinError(170002, "更新公告ID为空");
    public final static ErrorType BULLETIN_ID_EMPTY = new BulletinError(170003, "公告ID为空");
    public final static ErrorType BULLETIN_NOT_EXIST = new BulletinError(170004, "公告不存在");

    protected BulletinError(Integer code, String desc) {
        super(code, desc);
    }
}
