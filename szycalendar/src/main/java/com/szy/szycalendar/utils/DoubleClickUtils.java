package com.szy.szycalendar.utils;

/**
 * 控制点击，避免瞬间点击两下
 */
public class DoubleClickUtils {
    private int mLimitTime = 500;
    private long mPreClickTime = 0;

    private static DoubleClickUtils self;

    public static DoubleClickUtils getInstance() {
        if (self == null) {
            synchronized (DoubleClickUtils.class) {
                if (self == null) {
                    self = new DoubleClickUtils();
                }
            }
        }
        return self;
    }

    /**
     * 设置有效点击的时间间隔
     */
    public void setLimitTime(int limitTime) {
        this.mLimitTime = limitTime;
    }

    /**
     * 是否是无效的点击事件(瞬间点击多下)
     */
    public boolean isInvalidClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mPreClickTime < mLimitTime) {
            return true;
        }
        mPreClickTime = currentTimeMillis;
        return false;
    }
}
