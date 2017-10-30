package cn.edu.swufe.lawschool.internship.server.user;


import cn.edu.swufe.lawschool.internship.server.RopTestCommon;
import com.xavier.commons.util.HttpClientUtil;
import com.xavier.rop.annotation.CharsetName;
import com.xavier.rop.annotation.FormatType;
import com.xavier.rop.annotation.SecretType;

import javax.xml.bind.JAXBContext;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created on 2016年10月11
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LoginRopTest {

    private static Map<Class<?>, JAXBContext> jaxbContextMap = new ConcurrentHashMap<Class<?>, JAXBContext>();

    public static void main(String args[]) throws Exception {
        System.setProperty("catalina.base", "/Users/pangxianhai/www/chat/");
        testLogin();
//        testUserInfo();
    }

    private static void testUserInfo() {
        Map<String, Object> params = new TreeMap<String, Object>();
//        params.put("method", "user.info");
        params.put("version", "1.0");
        params.put("signType", SecretType.MD5.getCode());
        params.put("format", FormatType.JSON.getCode());
        params.put("userName", "201500121322");
        params.put("password", "2222223");
//        params.put("sessionId", "a83e25fb305c418e9cb8ca16d045aea6");
        params = RopTestCommon.sign(params);
        String rest = HttpClientUtil.sendGet("http://127.0.0.1:9090/route", params, CharsetName.CHARSET_NAME);
        System.out.println(rest);
    }

    public static void testLogin() {
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("method", "user.login");
        params.put("version", "1.0");
        params.put("signType", SecretType.MD5.getCode());
        params.put("format", FormatType.JSON.getCode());
        params.put("userName", "201500121322");
        params.put("password", "222222");
        params = RopTestCommon.sign(params);
        String rest = HttpClientUtil.sendGet("http://127.0.0.1:9090/route", params, CharsetName.CHARSET_NAME);
        System.out.println(rest);
    }

}
