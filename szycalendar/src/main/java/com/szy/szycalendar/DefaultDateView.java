package com.szy.szycalendar;

import android.content.Context;

/**
 * 默认日期控件
 *
 * @author huwencheng
 * @date 2018/11/27 19:56
 */
public class DefaultDateView extends BaseDateView {

    private final String TAG = DefaultDateView.class.getSimpleName();

    public DefaultDateView(Context context) {
        super(context);
    }

    @Override
    protected boolean isSubPoint() {
        return false;
    }

}
