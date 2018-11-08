package com.szy.szycalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    void setup(Delegate delegate) {
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

}
