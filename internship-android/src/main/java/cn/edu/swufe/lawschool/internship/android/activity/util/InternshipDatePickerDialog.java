package cn.edu.swufe.lawschool.internship.android.activity.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import com.xavier.commons.android.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2017年05月03
 * <p>Title:       日历框工具</p>
 * <p>Description: 日历框工具</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipDatePickerDialog {
    private int year;

    private int month;

    private int date;

    /**
     * 主界面
     */
    private Context context;

    /**
     * 展示时间的控件
     */
    private TextView showDateView;

    public InternshipDatePickerDialog (Context context, TextView showDateView) {
        this(context, showDateView, new Date());
    }

    public InternshipDatePickerDialog (Context context, TextView showDateView, Date date) {
        this.context = context;
        this.showDateView = showDateView;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.date = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void showDialog () {
        Dialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet (DatePicker datePicker, int y, int m, int d) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(y, m, d);
                showDateView.setText(DateUtil.format(calendar.getTime()));
                year = y;
                month = m;
                date = d;
            }
        }, year, month, date);
        datePickerDialog.show();
        return;
    }
}
