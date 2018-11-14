package com.szy.szycalendar;

import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/11/1 09:44
 */
public interface CalendarClickListener {

    void onTitleClick();
    void onLeftRowClick(DateView dateView, Date date);
    void onRightRowClick(DateView dateView, Date date);
    void onDayClick(Date date);
    void onMaskClick();

}