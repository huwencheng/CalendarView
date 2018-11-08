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
    private MonthBar monthBar;
    private DateView dateView;

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
                visibleCanlendar(false);
                if (delegate != null && monthBar != null && listener != null) {
                    monthBar.updateTitle(DateUtil.getDayStr(delegate.getSelectYear(), delegate.getSelectMonth(), delegate.getSelectDay()));
                    listener.onMaskClick();
                }
            }
        });
        addView(maskView);

        //内容区域
        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(content);

        //月份栏
        monthBar = new MonthBar(context);
        monthBar.setup(this, delegate);
        content.addView(monthBar);

        //分割线
        View line = new View(context);
        line.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LocalDisplay.dip2px(context, 0.5f)));
        line.setBackgroundColor(Color.parseColor("#eeeeee"));
        content.addView(line);

        calendar = new LinearLayout(context);
        calendar.setOrientation(LinearLayout.VERTICAL);
        calendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        calendar.setVisibility(View.GONE);
        content.addView(calendar);

        //星期栏
        WeekBar weekBar = new WeekBar(context);
        weekBar.setup(delegate);
        calendar.addView(weekBar);

        //日期栏
        dateView = new DateView(context);
        dateView.setup(this, monthBar, delegate);
        calendar.addView(dateView);

    }

    public boolean isVisibleMenu() {
        return maskView != null && maskView.getVisibility() == View.VISIBLE;
    }

    public void visibleCanlendar(final boolean isShow) {
        calendar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        calendar.setAnimation(isShow ? AnimationUtils.loadAnimation(getContext(), R.anim.calendar_in) : AnimationUtils.loadAnimation(getContext(), R.anim.calendar_out));
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
        monthBar.setListener(dateView, listener);
        dateView.setListener(listener);
    }

}
