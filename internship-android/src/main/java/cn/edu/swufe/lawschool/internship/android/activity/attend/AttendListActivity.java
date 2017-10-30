package cn.edu.swufe.lawschool.internship.android.activity.attend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.ServiceFactory;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.header.HeaderMenu;
import cn.edu.swufe.lawschool.internship.android.activity.lib.PageView;
import cn.edu.swufe.lawschool.internship.android.activity.lib.PanelLayout;
import cn.edu.swufe.lawschool.internship.android.activity.lib.SwipeLayout;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.activity.util.InternshipDatePickerDialog;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendStatus;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.service.AttendService;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.service.LoginService;
import com.xavier.commons.android.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       签到列表</p>
 * <p>Description: 签到列表</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendListActivity extends Activity {

    LoginService loginService;

    AttendService attendService;

    UserInfo loginInfo;

    PageView<AttendRecord> attendRecordPageView;

    AttendListSearchPanel attendListSearchPanel;

    AttendRecordParam attendRecordParam;

    public AttendListActivity () {
        attendService = ServiceFactory.getInstance().getService("attendService", AttendService.class);
        loginService = ServiceFactory.getInstance().getService("loginService", LoginService.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_list);
        loginInfo = loginService.getLoginInfo(this);
        attendListSearchPanel = new AttendListSearchPanel();
        attendRecordParam = new AttendRecordParam();
        initTopHeadMenu();
        initAttendList();
    }

    private void initAttendList () {
        attendRecordPageView = new PageView(initAttendListPage());
    }

    private void initTopHeadMenu () {
        final View thisView = this.getWindow().getDecorView();
        HeaderMenu topMenuHeader = new HeaderMenu(thisView);
        topMenuHeader.getTopMenuTitle().setText("签到记录");
        Button right = topMenuHeader.getTopMenuRight();
        right.setText("筛选");
        right.setBackground(getResources().getDrawable(R.drawable.button_angle_bg));
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                attendListSearchPanel.openPanel();
            }
        });
    }

    private void bindDeleteAttendAction (TextView deleteView, final String desId,
                                         final SwipeLayout swipeView) {
        deleteView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                swipeView.close(true);
                AlertDialogActivity.yesNoDialog(AttendListActivity.this, "系统提示", "你确定要删除该记录吗?", new AlertDialogActivity.Callback() {
                    public void yesClick () {
                        deleteAttendRecord(desId);
                    }
                });
            }
        });
    }

    private void deleteAttendRecord (final String desId) {
        AccessServiceUtil.callService(AttendListActivity.this, new AccessServiceUtil.AccessInterface<Boolean>() {
            public Boolean call () {
                return attendService.deleteAttend(loginInfo, desId);
            }

            public void handler (Result<Boolean> result) {
                if (result.getRet() && result.getData()) {
                    AlertDialogActivity.dialog(AttendListActivity.this, "删除成功");
                    for (AttendRecord record : attendRecordPageView.getDataList()) {
                        if (record.getDesId().equals(desId)) {
                            attendRecordPageView.getDataList().remove(record);
                            attendRecordPageView.changeListView();
                            break;
                        }
                    }
                } else {
                    AlertDialogActivity.dialog(AttendListActivity.this, result.getMessage());
                }
            }
        });
    }

    private PageView.PageInterface<AttendRecord> initAttendListPage () {
        PageView.PageInterface<AttendRecord> pageInterface = new PageView.PageInterface<AttendRecord>() {
            public Context getContext () {
                return AttendListActivity.this;
            }

            public ListView getListView () {
                return (ListView) findViewById(R.id.attend_list_list_view);
            }

            public PageResult<List<AttendRecord>> loadData (Page page) {
                attendRecordParam.setPage(page);
                return attendService.getAttendRecord(loginInfo, attendRecordParam);
            }

            public View makeView (View view, AttendRecord attendRecord) {
                try{
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) AttendListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.attend_list_item, null);
                    }
                    SwipeLayout itemView = (SwipeLayout) view;
                    TextView deleteView = (TextView) itemView.findViewById(R.id.attend_list_delete_view);
                    TextView rightwardArrow = (TextView) itemView.findViewById(R.id.attend_list_arrow_rightward);
                    if (!attendRecord.isApply()) {
                        itemView.setIsScroll(false);
                        rightwardArrow.setVisibility(View.GONE);
                    } else {
                        itemView.setIsScroll(true);
                        rightwardArrow.setVisibility(View.VISIBLE);
                    }
                    itemView.close(true);
                    TextView attendDateText = (TextView) itemView.findViewById(R.id.attend_list_attend_date);
                    attendDateText.setText(attendRecord.getAttendDayShow());
                    TextView attendTimeIntervalText = (TextView) itemView.findViewById(R.id.attend_list_attend_time_interval_text);
                    attendTimeIntervalText.setText(attendRecord.getTimeInterval().getDesc());
                    TextView attendStatusText = (TextView) itemView.findViewById(R.id.attend_list_attend_status_text);
                    attendStatusText.setText(attendRecord.getAttendStatus().getDesc());
                    TextView attendStudentNameText = (TextView) itemView.findViewById(R.id.attend_list_student_name_text);
                    attendStudentNameText.setText(attendRecord.getStudent().getName());
                    TextView attendStudentNumberText = (TextView) itemView.findViewById(R.id.attend_list_student_number_text);
                    attendStudentNumberText.setText(attendRecord.getStudent().getStudentNumber());
                    TextView attendOperatorNameText = (TextView) itemView.findViewById(R.id.attend_list_operator_name_text);
                    if (CollectionUtil.isNotEmpty(attendRecord.getFlowRecords())) {
                        attendOperatorNameText.setText(attendRecord.getFlowRecords().get(0).getOperateName());
                    } else {
                        attendOperatorNameText.setText("-");
                    }
                    bindDeleteAttendAction(deleteView, attendRecord.getDesId(), itemView);
                    return itemView;
                } catch (Exception e) {
                    Log.e(SystemInfo.LOG_TAG, "push attend list failed", e);
                }
                return view;
            }
        };
        return pageInterface;
    }

    public class AttendListSearchPanel {
        PanelLayout panelLayout;

        Button searchBeginDateButton;

        Button searchEndDateButton;

        Spinner searchStatusSpinner;

        List<AttendStatus> selectAttendStatus;

        public AttendListSearchPanel () {
            panelLayout = (PanelLayout) findViewById(R.id.attend_list_search_panel);
            Button searchConfirmBtn = (Button) panelLayout.findViewById(R.id.attend_list_search_btn);
            searchConfirmBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    attendRecordParam = parseAttendRecordParam();
                    attendRecordPageView.reload();
                    panelLayout.close();
                }
            });
            Button cleanBtn = (Button) panelLayout.findViewById(R.id.attend_list_search_panel_clean_button);
            cleanBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    searchBeginDateButton.setText("");
                    searchEndDateButton.setText("");
                    searchStatusSpinner.setSelection(0);
                }
            });
            initSearchPanelDatePicker();
            initSearchPanelStatusSpinner();
        }

        public void openPanel () {
            panelLayout.open();
        }

        private void initSearchPanelStatusSpinner () {
            searchStatusSpinner = (Spinner) panelLayout.findViewById(R.id.attend_list_search_status_spinner);
            selectAttendStatus = AttendStatus.getValues();
            List<String> selectAttendStatusStr = new ArrayList<String>(selectAttendStatus.size() + 1);
            selectAttendStatusStr.add("全部");
            for (AttendStatus as : selectAttendStatus) {
                selectAttendStatusStr.add(as.getDesc());
            }
            ArrayAdapter<String> searchStatusSpinnerAdapter = new ArrayAdapter<String>(AttendListActivity.this, android.R.layout.simple_spinner_item, selectAttendStatusStr);
            searchStatusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            searchStatusSpinner.setAdapter(searchStatusSpinnerAdapter);
        }

        private void initSearchPanelDatePicker () {
            searchBeginDateButton = (Button) panelLayout.findViewById(R.id.attend_list_search_begin_date_button);
            searchEndDateButton = (Button) panelLayout.findViewById(R.id.attend_list_search_end_date_button);
            final InternshipDatePickerDialog beginPickerDialog = new InternshipDatePickerDialog(AttendListActivity.this, searchBeginDateButton, new Date());
            final InternshipDatePickerDialog endPickerDialog = new InternshipDatePickerDialog(AttendListActivity.this, searchEndDateButton, new Date());
            searchBeginDateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    beginPickerDialog.showDialog();
                }
            });
            searchEndDateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    endPickerDialog.showDialog();
                }
            });
        }

        private AttendRecordParam parseAttendRecordParam () {
            AttendRecordParam attendRecordParam = new AttendRecordParam();
            attendRecordParam.setBeginTime(searchBeginDateButton.getText().toString());
            attendRecordParam.setEndTime(searchEndDateButton.getText().toString());
            if (searchStatusSpinner.getSelectedItemPosition() != 0) {
                attendRecordParam.setAttendStatus(selectAttendStatus.get(
                        searchStatusSpinner.getSelectedItemPosition() - 1));
            }
            return attendRecordParam;
        }
    }
}
