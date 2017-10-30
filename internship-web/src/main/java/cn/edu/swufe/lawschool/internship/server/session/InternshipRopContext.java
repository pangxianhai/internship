package cn.edu.swufe.lawschool.internship.server.session;

import com.xavier.rop.request.RopRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016年10月21
 * <p>Title:       InternshipRopContext</p>
 * <p>Description: InternshipRopContext</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipRopContext {
    private final static ThreadLocal<InternshipRopContext> controllerContext = new ThreadLocal<InternshipRopContext>();

    private Map<String, Object> context;

    private final static String REQUEST = "internship.rop.request";

    private InternshipRopContext(Map<String, Object> context) {
        this.context = context;
    }

    /**
     * Created on 2016年10月21
     * <p>Description:初始化 </p>
     * @author: 庞先海
     * @update: [日期YYYY-MM-DD] [更改人姓名]
     */
    public static void init(RopRequest request) {
        controllerContext.set(new InternshipRopContext(new HashMap<String, Object>(1)));
        controllerContext.get().setValue(REQUEST, request);
    }

    public static void clean() {
        controllerContext.set(null);
    }

    /**
     * Created on 2015-09-24
     * <p>Description:获取 request</p>
     * @author: 庞先海
     * @update: [日期YYYY-MM-DD] [更改人姓名]
     */
    public static RopRequest getRequest() {
        return (RopRequest) controllerContext.get().getValue(REQUEST);
    }


    private Object getValue(String key) {
        return context.get(key);
    }

    private void setValue(String key, Object value) {
        context.put(key, value);
    }
}
