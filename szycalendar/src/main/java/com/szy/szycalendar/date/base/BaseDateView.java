package com.szy.szycalendar.date.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.szy.szycalendar.CalendarView;
import com.szy.szycalendar.R;
import com.szy.szycalendar.common.Delegate;
import com.szy.szycalendar.inner.CalendarClickListener;
import com.szy.szycalendar.month.base.BaseMonthBar;
import com.szy.szycalendar.utils.DateUtil;
import com.szy.szycalendar.utils.DisplayUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期栏
 *
 * @author huwencheng
 * @date 2018/10/30 20:18
 */
public abstract class BaseDateView extends View {

    private final String TAG = BaseDateView.class.getSimpleName();

    private Paint circlePaint;//圆形画笔
    private Paint textPaint;//文字画笔
    private Paint recPaint;//矩形画笔

    private int dayOfMonth;//月份天数
    private int firstIndex;//当月第一天位置索引
    protected int lineNum;//日期行数
    private int firstLineNum, lastLineNum; //第一行、最后一行能展示多少日期
    protected int columnWidth;//每列宽度
    protected int dayHeight;//每行高度
    protected int tailHeight;//末尾高度
    protected Delegate delegate;
    private PointF focusPoint = new PointF();//焦点坐标
    protected CalendarClickListener listener;
    private CalendarView calendarView;
    private BaseMonthBar baseMonthBar;

    public BaseDateView(Context context) {
        this(context, null);
    }

    public BaseDateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompute();
    }

    private void initCompute() {
        dayHeight = DisplayUtil.designedDP2px(45);
        tailHeight = DisplayUtil.designedDP2px(12);

        textPaint = new Paint();
        circlePaint = new Paint();
        recPaint = new Paint();
        textPaint.setAntiAlias(true);//抗锯齿
        circlePaint.setAntiAlias(true);//抗锯齿
    }

    public void setup(CalendarView calendarView, BaseMonthBar baseMonthBar, Delegate delegate) {
        this.calendarView = calendarView;
        this.baseMonthBar = baseMonthBar;
        this.delegate = delegate;
        setBackgroundColor(Color.WHITE);
        recPaint.setColor(Color.TRANSPARENT);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //TODO textPaint.setTextsize()和textview.setTextsize()区别 ==> https://blog.csdn.net/chenhuakang/article/details/53323034
        textPaint.setTextSize(DisplayUtil.sp2px(getContext(), delegate.getTextSizeDay()));

        //默认当前日期为选中的日期
        setCurrentPageDate(delegate.getSelectDate());
    }

    public void setListener(CalendarClickListener listener) {
        this.listener = listener;
    }

    /**
     * 月份增减
     */
    public void monthChange(Date date) {
        setCurrentPageDate(date);
        invalidate();
    }

    /**
     * 日期增减
     */
    public void dayChange(Date date) {
        setCurrentPageDate(date);
        invalidate();
    }

    /**
     * 设置日期
     */
    private void setCurrentPageDate(Date date) {
        //设置日期格式为yyyy-MM(以便获取正确的第一行1号显示的初始位置)
        Date time = DateUtil.str2DateYM(DateUtil.getMonthStr(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //第一行1号显示在什么位置（星期几）
        firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        lineNum = 1;
        //第一行能展示的天数
        firstLineNum = 7 - firstIndex;
        //最后一行能展示的天数
        lastLineNum = 0;
        int shengyu = dayOfMonth - firstLineNum;
        while (shengyu > 7) {
            lineNum++;
            shengyu -= 7;
        }
        if (shengyu > 0) {
            lineNum++;
            lastLineNum = shengyu;
        }

        Log.i(TAG, DateUtil.getMonthStr(delegate.getCurrentPageDate()) + "一共有" + dayOfMonth + "天,第一天的索引是：" + firstIndex + "   有" + lineNum +
                "行，第一行" + firstLineNum + "个，最后一行" + lastLineNum + "个");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        columnWidth = widthSize / 7;
        //总高度 = 日期行数*每行高度 + 尾部高度
        //总高度 = 日期行数*每行高度 + 尾部高度
        int allHeight = lineNum * dayHeight + tailHeight;
        Log.v(TAG, " 每行高度：" + dayHeight + " 行数：" + lineNum + "  \n控件高度：" + allHeight);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), (int) allHeight + fillMeasureHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDate(canvas);
    }

    /**
     * 绘制日期
     */
    private void drawDate(Canvas canvas) {
        //某行开始绘制的Y坐标
        int top = 0;
        //行
        for (int line = 0; line < lineNum; line++) {
            if (line == 0) {
                //第一行
                drawDate(canvas, top, firstLineNum, 0, firstIndex);
            } else if (line == lineNum - 1) {
                //最后一行
                top += dayHeight;
                drawDate(canvas, top, lastLineNum, firstLineNum + (line - 1) * 7, 0);
            } else {
                //满行
                top += dayHeight;
                drawDate(canvas, top, 7, firstLineNum + (line - 1) * 7, 0);
            }
        }
    }

    /**
     * 绘制某一行的日期
     *
     * @param canvas
     * @param top        顶部坐标
     * @param count      此行需要绘制的日期数量（不一定都是7天）
     * @param overDay    已经绘制过的日期，从overDay+1开始绘制
     * @param startIndex 此行第一个日期的星期索引
     */
    private void drawDate(Canvas canvas, int top, int count, int overDay, int startIndex) {
        Log.d(TAG, "总共" + dayOfMonth + "天  有" + lineNum + "行" + "  已经画了" + overDay + "天,下面绘制：" + count + "天");
        for (int i = 0; i < count; i++) {

            int left = (startIndex + i) * columnWidth;
            int day = (overDay + i + 1);

            //画一个矩形
            Rect rect = new Rect(left, top, left + columnWidth, top + dayHeight);
            canvas.drawRect(rect, recPaint);

            if (DateUtil.isWeekend(delegate.getCurrentPageYear(), delegate.getCurrentPageMonth(), day)) {
                this.textPaint.setColor(getTextColorWeekDay(day));
            } else {
                this.textPaint.setColor(getTextColorDay(day));
            }

            if (delegate.getSelectYear() == delegate.getCurrentPageYear()
                    && delegate.getSelectMonth() == delegate.getCurrentPageMonth()
                    && delegate.getSelectDay() == day) {
                this.textPaint.setColor(getSelectTextColor(day));
                this.circlePaint.setColor(delegate.getSelectBg());
                if (drawSelectBg()) {
                    canvas.drawCircle(left + columnWidth / 2, top + dayHeight / 2, delegate.getSelectRadius(), circlePaint);
                }
            }

            //TODO 文字垂直方向不居中显示问题处理 ==> https://www.jianshu.com/p/71cfab079c64
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float basTop = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float baseBottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
            int baseLineY = (int) (rect.centerY() - basTop / 2 - baseBottom / 2);//基线中间点的y轴计算公式
            if (delegate.getCurrentPageYear() == delegate.getCurrentYear()
                    && delegate.getCurrentPageMonth() == delegate.getCurrentMonth()
                    && delegate.getCurrentDay() == day) {
                canvas.drawText("今", rect.centerX(), baseLineY, this.textPaint);
            } else {
                canvas.drawText(day + "", rect.centerX(), baseLineY, this.textPaint);
            }

            drawOther(canvas, rect, baseLineY, day);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_MOVE:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                focusPoint.set(event.getX(), event.getY());
                touchFocusMove(focusPoint, true);
                break;
        }
        return true;
    }

    @Override
    public void invalidate() {
        requestLayout();//避免重绘的时候没去调用onMeasure()导致高度没法重绘
        super.invalidate();
    }

    /**
     * 焦点滑动
     *
     * @param point
     * @param eventEnd
     */
    private void touchFocusMove(final PointF point, boolean eventEnd) {
        Log.e(TAG, "点击坐标：(" + point.x + " ，" + point.y + "),事件是否结束：" + eventEnd);
        /**日期部分按下和滑动时重绘，只有在事件结束后才响应*/
        touchDay(point, eventEnd);
    }

    /**
     * 事件点在 日期区域 范围内
     *
     * @param point
     * @param eventEnd
     */
    private void touchDay(final PointF point, boolean eventEnd) {
        //根据Y坐标找到焦点行
        boolean availability = false;  //事件是否有效
        //日期部分
        float top = dayHeight;
        int foucsLine = 1;
        while (foucsLine <= lineNum) {
            if (top >= point.y) {
                availability = true;
                break;
            }
            top += dayHeight;
            foucsLine++;
        }
        if (availability) {
            //根据X坐标找到具体的焦点日期
            int xIndex = (int) point.x / columnWidth;
            if ((point.x / columnWidth - xIndex) > 0) {
                xIndex += 1;
            }
            Log.e(TAG, "列宽：" + columnWidth + "  x坐标余数：" + (point.x / columnWidth));
            if (xIndex <= 0) {
                xIndex = 1;   //避免调到上一行最后一个日期
            }
            if (xIndex > 7) {
                xIndex = 7;   //避免调到下一行第一个日期
            }
            Log.e(TAG, "事件在日期部分，第" + foucsLine + "/" + lineNum + "行, " + xIndex + "列");
            if (foucsLine == 1) {
                //第一行
                if (xIndex <= firstIndex) {
                    Log.e(TAG, "点到开始空位了");
                    setSelectedDay(delegate.getSelectDay(), true);
                } else {
                    setSelectedDay(xIndex - firstIndex, eventEnd);
                }
            } else if (foucsLine == lineNum) {
                //最后一行
                if (xIndex > lastLineNum) {
                    Log.e(TAG, "点到结尾空位了");
                    setSelectedDay(delegate.getSelectDay(), true);
                } else {
                    setSelectedDay(firstLineNum + (foucsLine - 2) * 7 + xIndex, eventEnd);
                }
            } else {
                setSelectedDay(firstLineNum + (foucsLine - 2) * 7 + xIndex, eventEnd);
            }
        } else {
            //超出日期区域后，视为事件结束，响应最后一个选择日期的回调
            setSelectedDay(delegate.getSelectDay(), true);
        }
    }

    /**
     * 设置选中的日期
     *
     * @param day
     * @param eventEnd
     */
    private void setSelectedDay(int day, boolean eventEnd) {
        if (canTouch(day)) {
            Log.w(TAG, "选中：" + day + "  事件是否结束" + eventEnd);
            if (delegate != null && listener != null) {
                updateSelectDay(day, eventEnd);
            }
        }
    }

    protected void updateSelectDay(int day, boolean eventEnd){
        updateSelectInfo(day, eventEnd);
        listener.onDayClick(delegate.getSelectDate());
    }

    protected void updateSelectInfo(int day, boolean eventEnd) {
        updateSelectAndCurrentPageTime(delegate.getCurrentPageYear(), delegate.getCurrentPageMonth(), day);
        invalidate();
        if (calendarView != null && baseMonthBar != null && eventEnd) {
            delegate.setLastSelectYear(delegate.getSelectYear());
            delegate.setLastSelectMonth(delegate.getSelectMonth());
            delegate.setLastSelectDay(delegate.getSelectDay());
            calendarView.visibleCanlendar(false);
            if (delegate.isCalendarExEnable()) {
                baseMonthBar.updateTitle(DateUtil.getMonthStr(delegate.getSelectYear(), delegate.getSelectMonth()));
            } else {
                baseMonthBar.updateTitle(DateUtil.getDayStr(delegate.getSelectYear(), delegate.getSelectMonth(), delegate.getSelectDay()));
            }
        }
    }

    private void updateSelectAndCurrentPageTime(int year, int month, int day) {
        delegate.setSelectYear(delegate.getCurrentPageYear());
        delegate.setSelectMonth(delegate.getCurrentPageMonth());
        delegate.setSelectDay(day);
        delegate.setSelectDate(DateUtil.getDate(year, month, day));

        delegate.setCurrentPageYear(year);
        delegate.setCurrentPageMonth(month);
        delegate.setCurrentPageDay(day);
        delegate.setCurrentPageDate(DateUtil.getDate(year, month, day));
    }

    /**
     * 上一次选中的时间是否和当前选中的时间一致
     */
    private boolean isCurrentPageSelect() {
        if (delegate.getLastSelectYear() == delegate.getSelectYear()
                && delegate.getLastSelectMonth() == delegate.getSelectMonth()
                && delegate.getLastSelectDay() == delegate.getSelectDay()) {
            return true;
        }
        return false;
    }

    /**
     * 设置日期周末字体颜色
     *
     * @param day
     * @return
     */
    protected int getTextColorWeekDay(int day) {
        return delegate.getTextColorWeekDay();
    }

    /**
     * 设置日期字体颜色
     *
     * @param day
     * @return
     */
    protected int getTextColorDay(int day) {
        return delegate.getTextColorDay();
    }

    /**
     * 设置选中的文本颜色
     *
     * @param day
     * @return
     */
    protected int getSelectTextColor(int day) {
        return delegate.getSelectTextColor();
    }

    /**
     * 是否支持点击
     *
     * @return
     */
    protected boolean canTouch(int day) {
        return true;
    }

    /**
     * 是否需要绘制选中的背景
     *
     * @return
     */
    protected boolean drawSelectBg() {
        return true;
    }

    /**
     * 填充布局高度
     *
     * @return
     */
    protected int fillMeasureHeight() {
        return 0;
    }

    /**
     * 绘制其他内容
     *
     * @param canvas
     * @param rect
     * @param baseLineY
     * @param day
     */
    protected void drawOther(Canvas canvas, Rect rect, int baseLineY, int day) {
    }

}
