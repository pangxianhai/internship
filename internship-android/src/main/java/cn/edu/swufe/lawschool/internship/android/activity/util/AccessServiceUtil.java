package cn.edu.swufe.lawschool.internship.android.activity.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.user.LoginActivity;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.CommonExceptionType;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年04月24
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AccessServiceUtil {

    public static void callService (final Context context, final AccessInterface accessInterface) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage (Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                accessInterface.handler((Result<UserInfo>) data.getSerializable("result"));
            }
        };
        new Thread(new Runnable() {
            public void run () {
                Message msg = new Message();
                Bundle data = new Bundle();
                Result result = new Result();
                try{
                    result.setRet(true);
                    result.setData(accessInterface.call());
                } catch (InternshipException e) {
                    if (e.getCode().equals(CommonExceptionType.NEED_SESSION.getCode())) {
                        Intent intent = new Intent();
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    result.setRet(false);
                    result.setCode(e.getCode());
                    result.setMessage(e.getDesc());
                } catch (Exception e) {
                    Log.e(SystemInfo.LOG_TAG, "failed", e);
                    result.setRet(false);
                    result.setCode(CommonExceptionType.SYSTEM_ERROR.getCode());
                    result.setMessage(CommonExceptionType.SYSTEM_ERROR.getDesc());
                }
                data.putSerializable("result", result);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }).start();
    }

    public interface AccessInterface<T> {
        T call ();

        void handler (Result<T> result);
    }

}