package com.szy.szycalendar.bean;

import com.szy.szycalendar.annotation.DateAnnotation;

/**
 * @author huwencheng
 * @date 2018/12/5 14:00
 */
public class HealthBean {

    private int status;//状态
    private String checkDate;//日期 格式:yyyy-MM-dd

    public @DateAnnotation.HealthStatus int getStatus() {
        return status;
    }

    public void setStatus(@DateAnnotation.HealthStatus int status) {
        this.status = status;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

}
