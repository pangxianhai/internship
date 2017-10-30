package cn.edu.swufe.lawschool.internship.server.session;

import com.xavier.rop.context.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2016年10月16
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipSession implements Session {
    private Map<String, Object> attributeMap = new ConcurrentHashMap<String, Object>();


    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    public Map<String, Object> getAllAttribute() {
        return new HashMap<String, Object>(attributeMap);
    }
}
