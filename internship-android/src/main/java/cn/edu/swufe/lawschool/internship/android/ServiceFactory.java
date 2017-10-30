package cn.edu.swufe.lawschool.internship.android;

import cn.edu.swufe.lawschool.internship.android.bussiness.attend.service.impl.AttendServiceImpl;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.impl.LeaveServiceImpl;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.impl.ServerServiceImpl;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.impl.LoginServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017年03月09
 * <p>Title:       service工厂</p>
 * <p>Description: service工厂</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class ServiceFactory {

    private static final Object lock = new Object();

    private static ServiceFactory serviceFactory;

    private Map<String, Object> serviceContainer;

    public static ServiceFactory getInstance () {
        if (serviceFactory != null) {
            return serviceFactory;
        }
        synchronized (lock) {
            serviceFactory = new ServiceFactory();
            serviceFactory.loadService();
        }
        return serviceFactory;
    }

    protected void loadService () {
        serviceContainer = new HashMap<String, Object>();
        serviceContainer.put("serverService", new ServerServiceImpl());
        serviceContainer.put("loginService", new LoginServiceImpl());
        serviceContainer.put("attendService", new AttendServiceImpl());
        serviceContainer.put("leaveService", new LeaveServiceImpl());
    }

    protected Object getService (String serviceName) {
        return serviceContainer.get(serviceName);
    }

    public <T> T getService (String serviceName, Class<T> clazz) {
        return (T) getService(serviceName);
    }
}
