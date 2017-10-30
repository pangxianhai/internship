package cn.edu.swufe.lawschool.internship.android.activity.leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.activity.util.InternshipDatePickerDialog;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.TimeInterval;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveType;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * Created on 2017年05月11
 * <p>Title:       请假</p>
 * <p>Description: 请假</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @version 1.0
 */
public class LeaveActivity extends Activity {

    LoginService loginService;

    LeaveService leaveService;

    Button beginTimeButton;

    Spinner beginTimeIntervalSpinner;

    Button endTimeButton;

    Spinner endTimeIntervalSpinner;

    Spinner leaveTypeSpinner;

    EditText reasonEditText;

    Button submitButton;

    List<LeaveType> leaveTypeList;

    List<TimeInterval> timeIntervalList;

    UserInfo loginInfo;

    public LeaveActivity () {
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
        leaveService = ServiceFactory.getInstance().getService("leaveService", LeaveService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave);
        loginInfo = loginService.getLoginInfo(this);
        createTopMenu();
        findLeaveView();
        bindSubmitLeaveAction();
    }

    private void createTopMenu () {
        final View thisView = this.getWindow().getDecorView();
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("请假");
        Button right = topMenuHeader.getTopMenuRight();
        right.setText("请假记录");
        right.setBackground(getResources().getDrawable(R.drawable.button_angle_bg));
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent intent = new Intent();
                intent.setClass(LeaveActivity.this, LeaveListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findLeaveView () {
        beginTimeButton = (Button) findViewById(R.id.leave_begin_time_button);
        beginTimeIntervalSpinner = (Spinner) findViewById(R.id.leave_begin_time_interval_spinner);
        endTimeButton = (Button) findViewById(R.id.leave_end_time_button);
        endTimeIntervalSpinner = (Spinner) findViewById(R.id.leave_end_time_interval_spinner);
        leaveTypeSpinner = (Spinner) findViewById(R.id.leave_type_spinner);
        reasonEditText = (EditText) findViewById(R.id.leave_reason_text);
        submitButton = (Button) findViewById(R.id.leave_submit_button);
        leaveTypeList = LeaveType.getValues();
        timeIntervalList = TimeInterval.getValues();

        final InternshipDatePickerDialog beginDatePickerDialog = new InternshipDatePickerDialog(LeaveActivity.this, beginTimeButton, new Date());
        beginTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                beginDatePickerDialog.showDialog();
            }
        });
        final InternshipDatePickerDialog endDatePickerDialog = new InternshipDatePickerDialog(LeaveActivity.this, endTimeButton, new Date());
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                endDatePickerDialog.showDialog();
            }
        });

        ArrayAdapter<TimeInterval> beginTimeIntervalSpinnerAdapter = new ArrayAdapter<TimeInterval>(LeaveActivity.this, android.R.layout.simple_spinner_item, timeIntervalList);
        beginTimeIntervalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beginTimeIntervalSpinner.setAdapter(beginTimeIntervalSpinnerAdapter);

        ArrayAdapter<TimeInterval> endTimeIntervalSpinnerAdapter = new ArrayAdapter<TimeInterval>(LeaveActivity.this, android.R.layout.simple_spinner_item, timeIntervalList);
        endTimeIntervalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTimeIntervalSpinner.setAdapter(endTimeIntervalSpinnerAdapter);

        ArrayAdapter<LeaveType> leaveTypeSpinnerAdapter = new ArrayAdapter<LeaveType>(LeaveActivity.this, android.R.layout.simple_spinner_item, leaveTypeList);
        leaveTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveTypeSpinner.setAdapter(leaveTypeSpinnerAdapter);
    }

    private void bindSubmitLeaveAction () {
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                final LeaveRecordParam recordParam = parseLeaveRecordParam();
                if (StringUtil.isBlank(recordParam.getBeginDayStr())) {
                    AlertDialogActivity.dialog(LeaveActivity.this, "请选择请假开始时间");
                    return;
                }
                if (StringUtil.isBlank(recordParam.getEndDayStr())) {
                    AlertDialogActivity.dialog(LeaveActivity.this, "请选择请假结束时间");
                    return;
                }
                if (StringUtil.isBlank(recordParam.getReason())) {
                    AlertDialogActivity.dialog(LeaveActivity.this, "请输入请假理由");
                    return;
                }
                AccessServiceUtil.callService(LeaveActivity.this, new AccessServiceUtil.AccessInterface<Boolean>() {
                    public Boolean call () {
                        return leaveService.leave(loginInfo, recordParam);
                    }

                    public void handler (Result<Boolean> result) {
                        if (result.getRet()) {
                            if (result.getData()) {
                                AlertDialogActivity.dialog(LeaveActivity.this, "请假信息提交成功");
                            } else {
                                AlertDialogActivity.dialog(LeaveActivity.this, "请假信息提交失败");
                            }
                        } else {
                            AlertDialogActivity.dialog(LeaveActivity.this, result.getMessage());
                        }
                    }
                });
            }
        });
    }

    private LeaveRecordParam parseLeaveRecordParam () {
        LeaveRecordParam recordParam = new LeaveRecordParam();
        recordParam.setBeginDayStr(beginTimeButton.getText().toString());
        recordParam.setBeginTimeInterval(timeIntervalList.get(beginTimeIntervalSpinner.getSelectedItemPosition()));
        recordParam.setEndDayStr(endTimeButton.getText().toString());
        recordParam.setEndTimeInterval(timeIntervalList.get(endTimeIntervalSpinner.getSelectedItemPosition()));
        recordParam.setLeaveType(leaveTypeList.get(leaveTypeSpinner.getSelectedItemPosition()));
        recordParam.setReason(reasonEditText.getText().toString());
        return recordParam;
    }
}
