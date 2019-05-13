package com.szy.szycalendar.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/10/31 10:17
 */
public class DateUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final int YMDHMS_LINE = 11;
    /**
     * yyyy-MM-dd
     */
    public static final int YMD_LINE = 12;
    /**
     * MM-dd
     */
    public static final int MD_LINE = 13;
    /**
     * HH:mm
     */
    public static final int HM = 14;
    /**
     * yyyy年MM月dd日
     */
    public static final int YMD_CHIN = 22;
    /**
     * MM月dd日
     */
    public static final int MD_CHIN = 23;
    /**
     * MM月dd日 HH:mm
     */
    public static final int MDHM_CHIN = 24;
    /**
     * MM-dd HH:mm
     */
    public static final int MDHM_LINE = 25;
    /**
     * yyyy.MM.dd
     */
    public static final int YMD_DOT = 32;

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final int YMD_HM = 33;

    public static final int YMD_HH_MM_SS =34;

    private static SimpleDateFormat ymdhms = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymdhm = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ymd_dot = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat nyr = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat yr = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat yrhm = new SimpleDateFormat("MM月dd日 HH:mm");
    private static SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat mdhm = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat yrhms = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

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
        return year + "-" + String.format("%02d", month);
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
        return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
    }

    public static Date str2DateYM(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date str2DateYMD(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param agodate
     * @param afterdate
     * @param type
     * @return 0为相等、1为agodate前、-1为afterdate前
     */
    public static int compareTime(String agodate, String afterdate, int type) {
        try {
            if (agodate.equals(afterdate)) {
                return 0;
            } else if (getTimeLong(getTimeCalendar(agodate, type)) > getTimeLong(getTimeCalendar(
                    afterdate, type))) {
                return 1;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param agodate   时间1
     * @param agotype   时间1类型
     * @param afterdate 时间2
     * @param aftertype 时间2类型
     * @return 0为相等、1为agodate前、-1为afterdate前
     */
    public static int compareTime(String agodate, int agotype,
                                  String afterdate, int aftertype) {
        if (agotype != aftertype) {
            afterdate = transformTimeString(afterdate, agotype, aftertype);
        }
        return compareTime(agodate, afterdate, agotype);
    }

    /**
     * 获取传入时间的对应时间long值
     *
     * @param calendar
     * @return
     */
    public static long getTimeLong(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    /**
     * 根据类型获取传入时间calendar的对应字符串
     *
     * @param calendar
     * @param type
     * @return
     */
    public static String getTimeString(Calendar calendar, int type) {
        return getTimeString(calendar.getTime(), type);
    }

    /**
     * 将Date类型时间转为对应type的字符串
     *
     * @param type
     * @return
     */
    @SuppressWarnings("finally")
    public static String getTimeString(Date date, int type) {
        String retStr = "";
        try {
            switch (type) {
                case YMDHMS_LINE:
                    retStr = ymdhms.format(date);
                    break;

                case YMD_LINE:
                    retStr = ymd.format(date);
                    break;
                case MD_LINE:
                    retStr = md.format(date);
                    break;
                case HM:
                    retStr = hm.format(date);
                    break;
                case YMD_CHIN:
                    retStr = nyr.format(date);
                    break;
                case MD_CHIN:
                    retStr = yr.format(date);
                    break;
                case MDHM_CHIN:
                    retStr = yrhm.format(date);
                    break;
                case YMD_DOT:
                    retStr = ymd_dot.format(date);
                    break;
                case MDHM_LINE:
                    retStr = mdhm.format(date);
                    break;
                case YMD_HH_MM_SS:
                    retStr = yrhms.format(date);
                    break;
                case YMD_HM:
                    retStr = ymdhm.format(date);
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d("TimeTransformation", "时间转换字符串失败！");
        } finally {
            return retStr;
        }
    }

    /**
     * 根据类型获取传入字符串的时间 、-1为当前时间
     *
     * @param date
     * @param type
     * @return
     */
    @SuppressWarnings("finally")
    public static Calendar getTimeCalendar(String date, int type) {
        Calendar calendar = Calendar.getInstance();
        try {
            switch (type) {
                case YMDHMS_LINE:
                    calendar.setTime(ymdhms.parse(date));
                    break;
                case YMD_LINE:
                    calendar.setTime(ymd.parse(date));
                    break;
                case YMD_CHIN:
                    calendar.setTime(nyr.parse(date));
                    break;
                case YMD_DOT:
                    calendar.setTime(ymd_dot.parse(date));
                    break;
                case MD_CHIN:
                    calendar.setTime(yr.parse(date));
                    break;
                case MD_LINE:
                    calendar.setTime(md.parse(date));
                    break;
                case HM:
                    calendar.setTime(hm.parse(date));
                    break;
                case MDHM_CHIN:
                    calendar.setTime(yrhm.parse(date));
                    break;
                case MDHM_LINE:
                    calendar.setTime(mdhm.parse(date));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d("TimeTransformation", "字符串转换时间失败！");
        } finally {
            return calendar;
        }
    }

    /**
     * 将一格式字符串转为另一格式字符串
     *
     * @param date    转换前时间字符串
     * @param newtype 转换后格式
     * @param oldtype 转换前格式
     * @return
     */
    public static String transformTimeString(String date, int newtype, int oldtype) {
        try {
            if (newtype == oldtype) {
                return date;
            }
            String newdate = getTimeString(getTimeCalendar(date, oldtype), newtype);
            return TextUtils.isEmpty(newdate) ? date : newdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



}
