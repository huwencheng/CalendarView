package com.szy.szycalendar.date;

import android.content.Context;
import android.widget.Toast;

import com.szy.szycalendar.R;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.utils.DateUtil;

import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/12/20.
 */

public class HealthLimitDateView extends BaseDateView {

    public HealthLimitDateView(Context context) {
        super(context);
    }

    @Override
    protected void updateSelectDay(int day, boolean eventEnd) {
        Date currentDate = delegate.getCurrentDate();

        String currentDateStr = DateUtil.getDayStr(currentDate);
        String selectDateStr = DateUtil.getDayStr(delegate.getCurrentPageYear(), delegate.getCurrentPageMonth(), day);

        int compare = DateUtil.compareTime(currentDateStr, selectDateStr, DateUtil.YMD_LINE);
        //选中日期<=当前日期 才更新对应日期
        if (compare == 0 || compare == 1) {
            updateSelectInfo(day, eventEnd);
        } else {
            Toast.makeText(getContext(), R.string.health_beyond, Toast.LENGTH_LONG).show();
            return;
        }
        listener.onDayClick(delegate.getSelectDate());
    }

}
