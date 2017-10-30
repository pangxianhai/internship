package cn.edu.swufe.lawschool.internship.android.bussiness.server.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017年03月08
 * <p>Title:       访问参数</p>
 * <p>Description: 访问参数</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AccessParam {
    /**
     * 服务名
     */
    String method;

    /**
     * 服务版本号
     */
    String version;

    /**
     * 访问方式
     */
    AccessMethod accessMethod;

    /**
     * sessionID
     */
    String sessionId;

    /**
     * 业务参数
     */
    Map<String, Object> params;

    public String getMethod () {
        return method;
    }

    public void setMethod (String method) {
        this.method = method;
    }

    public String getVersion () {
        return version;
    }

    public void setVersion (String version) {
        this.version = version;
    }

    public AccessMethod getAccessMethod () {
        return accessMethod;
    }

    public void setAccessMethod (AccessMethod accessMethod) {
        this.accessMethod = accessMethod;
    }

    public String getSessionId () {
        return sessionId;
    }

    public void setSessionId (String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Object> getParams () {
        return params;
    }

    public void setParams (String paramName, Object value) {
        if (this.params == null) {
            this.params = new HashMap<String, Object>();
        }
        this.params.put(paramName, value);
    }
}
