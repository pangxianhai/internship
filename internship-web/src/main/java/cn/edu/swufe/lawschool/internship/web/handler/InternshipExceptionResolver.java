package cn.edu.swufe.lawschool.internship.web.handler;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.web.model.ImageResponse;
import cn.edu.swufe.lawschool.internship.web.model.Result;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import com.google.gson.Gson;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.EncodeUtil;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created on 2015年09月25
 * <p>Title:       异常捕获</p>
 * <p>Description: 自定义异常捕获</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipExceptionResolver implements HandlerExceptionResolver {
    @Logger
    private Log log;

    /**
     * Created on 2015年09月25
     * <p>
     * Description:处理异常请求：
     * 1.打印日志
     * 2.如果是json的接口则返回含错误信息标准接口格式
     * 3.如果请求的结果是页面则返回错误页面
     * </p>
     * @author: 庞先海
     * @update: 日期YYYY-MM-DD 更改人姓名
     */
    public ModelAndView resolveException (
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        printLog(request, ex);
        if (ServletUtil.isJsonLink(request)) {
            writeJson(request, response, ex);
            return null;
        } else {
            if (ex instanceof InternshipException) {
                InternshipException me = (InternshipException) ex;
                if (me.getCode().intValue() == ErrorType.NO_LOGIN.getCode().intValue()) {
                    String returnUrl = request.getHeader("Referer");
                    if (StringUtil.isEmpty(returnUrl)) {
                        returnUrl = request.getRequestURI();
                        if (StringUtil.isNotEmpty(request.getQueryString())) {
                            returnUrl += "?" + request.getQueryString();
                        }
                    }
                    if ("/login/index.htm".equals(returnUrl) || "/webApp/login/index.htm".equals(returnUrl)) {
                        returnUrl = null;
                    }
                    if (ServletUtil.isMobile()) {
                        return new ModelAndView("redirect:/webApp/login/index.htm?returnUrl=" +
                                                        EncodeUtil.urlEncode(returnUrl));
                    } else {
                        return new ModelAndView(
                                "redirect:/login/index.htm?returnUrl=" + EncodeUtil.urlEncode(returnUrl));
                    }
                } else {
                    request.setAttribute("error_message", me.getDesc());
                }
            }
            if (ServletUtil.isMobile()) {
                return new ModelAndView("webApp/505");
            } else {
                return new ModelAndView("505");
            }
        }
    }

    private void writeJson (HttpServletRequest request, HttpServletResponse response, Exception ex) {
        Object result;
        if (ex instanceof InternshipException) {
            InternshipException me = (InternshipException) ex;
            if (ServletUtil.isUploadImageLink(request)) {
                result = new ImageResponse(me.getDesc());
            } else {
                result = new Result(false, me.getCode(), me.getDesc());
            }
        } else if (ex instanceof BindException) {
            result = new Result(false, ErrorType.PARSE_PARAM_ERROR.getCode(), ErrorType.PARSE_PARAM_ERROR.getDesc());
        } else {
            if (ServletUtil.isUploadImageLink(request)) {
                result = new ImageResponse(ErrorType.SYS_ERROR.getDesc());
            } else {
                result = new Result(false, ErrorType.SYS_ERROR.getCode(), ErrorType.SYS_ERROR.getDesc());
            }
        }
        try{
            PrintWriter writer = response.getWriter();
            writer.write(ServletUtil.buildPrintJsonData(request, result));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("print data IOException ", ex);
        }
    }

    private void printLog (HttpServletRequest request, Exception ex) {
        String uri = request.getRequestURI();
        String errorInfo;
        if (request.getMethod().toLowerCase().equals("get")) {
            errorInfo = String.format("address:%s,params:%s", uri, request.getQueryString());
        } else {
            //post请求
            Map<String, Object> paramMap = request.getParameterMap();
            String paramsValue = "";
            if (paramMap != null && !paramMap.isEmpty()) {
                for (String k : paramMap.keySet()) {
                    Gson gson = new Gson();
                    String v[] = (String[]) paramMap.get(k);
                    if (v.length > 1) {
                        paramsValue += "&" + k + "=" + gson.toJson(v);
                    } else {
                        paramsValue += "&" + k + "=" + v[0];
                    }
                }
            }
            errorInfo = String.format("address:%s,params:%s", uri, paramsValue);
        }
        log.error(errorInfo, ex);
    }
}
