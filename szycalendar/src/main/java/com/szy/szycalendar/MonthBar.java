package com.szy.szycalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择月份栏
 *
 * @author huwencheng
 * @date 2018/10/30 19:11
 */
public class MonthBar extends LinearLayout implements View.OnClickListener {

    private TextView tvTitle;
    private CalendarView calendarView;
    private Delegate delegate;
    private CalendarClickListener listener;
    private BaseDateView baseDateView;

    public MonthBar(Context context) {
        this(context, null);
    }

    public MonthBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.cv_month_bar, this, true);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        ImageView imgLeft = (ImageView) inflate.findViewById(R.id.img_left);
        ImageView imgRight = (ImageView) inflate.findViewById(R.id.img_right);
        tvTitle.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    public void setup(CalendarView calendarView, Delegate delegate) {
        this.calendarView = calendarView;
        this.delegate = delegate;
        setBackgroundColor(Color.WHITE);
        int selectYear = delegate.getSelectYear();
        int selectMonth = delegate.getSelectMonth();
        int selectDay = delegate.getSelectDay();
        tvTitle.setTextSize(delegate.getTextSizeMonth());
        tvTitle.setTextColor(delegate.getTextColorMonth());
        updateTitle(calendarView.isVisibleMenu() ? DateUtil.getMonthStr(selectYear, selectMonth) : DateUtil.getDayStr(selectYear, selectMonth, selectDay));
    }

    public void setListener(BaseDateView baseDateView, CalendarClickListener listener) {
        this.baseDateView = baseDateView;
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(LocalDisplay.dip2px(getContext(), 45), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        if (DoubleClickUtils.getInstance().isInvalidClick()) {
            return;
        }
        if (delegate != null && baseDateView != null && calendarView != null && listener != null) {
            if (v.getId() == R.id.tv_title) {
                int selectYear = delegate.getSelectYear();
                int selectMonth = delegate.getSelectMonth();
                int selectDay = delegate.getSelectDay();
                Date selectDate = delegate.getSelectDate();
                boolean status = calendarView.isVisibleMenu() ? false : true;

                //重置当前页面的日期，确保当前显示的页面和选中的页面内容一致
                delegate.setCurrentPageYear(selectYear);
                delegate.setCurrentPageMonth(selectMonth);
                delegate.setCurrentPageDay(selectDay);
                delegate.setCurrentPageDate(selectDate);

                //如果展开，则设置日期为当前选中的页面，并重新绘制日期
                if (status) {
                    baseDateView.monthChange(selectDate);
                }

                calendarView.visibleCanlendar(status);
                updateTitle(status ? DateUtil.getMonthStr(selectYear, selectMonth) : DateUtil.getDayStr(selectYear, selectMonth, selectDay));
                listener.onTitleClick();
            } else if (v.getId() == R.id.img_left) {
                boolean visible = calendarView.isVisibleMenu();
                if (visible) {
                    updateMonth(-1, true);
                } else {
                    updateDay(-1, false);
                }
                listener.onLeftRowClick(baseDateView, delegate.getSelectDate());
            } else if (v.getId() == R.id.img_right) {
                boolean visible = calendarView.isVisibleMenu();
                if (visible) {
                    updateMonth(1, true);
                } else {
                    updateDay(1, false);
                }
                listener.onRightRowClick(baseDateView, delegate.getSelectDate());
            }
        }
    }

    /**
     * 更新月份
     *
     * @param change  改变值
     * @param visible true：更新当前页面的日期， false：更新选中的日期
     */
    private void updateMonth(int change, boolean visible) {
        if (visible) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(delegate.getCurrentPageDate());
            calendar.add(Calendar.MONTH, change);

            delegate.setCurrentPageYear(calendar.get(Calendar.YEAR));
            delegate.setCurrentPageMonth(calendar.get(Calendar.MONTH) + 1);
            delegate.setCurrentPageDay(calendar.get(Calendar.DAY_OF_MONTH));
            delegate.setCurrentPageDate(calendar.getTime());

            updateTitle(DateUtil.getMonthStr(delegate.getCurrentPageDate()));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(delegate.getSelectDate());
            calendar.add(Calendar.MONTH, change);

            delegate.setSelectYear(calendar.get(Calendar.YEAR));
            delegate.setSelectMonth(calendar.get(Calendar.MONTH) + 1);
            delegate.setSelectDay(calendar.get(Calendar.DAY_OF_MONTH));
            delegate.setSelectDate(calendar.getTime());

            updateTitle(DateUtil.getMonthStr(delegate.getSelectDate()));
        }
        baseDateView.monthChange(visible ? delegate.getCurrentPageDate() : delegate.getSelectDate());
    }

    /**
     * 更新日期
     *
     * @param change  改变值
     * @param visible true：更新当前页面的日期， false：更新选中的日期
     */
    private void updateDay(int change, boolean visible) {
        if (visible) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(delegate.getCurrentPageDate());
            calendar.add(Calendar.DAY_OF_MONTH, change);

            delegate.setCurrentPageYear(calendar.get(Calendar.YEAR));
            delegate.setCurrentPageMonth(calendar.get(Calendar.MONTH) + 1);
            delegate.setCurrentPageDay(calendar.get(Calendar.DAY_OF_MONTH));
            delegate.setCurrentPageDate(calendar.getTime());

            updateTitle(DateUtil.getDayStr(delegate.getCurrentPageDate()));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(delegate.getSelectDate());
            calendar.add(Calendar.DAY_OF_MONTH, change);

            delegate.setSelectYear(calendar.get(Calendar.YEAR));
            delegate.setSelectMonth(calendar.get(Calendar.MONTH) + 1);
            delegate.setSelectDay(calendar.get(Calendar.DAY_OF_MONTH));
            delegate.setSelectDate(calendar.getTime());

            updateTitle(DateUtil.getDayStr(delegate.getSelectDate()));
        }
        baseDateView.dayChange(visible ? delegate.getCurrentPageDate() : delegate.getSelectDate());
    }

    public void updateTitle(String date) {
        tvTitle.setText(date);
    }

}
