package com.szy.szycalendar.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author huwencheng
 * @date 2018/10/30 16:57
 */
public class DisplayUtil {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    private static boolean sInitialed;

    public DisplayUtil() {
    }

    public static void init(Context context) {
        if(!sInitialed && context != null) {
            sInitialed = true;
            SCREEN_WIDTH_PIXELS = deviceWidth(context);
            SCREEN_HEIGHT_PIXELS = deviceWidth(context);
            SCREEN_DENSITY = deviceDensity(context);
            SCREEN_WIDTH_DP = (int)((float)SCREEN_WIDTH_PIXELS / SCREEN_DENSITY);
            SCREEN_HEIGHT_DP = (int)((float)SCREEN_HEIGHT_PIXELS / SCREEN_DENSITY);
        }
    }

    public static int dp2px(float dp) {
        float scale = SCREEN_DENSITY;
        return (int)(dp * scale + 0.5F);
    }

    public static int designedDP2px(float designedDp) {
        if(SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * (float)SCREEN_WIDTH_DP / 320.0F;
        }

        return dp2px(designedDp);
    }

    public static void setPadding(View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }

    public static int deviceWidth(Context context) {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0;
        }
    }

    public static int deviceHeight(Context context) {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0;
        }
    }

    public static float deviceDensity(Context context) {
        try {
            return context.getResources().getDisplayMetrics().density;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0.0F;
        }
    }

    public static int getScreenWidthDp(Context context) {
        try {
            return (int)((float)deviceWidth(context) / deviceDensity(context));
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        try {
            return (int)(dpValue * deviceDensity(context) + 0.5F);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public static int px2dip(Context context, float pxValue) {
        try {
            return (int)(pxValue / deviceDensity(context) + 0.5F);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public static int px2sp(Context context, float pxValue) {
        try {
            float e = context.getResources().getDisplayMetrics().scaledDensity;
            return (int)(pxValue / e + 0.5F);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public static int sp2px(Context context, float spValue) {
        try {
            float e = context.getResources().getDisplayMetrics().scaledDensity;
            return (int)(spValue * e + 0.5F);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public static Point getScreenMetrics(Context context) {
        return new Point(deviceWidth(context), deviceHeight(context));
    }

    public static int getStatusBarHeight(Context context) {
        try {
            int e = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if(resourceId > 0) {
                e = context.getResources().getDimensionPixelSize(resourceId);
            }

            return e;
        } catch (Resources.NotFoundException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / 160.0F);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / 160.0F);
        return dp;
    }

    public static void setMargins(View view, float left, float top, float right, float bottom) {
        if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            p.setMargins(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
            view.requestLayout();
        }

    }

}
