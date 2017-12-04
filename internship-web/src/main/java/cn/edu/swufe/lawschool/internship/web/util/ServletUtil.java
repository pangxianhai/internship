package cn.edu.swufe.lawschool.internship.web.util;

import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UserError;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xavier.commons.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2015年09月25
 * <p>Title:       Servlet 工具类</p>
 * <p>Description: Servlet 工具类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 *
 * @author
 * @version 1.0
 */
public class ServletUtil {

    /**
     * 判断当前请求是不是请求json参数
     *
     * @param request
     * @return true/false
     */
    public static boolean isJsonLink(HttpServletRequest request) {
        return request.getRequestURI().endsWith(".json");
    }

    public static boolean isUploadImageLink(HttpServletRequest request) {
        return request.getRequestURI().equals("/image/upload.json");
    }

    /**
     * 生成支持callback的数据格式
     *
     * @return
     */
    public static String buildPrintJsonData(HttpServletRequest request, Object result) {
        String callback = request.getParameter("callback");
        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
        String text = JSON.toJSONString(result, feature);
        if (StringUtil.isEmpty(callback)) {
            return text;
        } else {
            return String.format("%s(%s)", callback, text);
        }
    }

    public static boolean isMobile() {
        String userAgent = ServletContext.getRequest().getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }
        //先检测几个特定的pc来源
        String[] desktopSysAgents = new String[]{"Windows NT", "compatible; MSIE 9.0;", "Macintosh"};
        for (String d : desktopSysAgents) {
            if (userAgent.toLowerCase().contains(d.toLowerCase())) {
                return false;
            }
        }
        //在通过正则表达式检测是否手机浏览器
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry" +
                "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" +
                "|laystation portable)|nokia|fennec|htc[-_]" +
                "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static String redirectWhenLogin(UserInfo userInfo, String returnUrl) {
        if (StringUtil.isNotEmpty(returnUrl)) {
            return returnUrl;
        } else if (userInfo == null) {
            return "/";
        } else {
            if (userInfo.isTutor()) {
                if (isMobile()) {
                    return "/webApp/tutor/main.htm";
                } else {
                    return "/examine/list.htm";
                }
            } else if (userInfo.isTeacher()) {
                if (isMobile()) {
                    return "/webApp/teacher/main.htm";
                } else {
                    return "/examine/list.htm";
                }
            } else if (userInfo.isSysadmin()) {
                if (isMobile()) {
                    return "/webApp/sysadmin/main.htm";
                } else {
                    return "/user/list.htm";
                }
            } else if (userInfo.isStudent()) {
                if (isMobile()) {
                    return "/webApp/student/main.htm";
                } else {
                    return "/student/attend/list.htm";
                }
            } else {
                throw new InternshipException(UserError.USER_TYPE_EXIST);
            }
        }
    }
}
