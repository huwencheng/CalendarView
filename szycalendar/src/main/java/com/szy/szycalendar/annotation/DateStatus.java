package com.szy.szycalendar.annotation;

/**
 * @author huwencheng
 * @date 2018/12/5 14:11
 */
public interface DateStatus {

    interface HealthStatus {
        int NORMAL = 0;//正常
        int ISSUE = 1;//异常
        int UNCHECK = 2;//未检查
    }

}
