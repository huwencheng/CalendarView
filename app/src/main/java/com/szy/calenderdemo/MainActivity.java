package com.szy.calenderdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.annotation.DateStatus;
import com.szy.szycalendar.bean.HealthBean;
import com.szy.szycalendar.date.HealthDateView;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.inner.CalendarClickListener;
import com.szy.szycalendar.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHealth = (Button) findViewById(R.id.btn_health);
        btnHealth.setOnClickListener(this);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        final BaseDateView dateView = calendarView.getDateView();
        calendarView.setOnCalendarClickListener(new CalendarClickListener() {
            @Override
            public void onTitleClick() {
                Toast.makeText(MainActivity.this, "onTitleClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLeftRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(MainActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
                HealthDateView healthDateView = (HealthDateView) dateView;

                List<HealthBean> list = new ArrayList<>();
                HealthBean healthBean1 = new HealthBean();
                healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                healthBean1.setCheckDate("2018-11-02");
                list.add(healthBean1);

                HealthBean healthBean2 = new HealthBean();
                healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                healthBean2.setCheckDate("2018-11-18");
                list.add(healthBean2);

                HealthBean healthBean3 = new HealthBean();
                healthBean3.setStatus(DateStatus.HealthStatus.NORMAL);
                healthBean3.setCheckDate("2018-11-19");
                list.add(healthBean3);

                healthDateView.pushData("2018-11", list);
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
        if (dateView != null && dateView instanceof HealthDateView) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HealthDateView healthDateView = (HealthDateView) dateView;

                    List<HealthBean> list = new ArrayList<>();
                    HealthBean healthBean1 = new HealthBean();
                    healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                    healthBean1.setCheckDate("2018-12-10");
                    list.add(healthBean1);

                    HealthBean healthBean2 = new HealthBean();
                    healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                    healthBean2.setCheckDate("2018-12-11");
                    list.add(healthBean2);

                    HealthBean healthBean3 = new HealthBean();
                    healthBean3.setStatus(DateStatus.HealthStatus.UNCHECK);
                    healthBean3.setCheckDate("2018-12-13");
                    list.add(healthBean3);

                    healthDateView.pushData("2018-12", list);
                }
            }, 2000);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_health:
                calendarView.visibleCanlendar(calendarView.isVisibleMenu() ? false : true);
                break;
        }
    }
}
