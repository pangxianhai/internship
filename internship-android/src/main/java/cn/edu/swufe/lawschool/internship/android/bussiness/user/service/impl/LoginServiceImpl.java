package cn.edu.swufe.lawschool.internship.android.bussiness.user.service.impl;

import android.content.Context;
import android.util.Log;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.constants.SexType;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.util.JSONUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.ServerService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserType;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.google.gson.JsonObject;
import com.xavier.commons.android.util.NumberUtil;
import com.xavier.commons.android.util.RandomUtil;
import com.xavier.commons.android.util.StringUtil;
import com.xavier.commons.android.util.encrypt.AESUtil;
import com.xavier.commons.android.util.encrypt.EncodeUtil;
import com.xavier.commons.android.util.encrypt.HashUtil;
import com.xavier.commons.android.util.encrypt.RSAUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 2017年03月07
 * <p>Title:       登录服务</p>
 * <p>Description:登录服务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LoginServiceImpl implements LoginService {

    ServerService serverService;

    static final String USER_INFO_FILE = "Y32hx.db";

    public LoginServiceImpl () {
        serverService = ServiceFactory.getInstance().getService("serverService", ServerService.class);
    }

    public UserInfo login (Context context, String userName, String password) {
        AccessParam accessParam = new AccessParam();
        accessParam.setMethod("user.login");
        accessParam.setVersion("1.0");
        accessParam.setParams("userName", userName);
        accessParam.setParams("password", RSAUtil.encryptByPublic(password, SystemInfo.PUBLIC_KEY));
        JsonObject loginInfo = serverService.access(accessParam, JsonObject.class);
        UserInfo userInfo = JSONUtil.fromJson(loginInfo.get("userInfo"), UserInfo.class);
        userInfo.setSessionId(loginInfo.get("sessionId").getAsString());
        String secretKey = RandomUtil.getRandCodeEx(32);
        String secretAllKey = RandomUtil.getRandCodeEx(32);
        Map<String, String> userInfoMap = loginInfoToMap(userInfo);
        userInfoMap.put("sign", sign(userInfoMap, secretKey));
        try{
            FileOutputStream outputStream = context.openFileOutput(USER_INFO_FILE, Context.MODE_PRIVATE);
            String content = AESUtil.encrypt(userInfoToString(userInfoMap, secretKey), secretAllKey);
            content += ";" + secretKey + ";" + secretAllKey;
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(SystemInfo.LOG_TAG, "", e);
        } catch (IOException e) {
            Log.e(SystemInfo.LOG_TAG, "", e);
        }
        return userInfo;
    }

    public UserInfo getLoginInfo (Context context) {
        try{
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int length = 0;
            FileInputStream inputStream = context.openFileInput(USER_INFO_FILE);
            while ((length = inputStream.read(buf)) != -1) {
                bout.write(buf, 0, length);
            }
            byte[] content = bout.toByteArray();
            String contentText = new String(content, SystemInfo.CHARSET_NAME);
            if (StringUtil.isBlank(contentText)) {
                Log.i(SystemInfo.LOG_TAG, "location user info is empty");
                return null;
            }
            String[] contentTexts = contentText.split(";");
            String userInfoStr = contentTexts[0];
            String secretKey = contentTexts[1];
            String secretAllKey = contentTexts[2];
            userInfoStr = AESUtil.decrypt(userInfoStr, secretAllKey);
            Map<String, String> userInfoMap = userInfoStrToMap(userInfoStr, secretKey);
            String sign = userInfoMap.remove("sign");
            userInfoMap.put("deviceId", SystemInfo.DEVICE_ID);
            String _sign = sign(userInfoMap, secretKey);
            if (!_sign.equals(sign)) {
                Log.i(SystemInfo.LOG_TAG, "location user info has falsification");
                return null;
            }
            return userInfoMapToUserInfo(userInfoMap);
        } catch (FileNotFoundException e) {
            Log.e(SystemInfo.LOG_TAG, "", e);
        } catch (IOException e) {
            Log.e(SystemInfo.LOG_TAG, "", e);
        } catch (Exception e) {
            Log.e(SystemInfo.LOG_TAG, "", e);
        }
        return null;
    }

    public boolean isLogin (Context context) {
        return getLoginInfo(context) != null;
    }

    private Map<String, String> loginInfoToMap (UserInfo userInfo) {
        Map<String, String> infoMap = new HashMap<String, String>();
        infoMap.put("sessionId", userInfo.getSessionId());
        infoMap.put("userId", String.valueOf(userInfo.getUserId()));
        infoMap.put("userName", userInfo.getUserName());
        infoMap.put("name", userInfo.getName());
        infoMap.put("phone", userInfo.getPhone());
        infoMap.put("sexCode", String.valueOf(userInfo.getSex().getCode()));
        infoMap.put("userTypeCode", String.valueOf(userInfo.getUserType().getCode()));
        infoMap.put("deviceId", SystemInfo.DEVICE_ID);
        return infoMap;
    }

    private UserInfo userInfoMapToUserInfo (Map<String, String> userInfoMap) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSessionId(userInfoMap.get("sessionId"));
        userInfo.setUserId(NumberUtil.parseLong(userInfoMap.get("userId")));
        userInfo.setUserName(userInfoMap.get("userName"));
        userInfo.setName(userInfoMap.get("name"));
        userInfo.setPhone(userInfoMap.get("phone"));
        userInfo.setSex(SexType.parse(NumberUtil.parseInt(userInfoMap.get("sexCode"))));
        userInfo.setUserType(UserType.parse(NumberUtil.parseInt(userInfoMap.get("userTypeCode"))));
        return userInfo;
    }

    private String sign (Map<String, String> userInfoMap, String secretKey) {
        userInfoMap = new TreeMap<String, String>(userInfoMap);
        String userInfoString = userInfoToString(userInfoMap, secretKey);
        return HashUtil.sha256Hash(userInfoString);
    }

    private String userInfoToString (Map<String, String> userInfoMap, String secretKey) {
        String infoText = "";
        for (String key : userInfoMap.keySet()) {
            String value = AESUtil.encrypt(EncodeUtil.urlEncode(userInfoMap.get(key)), secretKey);
            infoText += key + "=" + value + "&";
        }
        return infoText.substring(0, infoText.length() - 1);
    }

    private Map<String, String> userInfoStrToMap (String content, String secretKey) {
        Map<String, String> infoMap = new HashMap<String, String>();
        for (String info : content.split("&")) {
            String kv[] = info.split("=");
            infoMap.put(kv[0], EncodeUtil.urlDecode(AESUtil.decrypt(kv[1], secretKey)));
        }
        return infoMap;
    }

}
