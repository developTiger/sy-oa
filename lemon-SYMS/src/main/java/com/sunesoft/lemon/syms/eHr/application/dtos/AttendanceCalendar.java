package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * fghf
 * Created by xiazl on 2017/1/18.
 */
public class AttendanceCalendar {
    private Long id;
    /**
     * 考勤描述
     */
    private String descript;
    /**
     * 日期
     */
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
