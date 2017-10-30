package cn.edu.swufe.lawschool.internship.android.activity.user;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.activity.lib.ClearEditText;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;

/**
 * Created on 2017年03月07
 * <p>Title:       登录组件</p>
 * <p>Description: 登录组件</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LoginActivity extends Activity {

    LoginService loginService;

    /**
     * 用户名输入框
     */
    ClearEditText userNameEditText;

    /**
     * 密码输入框
     */
    ClearEditText passwordEditText;

    Dialog loadingDialog;

    public LoginActivity () {
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        userNameEditText = (ClearEditText) this.findViewById(R.id.user_name_edit_text);
        passwordEditText = (ClearEditText) this.findViewById(R.id.password_edit_text);
        Button submitBtn = (Button) this.findViewById(R.id.login_submit_button);
        this.bindSubmitLoginAction(submitBtn);
    }

    //绑定提交按钮点击事件
    private void bindSubmitLoginAction (Button submitBtn) {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                loadingDialog = AlertDialogActivity.loadingDialog(LoginActivity.this, "登录中...", false);
                AccessServiceUtil.callService(LoginActivity.this, new AccessServiceUtil.AccessInterface<UserInfo>() {
                    public UserInfo call () {
                        return loginService.login(LoginActivity.this, userNameEditText.getText().toString(), passwordEditText.getText().toString());
                    }

                    public void handler (Result<UserInfo> result) {
                        AlertDialogActivity.closeDialog(loadingDialog);
                        if (result != null && result.getRet()) {
                            AlertDialogActivity.dialog(LoginActivity.this, "登录成功");
                            UserActivityUtil.jumpToMainActivity(LoginActivity.this, result.getData());
                            finish();
                        } else {
                            AlertDialogActivity.dialog(LoginActivity.this, result.getMessage());
                        }
                    }
                });
            }
        });
    }
}
