package cn.edu.swufe.lawschool.internship.android.bussiness.server.impl;

import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.CommonExceptionType;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.util.JSONUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessMethod;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.ServerService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.SecretType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;
import com.google.gson.JsonObject;
import com.xavier.commons.android.util.StringUtil;
import com.xavier.commons.android.util.encrypt.HashUtil;
import com.xavier.commons.android.util.http.HttpClientUtil;
import com.xavier.commons.android.util.http.HttpConnConfig;

/**
 * Created on 2017年03月08
 * <p>Title:       服务访问接口实现</p>
 * <p>Description: 服务访问接口实现</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class ServerServiceImpl implements ServerService {

    public <T> T access (AccessParam accessParam, Class<T> typeClass) {
        String result = _access(accessParam);
        return JSONUtil.fromJson(result, typeClass);
    }

    public <T> List<T> accessList (AccessParam accessParam, final Class<T> typeClass) {
        String result = _access(accessParam);

        Type type = new ParameterizedType() {
            public Type[] getActualTypeArguments () {
                return new Type[] { typeClass };
            }

            public Type getRawType () {
                return List.class;
            }

            public Type getOwnerType () {
                return null;
            }
        };
        return JSONUtil.fromJson(result, type);
    }

    public <T> PageResult<List<T>> access (AccessParam accessParam, String dataKey, Class<T> typeClass) {
        PageResult<List<T>> pageResult = new PageResult<List<T>>();
        JsonObject resultObj = access(accessParam, JsonObject.class);
        pageResult.setData(JSONUtil.fromJsonToList(resultObj.get(dataKey), typeClass));
        pageResult.setPage(JSONUtil.fromJson(resultObj.get("page"), Page.class));
        return pageResult;
    }

    private String _access (AccessParam accessParam) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("appId", SystemInfo.APP_ID);
        paramMap.put("method", accessParam.getMethod());
        paramMap.put("version", accessParam.getVersion());
        paramMap.put("signType", SecretType.MD5.getDesc());
        paramMap.put("format", "json");
        if (StringUtil.isNotBlank(accessParam.getSessionId())) {
            paramMap.put("sessionId", accessParam.getSessionId());
        }
        if (accessParam.getParams() != null) {
            for (String key : accessParam.getParams().keySet()) {
                Object value = accessParam.getParams().get(key);
                if (value != null) {
                    paramMap.put(key, value);
                }
            }
        }
        String sign = this.sign(paramMap, SystemInfo.APP_SECRET, SecretType.MD5);
        paramMap.put("sign", sign);
        String result;
        Log.i(SystemInfo.LOG_TAG, SystemInfo.SERVER_ADDRESS + "?" +
                HttpClientUtil.buildSendData(paramMap, SystemInfo.CHARSET_NAME));
        try{
            if (AccessMethod.GET.equals(accessParam.getAccessMethod())) {
                HttpConnConfig httpConnConfig = HttpConnConfig.getDefaultGetHttpConnConfig();
                httpConnConfig.setCharsetName(SystemInfo.CHARSET_NAME);
                result = HttpClientUtil.sendGet(SystemInfo.SERVER_ADDRESS, paramMap, httpConnConfig);
            } else {
                HttpConnConfig httpConnConfig = HttpConnConfig.getDefaultPostHttpConnConfig();
                httpConnConfig.setCharsetName(SystemInfo.CHARSET_NAME);
                result = HttpClientUtil.sendPost(SystemInfo.SERVER_ADDRESS, paramMap, httpConnConfig);
            }
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new InternshipException(CommonExceptionType.RESPONSE_TIMEOUT_ERROR);
            } else if (e instanceof ConnectException) {
                throw new InternshipException(CommonExceptionType.REQUEST_TIMEOUT_ERROR);
            } else {
                throw new InternshipException(CommonExceptionType.SYSTEM_ERROR, e);
            }
        }
        Log.i(SystemInfo.LOG_TAG, "server access result:" + StringUtil.trimToEmpty(result));
        if (StringUtil.isBlank(result)) {
            throw new InternshipException(CommonExceptionType.RESPONSE_TIMEOUT_ERROR);
        }
        Result _result = JSONUtil.fromJson(result, Result.class);
        if (_result.getRet() != null && _result.getRet()) {
            return JSONUtil.toJson(_result.getData());
        } else {
            throw new InternshipException(_result.getCode(), _result.getMessage());
        }
    }

    private String sign (Map<String, Object> dataMap, String secret, SecretType secretType) {
        Map<String, Object> sortDataMap = new TreeMap<String, Object>(dataMap);
        StringBuffer sb = new StringBuffer();
        for (String key : sortDataMap.keySet()) {
            sb.append(key).append("=").append(sortDataMap.get(key)).append("&");
        }
        sb.append(secret);
        String sign;
        if (secretType.equals(SecretType.MD5)) {
            sign = HashUtil.md5Hash(sb.toString(), SystemInfo.CHARSET_NAME);
        } else if (secretType.equals(SecretType.SHA)) {
            sign = HashUtil.shaHash(sb.toString(), SystemInfo.CHARSET_NAME);
        } else if (secretType.equals(SecretType.SHA256)) {
            sign = HashUtil.sha256Hash(sb.toString(), SystemInfo.CHARSET_NAME);
        } else {
            throw new InternshipException(CommonExceptionType.SYSTEM_ERROR, "sign type is not fund");
        }
        return sign;
    }
}
