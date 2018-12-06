package com.szy.calenderdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.inner.CalendarClickListener;
import com.szy.szycalendar.utils.DateUtil;

import java.util.Date;

public class DefaultCalendarActivity extends AppCompatActivity {

    public static void start(Context ctx) {
        Intent intent = new Intent(ctx, DefaultCalendarActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnCalendarClickListener(new CalendarClickListener() {
            @Override
            public void onTitleClick() {
                Toast.makeText(DefaultCalendarActivity.this, "onTitleClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLeftRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(DefaultCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(DefaultCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayClick(Date date) {
                Toast.makeText(DefaultCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMaskClick() {
                Toast.makeText(DefaultCalendarActivity.this, "onMaskClick", Toast.LENGTH_LONG).show();
            }
        });
    }
}
