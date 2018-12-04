package com.szy.szycalendar;

import android.content.Context;

/**
 * 日常三检日历-日期控件
 *
 * @author huwencheng
 * @date 2018/11/27 20:02
 */
public class HealthDateView extends BaseDateView {

    private final String TAG = HealthDateView.class.getSimpleName();

    public HealthDateView(Context context) {
        super(context);
    }

    @Override
    protected boolean isSubPoint() {
        return true;
    }

}
