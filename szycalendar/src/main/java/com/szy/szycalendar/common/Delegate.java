package com.szy.szycalendar.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.szy.szycalendar.R;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.utils.DisplayUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/10/30 16:44
 */
public class Delegate {

    private final int textSizeMonth;
    private final int textSizeWeek;
    private final int textSizeDay;
    private final int textSizeHealth;
    private final int textColorWeekDay;
    private int monthRowL;
    private int monthRowR;
    private int textColorMonth;
    private int textColorWeek;
    private int textColorDay;
    private int selectTextColor;
    private int selectBg;
    private float selectRadius;
    private final boolean monthTitleClickEnable;
    private final boolean calendarExEnable;// true:全部隐藏 false:指隐藏周栏和日期栏，保留月份栏
    private Class<?> dateViewClass;

    private Date currentDate;//当前的日期
    private int currentYear;//当前的年
    private int currentMonth;//当前的月
    private int currentDay;//当前的日
    private Date selectDate;//选中的日期
    private int selectDay;//选中的日
    private int selectMonth;//选中的月
    private int selectYear;//选中的年

    private Date currentPageDate; //当前页面的日期
    private int currentPageYear; //当前页面的年
    private int currentPageMonth;//当前页面的月
    private int currentPageDay;//当前页面的日
    private int lastSelectYear;//上一次选中的年（避免造成重复回调请求）
    private int lastSelectMonth;//上一次选中的月（避免造成重复回调请求）
    private int lastSelectDay;//上一次选中的日（避免造成重复回调请求）

    public Delegate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SzyCalendar, defStyleAttr, 0);
        monthRowL = a.getResourceId(R.styleable.SzyCalendar_szyMonthRowLIcon, R.drawable.calendar_row_left);
        monthRowR = a.getResourceId(R.styleable.SzyCalendar_szyMonthRowRIcon, R.drawable.calendar_row_right);

        textColorMonth = a.getColor(R.styleable.SzyCalendar_szyTextColorMonth, Color.parseColor("#333333"));
        textColorWeek = a.getColor(R.styleable.SzyCalendar_szyTextColorWeek, Color.parseColor("#bbbbbd"));
        textColorDay = a.getColor(R.styleable.SzyCalendar_szyTextColorDay, Color.parseColor("#333333"));
        textColorWeekDay = a.getColor(R.styleable.SzyCalendar_szyTextColorWeekDay, Color.parseColor("#bbbbbd"));

        textSizeMonth = a.getDimensionPixelSize(R.styleable.SzyCalendar_szyTextSizeMonth, 15);
        textSizeWeek = a.getDimensionPixelSize(R.styleable.SzyCalendar_szyTextSizeWeek, 12);
        textSizeDay = a.getDimensionPixelSize(R.styleable.SzyCalendar_szyTextSizeDay, 15);
        textSizeHealth = a.getDimensionPixelSize(R.styleable.SzyCalendar_szyTextSizeHealth, 12);

        selectTextColor = a.getColor(R.styleable.SzyCalendar_szySelectTextColor, Color.WHITE);
        selectBg = a.getColor(R.styleable.SzyCalendar_szySelectBg, Color.parseColor("#00aaff"));
        selectRadius = a.getDimension(R.styleable.SzyCalendar_szySelectRadius, DisplayUtil.dip2px(context, 23));

        monthTitleClickEnable = a.getBoolean(R.styleable.SzyCalendar_szyMonthTitleClickEnable, true);
        calendarExEnable = a.getBoolean(R.styleable.SzyCalendar_szyCalendarExEnable, false);
        String dateViewPath = a.getString(R.styleable.SzyCalendar_szyDateViewPath);

        a.recycle();
        init(context, dateViewPath);
    }

    private void init(Context context, String dateViewPath) {
        DisplayUtil.init(context);

        //默认选中的日期为当前日期
        currentDate = selectDate = currentPageDate = new Date();
        Calendar calendar = Calendar.getInstance();
        //获取选中的年
        selectYear = currentYear = currentPageYear = calendar.get(Calendar.YEAR);
        //获取选中的月
        selectMonth = currentMonth = currentPageMonth = calendar.get(Calendar.MONTH) + 1;
        //获取选中的日
        selectDay = currentDay = currentPageDay = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            dateViewClass = TextUtils.isEmpty(dateViewPath) ? BaseDateView.class : Class.forName(dateViewPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getTextSizeMonth() {
        return textSizeMonth;
    }

    public int getTextSizeWeek() {
        return textSizeWeek;
    }

    public int getTextSizeDay() {
        return textSizeDay;
    }

    public int getTextSizeHealth() {
        return textSizeHealth;
    }

    public int getMonthRowL() {
        return monthRowL;
    }

    public int getMonthRowR() {
        return monthRowR;
    }

    public int getTextColorMonth() {
        return textColorMonth;
    }

    public int getTextColorWeek() {
        return textColorWeek;
    }

    public int getTextColorDay() {
        return textColorDay;
    }

    public int getTextColorWeekDay() {
        return textColorWeekDay;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public int getSelectBg() {
        return selectBg;
    }

    public float getSelectRadius() {
        return selectRadius;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        this.selectDay = selectDay;
    }

    public int getSelectMonth() {
        return selectMonth;
    }

    public void setSelectMonth(int selectMonth) {
        this.selectMonth = selectMonth;
    }

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public Date getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(Date selectDate) {
        this.selectDate = selectDate;
    }

    public Date getCurrentPageDate() {
        return currentPageDate;
    }

    public void setCurrentPageDate(Date currentPageDate) {
        this.currentPageDate = currentPageDate;
    }

    public int getCurrentPageYear() {
        return currentPageYear;
    }

    public void setCurrentPageYear(int currentPageYear) {
        this.currentPageYear = currentPageYear;
    }

    public int getCurrentPageMonth() {
        return currentPageMonth;
    }

    public void setCurrentPageMonth(int currentPageMonth) {
        this.currentPageMonth = currentPageMonth;
    }

    public int getCurrentPageDay() {
        return currentPageDay;
    }

    public void setCurrentPageDay(int currentPageDay) {
        this.currentPageDay = currentPageDay;
    }

    public int getLastSelectYear() {
        return lastSelectYear;
    }

    public void setLastSelectYear(int lastSelectYear) {
        this.lastSelectYear = lastSelectYear;
    }

    public int getLastSelectMonth() {
        return lastSelectMonth;
    }

    public void setLastSelectMonth(int lastSelectMonth) {
        this.lastSelectMonth = lastSelectMonth;
    }

    public int getLastSelectDay() {
        return lastSelectDay;
    }

    public void setLastSelectDay(int lastSelectDay) {
        this.lastSelectDay = lastSelectDay;
    }

    public boolean isMonthTitleClickEnable() {
        return monthTitleClickEnable;
    }

    public boolean isCalendarExEnable() {
        return calendarExEnable;
    }

    public Class<?> getDateViewClass() {
        return dateViewClass;
    }

}
