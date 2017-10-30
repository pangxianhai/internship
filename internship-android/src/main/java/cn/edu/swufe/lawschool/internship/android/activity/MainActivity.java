package cn.edu.swufe.lawschool.internship.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.user.LoginActivity;
import cn.edu.swufe.lawschool.internship.android.activity.user.UserActivityUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.DateUtil;
import com.xavier.commons.android.util.encrypt.AESUtil;
import com.xavier.commons.android.util.encrypt.EncodeUtil;
import com.xavier.commons.android.util.encrypt.HashUtil;
import com.xavier.commons.android.util.encrypt.RSAUtil;

public class MainActivity extends Activity {

    private LoginService loginService;

    public MainActivity () {
        HashUtil.setCharsetName(SystemInfo.CHARSET_NAME);
        EncodeUtil.setCharsetName(SystemInfo.CHARSET_NAME);
        AESUtil.setCharsetName(SystemInfo.CHARSET_NAME);
        RSAUtil.setCharsetName(SystemInfo.CHARSET_NAME);
        DateUtil.setDefaultPattern("yyyy-MM-dd");
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
    }

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            SystemInfo.DEVICE_ID = deviceId;
        } catch (Exception e) {
            Log.e(SystemInfo.LOG_TAG, "get device id error", e);
        }
        UserInfo loginUserInfo = loginService.getLoginInfo(this);
        if (loginUserInfo == null) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        } else {
            UserActivityUtil.jumpToMainActivity(this, loginUserInfo);
        }
        finish();
    }

}
