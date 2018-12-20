package com.szy.calenderdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HealthListCalendarActivity extends AppCompatActivity implements View.OnClickListener{

    public static void start(Context ctx) {
        Intent intent = new Intent(ctx, HealthListCalendarActivity.class);
        ctx.startActivity(intent);
    }

    private CalendarView calendarView;
    private BaseDateView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_list_calendar);
        Button btnHealth = (Button) findViewById(R.id.btn_health);
        btnHealth.setOnClickListener(this);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateView = calendarView.getDateView();
        calendarView.setOnCalendarClickListener(new CalendarClickListener() {
            @Override
            public void onTitleClick() {
                Toast.makeText(HealthListCalendarActivity.this, "onTitleClick", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLeftRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(HealthListCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
                if (dateView != null && dateView instanceof HealthDateView) {
                    final HealthDateView healthDateView = (HealthDateView) dateView;
                    healthDateView.clearHealthData();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String monthStr = DateUtil.getMonthStr(calendarView.getDelegate().getCurrentPageDate());
                            List<HealthBean> list = new ArrayList<>();
                            HealthBean healthBean1 = new HealthBean();
                            healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                            healthBean1.setCheckDate(monthStr + "-05");
                            list.add(healthBean1);

                            HealthBean healthBean2 = new HealthBean();
                            healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                            healthBean2.setCheckDate(monthStr + "-22");
                            list.add(healthBean2);

                            HealthBean healthBean3 = new HealthBean();
                            healthBean3.setStatus(DateStatus.HealthStatus.ISSUE);
                            healthBean3.setCheckDate(monthStr + "-" + calendarView.getDelegate().getCurrentDay());
                            list.add(healthBean3);

                            healthDateView.pushData(monthStr, list);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onRightRowClick(BaseDateView baseDateView, Date date) {
                Toast.makeText(HealthListCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
                if (dateView != null && dateView instanceof HealthDateView) {
                    final HealthDateView healthDateView = (HealthDateView) dateView;
                    healthDateView.clearHealthData();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String monthStr = DateUtil.getMonthStr(calendarView.getDelegate().getCurrentPageDate());
                            List<HealthBean> list = new ArrayList<>();
                            HealthBean healthBean1 = new HealthBean();
                            healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                            healthBean1.setCheckDate(monthStr + "-07");
                            list.add(healthBean1);

                            HealthBean healthBean2 = new HealthBean();
                            healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                            healthBean2.setCheckDate(monthStr + "-08");
                            list.add(healthBean2);

                            HealthBean healthBean3 = new HealthBean();
                            healthBean3.setStatus(DateStatus.HealthStatus.ISSUE);
                            healthBean3.setCheckDate(monthStr + "-" + calendarView.getDelegate().getCurrentDay());
                            list.add(healthBean3);

                            healthDateView.pushData(monthStr, list);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onDayClick(Date date) {
                Toast.makeText(HealthListCalendarActivity.this, "点击了日期：" + DateUtil.getDayStr(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMaskClick() {
                Toast.makeText(HealthListCalendarActivity.this, "onMaskClick", Toast.LENGTH_LONG).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dateView != null && dateView instanceof HealthDateView) {
                    HealthDateView healthDateView = (HealthDateView) dateView;
                    Date selectDate = calendarView.getDelegate().getSelectDate();
                    String monthStr = DateUtil.getMonthStr(selectDate);
                    List<HealthBean> list = new ArrayList<>();
                    HealthBean healthBean1 = new HealthBean();
                    healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                    healthBean1.setCheckDate(monthStr + "-10");
                    list.add(healthBean1);

                    HealthBean healthBean2 = new HealthBean();
                    healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                    healthBean2.setCheckDate(monthStr + "-11");
                    list.add(healthBean2);

                    HealthBean healthBean3 = new HealthBean();
                    healthBean3.setStatus(DateStatus.HealthStatus.ISSUE);
                    healthBean3.setCheckDate(monthStr + "-" + calendarView.getDelegate().getCurrentDay());
                    list.add(healthBean3);

                    healthDateView.pushData(monthStr, list);
                }
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_health:
                if (dateView != null && dateView instanceof HealthDateView) {
                    HealthDateView healthDateView = (HealthDateView) dateView;
                    Date selectDate = calendarView.getDelegate().getSelectDate();
                    String monthStr = DateUtil.getMonthStr(selectDate);
                    List<HealthBean> list = new ArrayList<>();
                    HealthBean healthBean1 = new HealthBean();
                    healthBean1.setStatus(DateStatus.HealthStatus.ISSUE);
                    healthBean1.setCheckDate(monthStr + "-10");
                    list.add(healthBean1);

                    HealthBean healthBean2 = new HealthBean();
                    healthBean2.setStatus(DateStatus.HealthStatus.NORMAL);
                    healthBean2.setCheckDate(monthStr + "-11");
                    list.add(healthBean2);

                    HealthBean healthBean3 = new HealthBean();
                    healthBean3.setStatus(DateStatus.HealthStatus.ISSUE);
                    healthBean3.setCheckDate(monthStr + "-" + calendarView.getDelegate().getCurrentDay());
                    list.add(healthBean3);

                    healthDateView.pushData(monthStr, list);
                    calendarView.visibleCanlendar(calendarView.isVisibleMenu() ? false : true);
                }
                break;
        }
    }

}
