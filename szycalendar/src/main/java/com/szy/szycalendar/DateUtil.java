package com.szy.szycalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/10/31 10:17
 */
public class DateUtil {

    /**
     * 获取年月标题
     */
    public static String getMonthStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(month);
    }

    /**
     * 获取年月标题
     */
    public static String getMonthStr(int year, int month) {
        return year + "-" + month;
    }

    /**
     * 获取年月日标题
     */
    public static String getDayStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(month);
    }

    /**
     * 获取年月日标题
     */
    public static String getDayStr(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

    public static Date str2Date(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过年月日获取到日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 判断一个日期是否是周末，即周六日
     *
     * @return 判断一个日期是否是周末，即周六日
     */
    public static boolean isWeekend(int year, int month, int day) {
        int week = getWeekFormCalendar(year, month, day);
        return week == 0 || week == 6;
    }

    /**
     * 获取某个日期是星期几
     * 测试通过
     *
     * @return 返回某个日期是星期几
     */
    public static int getWeekFormCalendar(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);
        return date.get(Calendar.DAY_OF_WEEK) - 1;
    }


}
