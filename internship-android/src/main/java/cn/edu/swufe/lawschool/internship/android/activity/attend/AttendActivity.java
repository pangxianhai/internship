package cn.edu.swufe.lawschool.internship.android.activity.attend;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.activity.util.InternshipDatePickerDialog;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.TimeInterval;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.DateUtil;

import java.util.Date;

/**
 * Created on 2017年05月02
 * <p>Title:       签到页</p>
 * <p>Description: 签到页</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendActivity extends Activity implements View.OnClickListener {
    LoginService loginService;

    AttendService attendService;

    Button attendDateButton;

    InternshipDatePickerDialog datePickerDialog;

    Dialog loadingDialog;

    public AttendActivity () {
        attendService = ServiceFactory.getInstance().getService("attendService", AttendService.class);
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend);
        final View thisView = this.getWindow().getDecorView();
        initTop(thisView);
        attendDateButton = (Button) findViewById(R.id.attend_supplement_attend_date_button);
        datePickerDialog = new InternshipDatePickerDialog(AttendActivity.this, attendDateButton, new Date());
        openDateDialog();
        bindAttendAction();
    }

    private void initTop (View thisView) {
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("签到");
        Button right = topMenuHeader.getTopMenuRight();
        right.setText("签到记录");
        right.setBackground(getResources().getDrawable(R.drawable.button_angle_bg));
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent intent = new Intent();
                intent.setClass(AttendActivity.this, AttendListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDateDialog () {
        attendDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                datePickerDialog.showDialog();
            }
        });
    }

    private void bindAttendAction () {
        Button todayAmAttendButton = (Button) findViewById(R.id.attend_today_am_attend_button);
        Button todayPmAttendButton = (Button) findViewById(R.id.attend_today_pm_attend_button);
        Button supplementAmAttendButton = (Button) findViewById(R.id.attend_supplement_am_attend_button);
        Button supplementPmAttendButton = (Button) findViewById(R.id.attend_supplement_pm_attend_button);
        todayAmAttendButton.setOnClickListener(this);
        todayPmAttendButton.setOnClickListener(this);
        supplementAmAttendButton.setOnClickListener(this);
        supplementPmAttendButton.setOnClickListener(this);
    }

    public void onClick (View v) {
        String attendDay = attendDateButton.getText().toString();
        switch (v.getId()) {
        case R.id.attend_today_am_attend_button:
            attend(DateUtil.format(new Date()), TimeInterval.AM);
            break;
        case R.id.attend_today_pm_attend_button:
            attend(DateUtil.format(new Date()), TimeInterval.PM);
            break;
        case R.id.attend_supplement_am_attend_button:
            if (attendDay.equals("选择补签日期")) {
                AlertDialogActivity.dialog(AttendActivity.this, "请选择补签日期");
            } else {
                attend(attendDay, TimeInterval.AM);
            }
            break;

        case R.id.attend_supplement_pm_attend_button:
            if (attendDay.equals("选择补签日期")) {
                AlertDialogActivity.dialog(AttendActivity.this, "请选择补签日期");
            } else {
                attend(attendDay, TimeInterval.PM);
            }
            break;
        }
    }

    private void attend (final String attendDay, final TimeInterval interval) {
        loadingDialog = AlertDialogActivity.loadingDialog(AttendActivity.this, "正在签到...", false);
        AccessServiceUtil.callService(AttendActivity.this, new AccessServiceUtil.AccessInterface<Boolean>() {
            public Boolean call () {
                UserInfo loginInfo = loginService.getLoginInfo(AttendActivity.this);
                return attendService.studentAttend(loginInfo, attendDay, interval);
            }

            public void handler (Result<Boolean> result) {
                AlertDialogActivity.closeDialog(loadingDialog);
                if (result != null && result.getRet() && result.getData()) {
                    AlertDialogActivity.dialog(AttendActivity.this, "签到成功");
                } else {
                    AlertDialogActivity.dialog(AttendActivity.this, result.getMessage());
                }
            }
        });
    }
}
