package com.szy.szycalendar;

/**
 * @author huwencheng
 * @date 2018/11/1 09:44
 */
public interface CalendarClickListener {

    void onTitleClick();
    void onLeftRowClick(DateView dateView);
    void onRightRowClick(DateView dateView);
    void onDayClick(int day, String dayStr);
    void onMaskClick();

}
