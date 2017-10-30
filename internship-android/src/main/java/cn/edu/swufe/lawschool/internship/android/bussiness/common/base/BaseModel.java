package cn.edu.swufe.lawschool.internship.android.bussiness.common.base;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.constants.Status;

import java.io.Serializable;

/**
 * Created on 2017年05月03
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class BaseModel implements Serializable {

    /**
     * 主建加密Id
     */
    private String desId;

    /**
     * 状态
     */
    private Status status;

    public String getDesId () {
        return desId;
    }

    public void setDesId (String desId) {
        this.desId = desId;
    }

    public Status getStatus () {
        return status;
    }

    public void setStatus (Status status) {
        this.status = status;
    }
}
