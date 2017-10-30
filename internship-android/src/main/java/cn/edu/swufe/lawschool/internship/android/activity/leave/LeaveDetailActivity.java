package cn.edu.swufe.lawschool.internship.android.activity.leave;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.util.JSONUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.LeaveRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.DateUtil;

import java.util.List;

/**
 * Created on 2017年05月16
 * <p>Title:       请假详情</p>
 * <p>Description: 请假详情</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveDetailActivity extends Activity {

    UserInfo loginInfo;

    LeaveRecord leaveRecord;

    LoginService loginService;

    LeaveService leaveService;

    public LeaveDetailActivity () {
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
        leaveService = ServiceFactory.getInstance().getService("leaveService", LeaveService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_detail);
        Bundle bundle = this.getIntent().getExtras();
        leaveRecord = (LeaveRecord) bundle.getSerializable("leaveRecord");
        loginInfo = loginService.getLoginInfo(LeaveDetailActivity.this);
        createTopMenu();
        buildLeaveInfo();
        buildLeaveFlow();
    }

    private void createTopMenu () {
        final View thisView = this.getWindow().getDecorView();
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("请假详情");
    }

    private void buildLeaveInfo () {
        TextView leaveTypeText = (TextView) findViewById(R.id.leave_detail_leave_type_text);
        leaveTypeText.setText(leaveRecord.getLeaveType().getDesc());
        TextView leaveTimeText = (TextView) findViewById(R.id.leave_detail_leave_time_text);
        leaveTimeText.setText(
                DateUtil.format(leaveRecord.getBeginDay()) + leaveRecord.getBeginTimeInterval().getDesc() +
                        "到" + DateUtil.format(leaveRecord.getEndDay()) +
                        leaveRecord.getEndTimeInterval().getDesc());
        TextView leaveReasonText = (TextView) findViewById(R.id.leave_detail_leave_reason_text);
        leaveReasonText.setText(leaveRecord.getReason());
        TextView leaveStatusText = (TextView) findViewById(R.id.leave_detail_leave_status_text);
        leaveStatusText.setText(leaveRecord.getLeaveStatus().getDesc());
    }

    private void buildLeaveFlow () {
        AccessServiceUtil.callService(LeaveDetailActivity.this, new AccessServiceUtil.AccessInterface<List<FlowRecord>>() {
            public List<FlowRecord> call () {
                return leaveService.getLeaveFlow(loginInfo, leaveRecord.getDesId());
            }

            public void handler (Result<List<FlowRecord>> result) {
                if (result.getRet()) {
                    List<FlowRecord> flowRecordList = result.getData();
                    if (flowRecordList == null) {
                        return;
                    }
                    LinearLayout flowPanel = (LinearLayout) findViewById(R.id.leave_detail_flow_panel);
                    flowPanel.addView(buildLeaveFlowNode(280, 280, "提交申请", R.mipmap.flow_node_pass));
                    for (int i = 0; i < flowRecordList.size(); ++i) {
                        FlowRecord flowRecord = flowRecordList.get(i);
                        if (i == 0) {
                            if (flowRecord.isPass()) {
                                flowPanel.addView(buildLeaveFlowNode(8, 150, "", R.mipmap.flow_v_line_pass));
                            } else {
                                flowPanel.addView(buildLeaveFlowNode(8, 150, "", R.mipmap.flow_v_line_pass_to_no_pass));
                            }
                        } else {
                            FlowRecord _preFlowRecord = flowRecordList.get(i - 1);
                            if (_preFlowRecord.isPass()) {
                                if (flowRecord.isPass()) {
                                    flowPanel.addView(buildLeaveFlowNode(8, 150, "", R.mipmap.flow_v_line_pass));
                                } else {
                                    flowPanel.addView(buildLeaveFlowNode(8, 150, "", R.mipmap.flow_v_line_pass_to_no_pass));
                                }
                            } else {
                                flowPanel.addView(buildLeaveFlowNode(8, 150, "", R.mipmap.flow_v_line_no_pass));
                            }
                        }
                        if (flowRecord.isPass()) {
                            flowPanel.addView(buildLeaveFlowNode(280, 280, flowRecord.getOperateName(), R.mipmap.flow_node_pass));
                        } else if (flowRecord.isNeedExamine() || flowRecord.isApply()) {
                            flowPanel.addView(buildLeaveFlowNode(280, 280, flowRecord.getOperateName(), R.mipmap.flow_node_apply));
                        } else if (flowRecord.isNoPass()) {
                            flowPanel.addView(buildLeaveFlowNode(280, 280, flowRecord.getOperateName(), R.mipmap.flow_node_no_pass));
                        }
                    }
                } else {
                    AlertDialogActivity.dialog(LeaveDetailActivity.this, result.getMessage());
                }
            }
        });
    }

    private TextView buildLeaveFlowNode (int width, int height, String text, int backgroundResource) {
        TextView flowNode = new TextView(this);
        flowNode.setWidth(width);
        flowNode.setHeight(height);
        Resources resources = getBaseContext().getResources();
        Drawable backgroundDrawable = resources.getDrawable(backgroundResource);
        flowNode.setBackground(backgroundDrawable);
        flowNode.setText(text);
        flowNode.setTextColor(Color.rgb(255, 255, 255));
        flowNode.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        flowNode.setLayoutParams(lp);
        return flowNode;
    }
}
