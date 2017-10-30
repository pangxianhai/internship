package cn.edu.swufe.lawschool.internship.android.activity.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.student.StudentMainActivity;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年04月28
 * <p>Title:       user页面工具 </p>
 * <p>Description:  user页面工具</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class UserActivityUtil {

    public static void jumpToMainActivity (Context context, UserInfo userInfo) {
        try{
            Intent intent = new Intent();
            if (userInfo.isStudent()) {
                intent.setClass(context, StudentMainActivity.class);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(SystemInfo.LOG_TAG, "跳转错误:", e);
        }

    }
}
