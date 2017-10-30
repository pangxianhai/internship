package cn.edu.swufe.lawschool.internship.android.activity.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.activity.attend.AttendActivity;
import cn.edu.swufe.lawschool.internship.android.activity.attend.AttendListActivity;
import cn.edu.swufe.lawschool.internship.android.activity.footer.FooterMenu;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.leave.LeaveActivity;
import cn.edu.swufe.lawschool.internship.android.activity.leave.LeaveListActivity;

/**
 * Created on 2017年04月28
 * <p>Title:       学生主页</p>
 * <p>Description: 学生主页</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class StudentMainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        final View thisView = this.getWindow().getDecorView();
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("首页");

        FooterMenu footerMenu = new FooterMenu(thisView);
        footerMenu.getHome().setSelected(true);

        bindAction();
    }

    private void bindAction () {
        Button attendButton = (Button) findViewById(R.id.student_main_attend_button);
        attendButton.setOnClickListener(this);
        Button leaveButton = (Button) findViewById(R.id.student_main_leave_button);
        leaveButton.setOnClickListener(this);
        LinearLayout attendListLinerLayout = (LinearLayout) findViewById(R.id.student_main_attend_list_linear_layout);
        attendListLinerLayout.setOnClickListener(this);
        LinearLayout leaveListLinerLayout = (LinearLayout) findViewById(R.id.student_main_leave_list_linear_layout);
        leaveListLinerLayout.setOnClickListener(this);
    }

    public void onClick (View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.student_main_attend_button:
            intent.setClass(StudentMainActivity.this, AttendActivity.class);
            break;
        case R.id.student_main_attend_list_linear_layout:
            intent.setClass(StudentMainActivity.this, AttendListActivity.class);
            break;
        case R.id.student_main_leave_button:
            intent.setClass(StudentMainActivity.this, LeaveActivity.class);
            break;
        case R.id.student_main_leave_list_linear_layout:
            intent.setClass(StudentMainActivity.this, LeaveListActivity.class);
            break;
        }
        startActivity(intent);
    }
}
