package cn.edu.swufe.lawschool.internship.web.cookie;

import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import cn.edu.swufe.lawschool.internship.web.system.ConfigHelper;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.EncodeUtil;

import javax.servlet.http.Cookie;

/**
 * Created on 2015年11月09
 * <p>Title:       cookie管理</p>
 * <p>Description: cookie管理</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class CookieManager {

    public static void setCookie(String key, String value) {
        setCookie(key, value, -1);
    }

    public static void setCookie(String key, String value, int expiry) {
        Cookie cookie = new Cookie(key, EncodeUtil.urlEncode(value));
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        String cookieDomain = ConfigHelper.getValue("cookie.domain");
        if (StringUtil.isNotEmpty(cookieDomain)) {
            cookie.setDomain(cookieDomain);
        }
        ServletContext.getResponse().addCookie(cookie);
    }

    public static String getCookie(String key) {
        Cookie[] cookies = ServletContext.getRequest().getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(key)) {
                return  EncodeUtil.urlEncode(c.getValue());
            }
        }
        return null;
    }
}
