package com.szy.szycalendar.annotation;

/**
 * @author huwencheng
 * @date 2018/12/5 14:11
 */
public interface DateStatus {

    interface HealthStatus {
        int NORMAL = 1;//正常
        int ISSUE = 2;//异常
        int UNCHECK = 3;//未检查
    }

}
