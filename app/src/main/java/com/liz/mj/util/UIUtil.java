package com.liz.mj.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * description: 一些工具类 <br>
 * author: dongyeforever@foxmail.com <br>
 * date: 2015/5/1 14:39 <br>
 */
public class UIUtil {

    private static DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
    /**
     *
     * Description: 随意隐藏年月日
     * Title: showDateDialog
     * @param context,listener 年，月，日 ---如果年月日某项数字小于0，将该项隐藏
     */
    public static void showDateDialog(Context context,
                                      DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog mDialog = new DatePickerDialog(context, listener,year,month,day);
        //限制最大日期为当前日期
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        mDialog.show();

        DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow().getDecorView());
        if (dp != null) {
            if (day < 0) {
                // 隐藏日
                ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
                        .getChildAt(2).setVisibility(View.GONE);
            }
            if (month < 0) {
                // 隐藏月
                ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
                        .getChildAt(1).setVisibility(View.GONE);
            }
            if (year < 0) {
                // 隐藏年
                ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
                        .getChildAt(0).setVisibility(View.GONE);
            }
        }
    }

    /**
     * description: tv显示，日期选择 <br>
     * author: dongyeforever@foxmail.com <br>
     * date: 2015/6/10 17:30 <br>
     * version: 1.0.1
     */
    public static void showDateDialog(final Activity context, final TextView tv) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String s,b;
                if ((monthOfYear + 1) < 10)
                    s = "0" + (monthOfYear + 1);
                else
                    s = (monthOfYear + 1) + "";
                if (dayOfMonth < 10)
                    b = "0" + dayOfMonth;
                else
                    b = dayOfMonth + "";
                tv.setText(year + "-" + s + "-" + b);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,datepicker, mYear, mMonth, mDay);
        //限制最大日期为当前日期
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    /**
     * description: tv显示，日期选择 <br>
     * author: dongyeforever@foxmail.com <br>
     * date: 2015/6/10 17:30 <br>
     * version: 1.0.1
     */
    public static void showDateDialog(final Activity context, final TextView tv,int year,int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String s,b;
                if ((monthOfYear + 1) < 10)
                    s = "0" + (monthOfYear + 1);
                else
                    s = (monthOfYear + 1) + "";
                if (dayOfMonth < 10)
                    b = "0" + dayOfMonth;
                else
                    b = dayOfMonth + "";
                tv.setText(year + "-" + s + "-" + b);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,datepicker, year, monthOfYear, dayOfMonth);
        //限制最大日期为当前日期
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}
