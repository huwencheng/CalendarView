package com.szy.szycalendar.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.szy.szycalendar.R;
import com.szy.szycalendar.annotation.DateAnnotation;
import com.szy.szycalendar.annotation.DateStatus;
import com.szy.szycalendar.bean.HealthBean;
import com.szy.szycalendar.date.base.BaseDateView;
import com.szy.szycalendar.utils.DateUtil;
import com.szy.szycalendar.utils.LocalDisplay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日常三检日历-日期控件
 *
 * @author huwencheng
 * @date 2018/11/27 20:02
 */
public class HealthDateView extends BaseDateView {

    private final String TAG = HealthDateView.class.getSimpleName();
    private Paint healthPaint;//三检画笔
    private Paint dotPaint;//圆点画笔
    private int spaceHeight;//圆心到点的距离
    private int dotRadius;//点的半径
    private List<HealthBean> listHealth = new ArrayList<>();//需要查阅的月份对应的数据

    public HealthDateView(Context context) {
        super(context);
        healthPaint = new Paint();
        dotPaint = new Paint();
        healthPaint.setAntiAlias(true);//抗锯齿
        dotPaint.setAntiAlias(true);//抗锯齿

        spaceHeight = LocalDisplay.designedDP2px(8);
        dotRadius = LocalDisplay.designedDP2px(2);
    }

    @Override
    protected int fillMeasureHeight() {
        return LocalDisplay.designedDP2px(40f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float height = lineNum * dayHeight + tailHeight;
        healthPaint.setColor(Color.parseColor("#eeeeee"));
        canvas.drawRect(0, height, LocalDisplay.deviceWidth(getContext()), height + LocalDisplay.dip2px(getContext(), 0.5f), healthPaint);

        healthPaint.setTextAlign(Paint.Align.CENTER);
        healthPaint.setTextSize(LocalDisplay.sp2px(getContext(), delegate.getTextSizeHealth()));
        healthPaint.setColor(getContext().getResources().getColor(R.color.health_issue));
        canvas.drawText("● 异常", columnWidth * 1.8f, height + LocalDisplay.designedDP2px(25f), healthPaint);
        healthPaint.setColor(getContext().getResources().getColor(R.color.health_normal));
        canvas.drawText("● 正常", columnWidth * 3.5f, height + LocalDisplay.designedDP2px(25f), healthPaint);
        healthPaint.setColor(getContext().getResources().getColor(R.color.health_uncheck));
        canvas.drawText("● 未体检", columnWidth * 5.2f, height + LocalDisplay.designedDP2px(25f), healthPaint);
    }

    @Override
    protected int getSelectTextColor(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthSelectTextColor(day);
    }

    @Override
    protected int getTextColorWeekDay(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthTextColorWeekDay(day);
    }

    @Override
    protected int getTextColorDay(int day) {
        return listHealth == null || listHealth.size() == 0 ? delegate.getTextColorWeekDay() : getHealthTextColorDay(day);
    }

    @Override
    protected boolean canTouch(int day) {
        return listHealth == null || listHealth.size() == 0 ? false : isCanTounch(day);
    }

    @Override
    protected boolean drawSelectBg() {
        return listHealth == null || listHealth.size() == 0 ? false : isDrawSelectBg();
    }

    @Override
    protected void drawOther(Canvas canvas, Rect rect, int baseLineY, int day) {
        int status = matchStatus(day);
        if (status == DateStatus.HealthStatus.ISSUE) {
            dotPaint.setColor(getContext().getResources().getColor(R.color.health_issue));
            canvas.drawCircle(rect.centerX(), baseLineY + spaceHeight, dotRadius, dotPaint);
        }
    }

    /**
     * 需要查阅的月份数据
     *
     * @param checkMonth 需要查阅的月份 格式yyyy-MM
     * @param list       需要查阅的月份对应的数据
     */
    public void pushData(String checkMonth, List<HealthBean> list) {
        Date time = DateUtil.str2DateYM(checkMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int currentYear = delegate.getCurrentPageYear();
        int currentMonth = delegate.getCurrentPageMonth();

        if (year == currentYear && month == currentMonth) {
            listHealth.clear();
            listHealth.addAll(list);
        }

        invalidate();
    }

    private
    @DateAnnotation.HealthStatus
    int matchStatus(int day) {
        if (listHealth != null && listHealth.size() > 0) {
            for (HealthBean item : listHealth) {
                String checkDate = item.getCheckDate();
                Date time = DateUtil.str2DateYMD(checkDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int healthDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (healthDay == day) {
                    return item.getStatus();
                }
            }
        }
        return DateStatus.HealthStatus.UNCHECK;
    }

    private int getHealthSelectTextColor(int day) {
        int status = matchStatus(day);
        if (status == DateStatus.HealthStatus.NORMAL) {
            return getContext().getResources().getColor(R.color.health_normal);
        } else if (status == DateStatus.HealthStatus.ISSUE) {
            return Color.WHITE;
        } else {
            return getContext().getResources().getColor(R.color.health_uncheck);
        }
    }

    private int getHealthTextColorWeekDay(int day) {
        int status = matchStatus(day);
        if (status == DateStatus.HealthStatus.NORMAL) {
            return getContext().getResources().getColor(R.color.health_normal);
        } else if (status == DateStatus.HealthStatus.ISSUE) {
            return getContext().getResources().getColor(R.color.health_issue);
        } else {
            return getContext().getResources().getColor(R.color.health_uncheck);
        }
    }

    private int getHealthTextColorDay(int day) {
        int status = matchStatus(day);
        if (status == DateStatus.HealthStatus.NORMAL) {
            return getContext().getResources().getColor(R.color.health_normal);
        } else if (status == DateStatus.HealthStatus.ISSUE) {
            return getContext().getResources().getColor(R.color.health_issue);
        } else {
            return getContext().getResources().getColor(R.color.health_uncheck);
        }
    }

    private boolean isDrawSelectBg() {
        if (listHealth != null && listHealth.size() > 0) {
            for (HealthBean item : listHealth) {
                String checkDate = item.getCheckDate();
                Date time = DateUtil.str2DateYMD(checkDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int healthDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (healthDay == delegate.getSelectDay()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCanTounch(int day) {
        if (listHealth != null && listHealth.size() > 0) {
            for (HealthBean item : listHealth) {
                String checkDate = item.getCheckDate();
                Date time = DateUtil.str2DateYMD(checkDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                int healthDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (healthDay == day) {
                    int status = item.getStatus();
                    if (status == DateStatus.HealthStatus.NORMAL || status == DateStatus.HealthStatus.ISSUE) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

}
