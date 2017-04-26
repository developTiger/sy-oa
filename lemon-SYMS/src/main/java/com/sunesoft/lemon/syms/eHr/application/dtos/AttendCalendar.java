package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * fghf
 * Created by xiazl on 2017/1/18.
 */
public class AttendCalendar {

    private Long aid;
    /**
     * 考勤描述
     */
    private String descript;
    /**
     * 日期
     */
    private Date attDate;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }
}
