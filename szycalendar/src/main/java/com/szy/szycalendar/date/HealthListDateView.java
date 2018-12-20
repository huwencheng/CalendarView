package com.szy.szycalendar.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.szy.szycalendar.R;
import com.szy.szycalendar.annotation.DateAnnotation;
import com.szy.szycalendar.annotation.DateStatus;
import com.szy.szycalendar.bean.HealthBean;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.utils.DateUtil;
import com.szy.szycalendar.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日常三检日历-日期控件
 *
 * @author huwencheng
 * @date 2018/11/27 20:02
 */
public class HealthListDateView extends HealthDateView {

    public HealthListDateView(Context context) {
        super(context);
    }

    @Override
    protected int getSelectTextColor(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthSelectTextColor(day);
    }

    @Override
    protected int getTextColorWeekDay(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthTextColorWeekDay(day);
    }

    @Override
    protected int getTextColorDay(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthTextColorDay(day);
    }

    @Override
    protected boolean drawSelectBg() {
        return false;
    }

    private int getHealthSelectTextColor(int day) {
        int status = matchStatus(day);
        if (status == DateStatus.HealthStatus.NORMAL) {
            return getContext().getResources().getColor(R.color.health_normal);
        } else if (status == DateStatus.HealthStatus.ISSUE) {
            return getContext().getResources().getColor(R.color.health_issue);
        } else {
            return getContext().getResources().getColor(R.color.health_uncheck);
        }
    }

}
