package com.szy.calenderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.szy.szycalendar.CalendarClickListener;
import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.DateView;

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
            public void onLeftRowClick(DateView dateView) {
                Toast.makeText(MainActivity.this, "onLeftRowClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightRowClick(DateView dateView) {
                Toast.makeText(MainActivity.this, "onRightRowClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDayClick(int day, String dayStr) {
                Toast.makeText(MainActivity.this, "点击了日期："+dayStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMaskClick() {
                Toast.makeText(MainActivity.this, "onMaskClick", Toast.LENGTH_LONG).show();
            }
        });
    }
}
