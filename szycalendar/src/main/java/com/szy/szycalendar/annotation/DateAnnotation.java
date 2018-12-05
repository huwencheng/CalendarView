package com.szy.szycalendar.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huwencheng
 * @date 2018/12/5 14:17
 */
public class DateAnnotation {

    @IntDef({DateStatus.HealthStatus.ISSUE, DateStatus.HealthStatus.NORMAL, DateStatus.HealthStatus.UNCHECK})
    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HealthStatus {
    }

}
