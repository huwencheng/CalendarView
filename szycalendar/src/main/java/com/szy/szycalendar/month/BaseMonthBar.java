package com.szy.szycalendar.month;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.R;
import com.szy.szycalendar.common.Delegate;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.inner.CalendarClickListener;
import com.szy.szycalendar.utils.DateUtil;
import com.szy.szycalendar.utils.DisplayUtil;
import com.szy.szycalendar.utils.DoubleClickUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择月份栏
 *
 * @author huwencheng
 * @date 2018/10/30 19:11
 */
public class BaseMonthBar extends LinearLayout implements View.OnClickListener {

    private TextView tvTitle;
    private CalendarView calendarView;
    private Delegate delegate;
    private CalendarClickListener listener;
    private BaseDateView baseDateView;

    public BaseMonthBar(Context context) {
        this(context, null);
    }

    public BaseMonthBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMonthBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.cv_month_bar, this, true);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        ImageView imgLeft = (ImageView) inflate.findViewById(R.id.img_left);
        ImageView imgRight = (ImageView) inflate.findViewById(R.id.img_right);
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
        if (delegate.isMonthTitleClickEnable()) {
            tvTitle.setOnClickListener(this);
        }
        if (delegate.isCalendarExEnable()) {
            updateTitle(DateUtil.getMonthStr(selectYear, selectMonth));
        } else {
            updateTitle(calendarView.isVisibleMenu() ? DateUtil.getMonthStr(selectYear, selectMonth) : DateUtil.getDayStr(selectYear, selectMonth, selectDay));
        }
    }

    public void setListener(BaseDateView baseDateView, CalendarClickListener listener) {
        this.baseDateView = baseDateView;
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(DisplayUtil.dip2px(getContext(), 45), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        if (DoubleClickUtils.getInstance().isInvalidClick()) {
            return;
        }
        if (delegate != null && baseDateView != null && calendarView != null && listener != null) {
            if (v.getId() == R.id.tv_title) {
                boolean status = calendarView.isVisibleMenu() ? false : true;
                calendarView.visibleCanlendar(status);
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
                    if (delegate.isMonthOnlyBefore()){
                        Date currentDate = delegate.getCurrentDate();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(delegate.getSelectDate());
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        Date selectDate = calendar.getTime();

                        String currentDateStr = DateUtil.getDayStr(currentDate);
                        String selectDateStr = DateUtil.getDayStr(selectDate);

                        int compare = DateUtil.compareTime(currentDateStr, selectDateStr, DateUtil.YMD_LINE);
                        //选中日期<=当前日期 才更新对应日期
                        if (compare == 0 || compare == 1){
                            updateDay(1, false);
                        }else {
                            Toast.makeText(getContext(), R.string.health_beyond, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        updateDay(1, false);
                    }
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
