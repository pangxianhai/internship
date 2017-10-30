package cn.edu.swufe.lawschool.internship.server.session;

import com.xavier.commons.util.RandomUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.rop.context.Session;
import com.xavier.rop.context.SessionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2016年10月11
 * <p>Title:       实习系统session管理器</p>
 * <p>Description: 实习系统session管理器</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipSessionManage implements SessionManager {

    private Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public String addSession(Session session) {
        String sessionId = buildSessionId();
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    public Session getSession(String sessionId) {
        if (StringUtil.isBlank(sessionId)) {
            return null;
        }
        return sessionMap.get(sessionId);
    }

    public void removeSession(String sessionId) {
        if (sessionMap.containsKey(sessionId)) {
            sessionMap.remove(sessionId);
        }
    }

    private String buildSessionId() {
        return RandomUtil.getUUID();
    }


}
