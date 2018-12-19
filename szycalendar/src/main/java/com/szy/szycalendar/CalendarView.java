package com.szy.szycalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.szy.szycalendar.common.Delegate;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.inner.CalendarClickListener;
import com.szy.szycalendar.month.BaseMonthBar;
import com.szy.szycalendar.utils.DateUtil;
import com.szy.szycalendar.utils.DisplayUtil;
import com.szy.szycalendar.utils.DoubleClickUtils;
import com.szy.szycalendar.week.WeekBar;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * 自定义日历控件
 *
 * @author huwencheng
 * @date 2018/10/30 15:57
 */
public class CalendarView extends FrameLayout {

    private View maskView;
    private int maskColor = 0x99000000;
    private Delegate delegate;
    private CalendarClickListener listener;
    private LinearLayout calendar;
    private BaseMonthBar baseMonthBar;
    private BaseDateView baseDateView;
    private LinearLayout content;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new Delegate(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        //蒙层
        maskView = new View(context);
        maskView.setId(R.id.view_mask);
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setVisibility(View.GONE);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DoubleClickUtils.getInstance().isInvalidClick()) {
                    return;
                }
                if (delegate != null && baseMonthBar != null && listener != null) {
                    visibleCanlendar(false);
                    listener.onMaskClick();
                }
            }
        });
        addView(maskView);

        //内容区域
        content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        content.setVisibility(delegate.isCalendarExEnable() ? View.GONE : View.VISIBLE);
        addView(content);

        //月份栏
        baseMonthBar = new BaseMonthBar(context);
        baseMonthBar.setup(this, delegate);
        content.addView(baseMonthBar);

        //分割线
        View line = new View(context);
        line.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 0.5f)));
        line.setBackgroundColor(Color.parseColor("#eeeeee"));
        content.addView(line);

        calendar = new LinearLayout(context);
        calendar.setOrientation(LinearLayout.VERTICAL);
        calendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        calendar.setVisibility(delegate.isCalendarExEnable() ? View.VISIBLE : View.GONE);
        content.addView(calendar);

        //星期栏
        WeekBar weekBar = new WeekBar(context);
        weekBar.setup(delegate);
        calendar.addView(weekBar);

        //日期栏
        try {
            Constructor<?> constructor = delegate.getDateViewClass().getConstructor(Context.class);
            baseDateView = (BaseDateView) constructor.newInstance(context);
            baseDateView.setup(this, baseMonthBar, delegate);
            calendar.addView(baseDateView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public BaseDateView getDateView() {
        return baseDateView;
    }

    public Delegate getDelegate() {
        return delegate;
    }

    public boolean isVisibleMenu() {
        return maskView != null && maskView.getVisibility() == View.VISIBLE;
    }

    public void visibleCanlendar(final boolean isShow) {
        if (delegate.isCalendarExEnable()) {
            baseMonthBar.updateTitle(DateUtil.getMonthStr(delegate.getSelectYear(), delegate.getSelectMonth()));
        } else {
            baseMonthBar.updateTitle(DateUtil.getDayStr(delegate.getSelectYear(), delegate.getSelectMonth(), delegate.getSelectDay()));
        }

        int selectYear = delegate.getSelectYear();
        int selectMonth = delegate.getSelectMonth();
        int selectDay = delegate.getSelectDay();
        Date selectDate = delegate.getSelectDate();

        //重置当前页面的日期，确保当前显示的页面和选中的页面内容一致
        delegate.setCurrentPageYear(selectYear);
        delegate.setCurrentPageMonth(selectMonth);
        delegate.setCurrentPageDay(selectDay);
        delegate.setCurrentPageDate(selectDate);

        //如果展开，则设置日期为当前选中的页面，并重新绘制日期
        if (isShow) {
            baseDateView.monthChange(selectDate);
        }

        if (delegate.isCalendarExEnable()) {
            content.setVisibility(isShow ? View.VISIBLE : View.GONE);
            content.setAnimation(isShow ? AnimationUtils.loadAnimation(getContext(), R.anim.calendar_in) : AnimationUtils.loadAnimation(getContext(), R.anim.calendar_out));
        } else {
            calendar.setVisibility(isShow ? View.VISIBLE : View.GONE);
            calendar.setAnimation(isShow ? AnimationUtils.loadAnimation(getContext(), R.anim.calendar_in) : AnimationUtils.loadAnimation(getContext(), R.anim.calendar_out));
        }
        Animation animation = isShow ? AnimationUtils.loadAnimation(getContext(), R.anim.calendar_bg_in) : AnimationUtils.loadAnimation(getContext(), R.anim.calendar_bg_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isShow) {
                    maskView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                maskView.setVisibility(isShow ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        maskView.setAnimation(animation);
    }

    public void setOnCalendarClickListener(CalendarClickListener listener) {
        this.listener = listener;
        baseMonthBar.setListener(baseDateView, listener);
        baseDateView.setListener(listener);
    }

}
