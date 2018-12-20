package com.szy.szycalendar.month;

import android.content.Context;
import android.widget.Toast;

import com.szy.szycalendar.R;
import com.szy.szycalendar.month.base.BaseMonthBar;
import com.szy.szycalendar.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/12/20.
 */

public class HealthLimitMonthBar extends BaseMonthBar {

    public HealthLimitMonthBar(Context context) {
        super(context);
    }

    @Override
    protected void updateRightDay() {
        Date currentDate = delegate.getCurrentDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(delegate.getSelectDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date selectDate = calendar.getTime();

        String currentDateStr = DateUtil.getDayStr(currentDate);
        String selectDateStr = DateUtil.getDayStr(selectDate);

        int compare = DateUtil.compareTime(currentDateStr, selectDateStr, DateUtil.YMD_LINE);
        //选中日期<=当前日期 才更新对应日期
        if (compare == 0 || compare == 1) {
            updateDay(1, false);
        } else {
            Toast.makeText(getContext(), R.string.health_beyond, Toast.LENGTH_LONG).show();
            return;
        }
        listener.onRightRowClick(baseDateView, delegate.getSelectDate());
    }
}
