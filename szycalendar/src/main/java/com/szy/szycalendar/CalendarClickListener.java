package com.szy.szycalendar;

import java.util.Date;

/**
 * @author huwencheng
 * @date 2018/11/1 09:44
 */
public interface CalendarClickListener {

    void onTitleClick();
    void onLeftRowClick(BaseDateView baseDateView, Date date);
    void onRightRowClick(BaseDateView baseDateView, Date date);
    void onDayClick(Date date);
    void onMaskClick();

}
