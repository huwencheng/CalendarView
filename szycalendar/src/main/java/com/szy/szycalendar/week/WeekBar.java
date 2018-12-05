package com.szy.szycalendar.week;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.szycalendar.R;
import com.szy.szycalendar.common.Delegate;
import com.szy.szycalendar.utils.LocalDisplay;

/**
 * 星期栏
 *
 * @author huwencheng
 * @date 2018/10/30 17:23
 */
public class WeekBar extends LinearLayout {

    public WeekBar(Context context) {
        this(context, null);
    }

    public WeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.cv_week_bar, this, true);
    }

    public void setup(Delegate delegate) {
        setTextSize(delegate.getTextSizeWeek());
        setTextColor(delegate.getTextColorWeek());
        setBackgroundColor(Color.WHITE);
    }

    private void setTextSize(int size) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextSize(size);
        }
    }

    private void setTextColor(int color) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextColor(color);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(LocalDisplay.dip2px(getContext(), 40), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
