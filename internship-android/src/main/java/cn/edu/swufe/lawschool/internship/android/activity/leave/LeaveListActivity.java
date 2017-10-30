package cn.edu.swufe.lawschool.internship.android.activity.leave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.lib.PageView;
import cn.edu.swufe.lawschool.internship.android.activity.lib.PanelLayout;
import cn.edu.swufe.lawschool.internship.android.activity.lib.SwipeLayout;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.model.*;
import cn.edu.swufe.lawschool.internship.android.bussiness.leave.service.LeaveService;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.DateUtil;
import com.xavier.commons.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017年05月11
 * <p>Title:       请假记录</p>
 * <p>Description: 请假记录</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class LeaveListActivity extends Activity {

    LoginService loginService;

    LeaveService leaveService;

    UserInfo loginInfo;

    PageView<LeaveRecord> leaveRecordPageView;

    LeaveRecordSearchParam leaveRecordSearchParam;

    LeaveSearchPanel leaveSearchPanel;

    public LeaveListActivity () {
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
        leaveService = ServiceFactory.getInstance().getService("leaveService", LeaveService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_list);
        loginInfo = loginService.getLoginInfo(this);
        leaveRecordSearchParam = new LeaveRecordSearchParam();
        leaveSearchPanel = new LeaveSearchPanel();
        onCreateTopMenu();
        initLeavePageView();
    }

    private void onCreateTopMenu () {
        final View thisView = this.getWindow().getDecorView();
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("请假记录");
        Button right = topMenuHeader.getTopMenuRight();
        right.setText("筛选");
        right.setBackground(getResources().getDrawable(R.drawable.button_angle_bg));
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                leaveSearchPanel.openPanel();
            }
        });
    }

    private void initLeavePageView () {
        leaveRecordPageView = new PageView(initLeaveListPage());
    }

    private PageView.PageInterface<LeaveRecord> initLeaveListPage () {
        PageView.PageInterface<LeaveRecord> pageInterface = new PageView.PageInterface<LeaveRecord>() {
            public Context getContext () {
                return LeaveListActivity.this;
            }

            public ListView getListView () {
                return (ListView) findViewById(R.id.leave_list_list_view);
            }

            public PageResult<List<LeaveRecord>> loadData (Page page) {
                leaveRecordSearchParam.setPage(page);
                return leaveService.getLeaveRecord(loginInfo, leaveRecordSearchParam);
            }

            public View makeView (View view, final LeaveRecord leaveRecord) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) LeaveListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.leave_list_item, null);
                }
                SwipeLayout swipeLayout = (SwipeLayout) view;
                if (loginInfo.isStudent()) {
                    swipeLayout.setIsScroll(false);
                }
                swipeLayout.close(true);
                TextView beginDayView = (TextView) swipeLayout.findViewById(R.id.leave_list_begin_day);
                beginDayView.setText(DateUtil.format(leaveRecord.getBeginDay()));
                TextView endDayView = (TextView) swipeLayout.findViewById(R.id.leave_list_end_day);
                endDayView.setText(DateUtil.format(leaveRecord.getEndDay()));
                TextView leaveStatusView = (TextView) swipeLayout.findViewById(R.id.leave_list_leave_status_text);
                leaveStatusView.setText(leaveRecord.getLeaveStatus().getDesc());
                TextView studentView = (TextView) swipeLayout.findViewById(R.id.leave_list_student_name_text);
                studentView.setText(leaveRecord.getStudent().getName());
                TextView leaveTypeView = (TextView) swipeLayout.findViewById(R.id.leave_list_leave_type_text);
                leaveTypeView.setText(leaveRecord.getLeaveType().getDesc());
                TextView leaveReasonView = (TextView) swipeLayout.findViewById(R.id.leave_list_leave_reason_text);
                leaveReasonView.setText(StringUtil.substring(leaveRecord.getReason(), 7, "-"));
                swipeLayout.getItemView().setOnClickListener(new View.OnClickListener() {
                    public void onClick (View view) {
                        Intent intent = new Intent(LeaveListActivity.this, LeaveDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("leaveRecord", leaveRecord);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                return view;
            }
        };
        return pageInterface;
    }

    public class LeaveSearchPanel {
        PanelLayout panelLayout;

        Spinner leaveTypeSpinner;

        Spinner leaveStatusSpinner;

        List<LeaveType> leaveTypeList;

        List<LeaveStatus> leaveStatusList;

        public LeaveSearchPanel () {
            panelLayout = (PanelLayout) findViewById(R.id.leave_list_search_panel);
            createLeaveTypeSpinner();
            createLeaveStatusSpinner();
            Button searchConfirmBtn = (Button) panelLayout.findViewById(R.id.leave_list_search_panel_search_button);
            searchConfirmBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    leaveRecordSearchParam = parseParam();
                    leaveRecordPageView.reload();
                    panelLayout.close();
                }
            });
            Button cleanBtn = (Button) panelLayout.findViewById(R.id.leave_list_search_panel_clean_button);
            cleanBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    leaveTypeSpinner.setSelection(0);
                }
            });
        }

        public void openPanel () {
            panelLayout.open();
        }

        private void createLeaveTypeSpinner () {
            leaveTypeSpinner = (Spinner) findViewById(R.id.leave_list_search_panel_leave_type_spinner);
            leaveTypeList = LeaveType.getValues();
            List<String> leaveTypeAdapterValue = new ArrayList<String>(leaveTypeList.size() + 1);
            leaveTypeAdapterValue.add("全部");
            for (LeaveType lt : leaveTypeList) {
                leaveTypeAdapterValue.add(lt.getDesc());
            }
            ArrayAdapter<String> leaveTypeAdapter = new ArrayAdapter<String>(LeaveListActivity.this, android.R.layout.simple_spinner_item, leaveTypeAdapterValue);
            leaveTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            leaveTypeSpinner.setAdapter(leaveTypeAdapter);
        }

        private void createLeaveStatusSpinner () {
            leaveStatusSpinner = (Spinner) findViewById(R.id.leave_list_search_panel_leave_status_spinner);
            leaveStatusList = LeaveStatus.getValues();
            List<String> leaveStatusAdapterValue = new ArrayList<String>(leaveStatusList.size() + 1);
            leaveStatusAdapterValue.add("全部");
            for (LeaveStatus ls : leaveStatusList) {
                leaveStatusAdapterValue.add(ls.getDesc());
            }
            ArrayAdapter<String> leaveStatusAdapter = new ArrayAdapter<String>(LeaveListActivity.this, android.R.layout.simple_spinner_item, leaveStatusAdapterValue);
            leaveStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            leaveStatusSpinner.setAdapter(leaveStatusAdapter);
        }

        private LeaveRecordSearchParam parseParam () {
            LeaveRecordSearchParam leaveRecordSearchParam = new LeaveRecordSearchParam();
            if (leaveTypeSpinner.getSelectedItemPosition() != 0) {
                leaveRecordSearchParam.setLeaveType(leaveTypeList.get(
                        leaveTypeSpinner.getSelectedItemPosition() - 1));
            }
            if (leaveStatusSpinner.getSelectedItemPosition() != 0) {
                leaveRecordSearchParam.setLeaveStatus(leaveStatusList.get(
                        leaveStatusSpinner.getSelectedItemPosition() - 1));
            }

            return leaveRecordSearchParam;
        }
    }

}
