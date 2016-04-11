package com.liz.mj.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Description: 对日期的常见操作
 * Created by yzr on 2015/5/1.
 */
public class DateUtil {

    //把日期转为字符串
    public static String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(date);
    }

    /**
     *
     * param strDate  日期字符串
     * param template 自定义格式
     * return 自定义格式的日期
     */
    public static Date stringToDate(String strDate,String template) throws Exception {
        DateFormat df = new SimpleDateFormat(template, Locale.getDefault());
        return df.parse(strDate);
    }
    //String 转化Calendar 
    public static Calendar stringToCalendar(String strDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(strDate,"yyyy-MM-dd"));
        return calendar;
    }
    //String 转化Calendar 
    public static Calendar stringToCalendar(String strDate,String template) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(strDate,template));
        return calendar;
    }
}
