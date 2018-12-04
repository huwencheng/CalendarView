package com.szy.calenderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.szy.szycalendar.CalendarClickListener;
import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.DateUtil;
import com.szy.szycalendar.BaseDateView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnCalendarClickListener(new CalendarClickListener() {
            @Override
            public void onTitleClick() {
                Toast.makeText(MainActivity.this, "onTitleClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLeftRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(MainActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(MainActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayClick(Date date) {
                Toast.makeText(MainActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMaskClick() {
                Toast.makeText(MainActivity.this, "onMaskClick", Toast.LENGTH_LONG).show();
            }
        });
    }
}
