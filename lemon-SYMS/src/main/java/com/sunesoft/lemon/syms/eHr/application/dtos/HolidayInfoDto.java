package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by xiazl on 2016/6/30.
 */
public class HolidayInfoDto {

    /**
     * 状态（休息，上班，加班）
     */
    private String holidayState;

    private Long id;

    /**
     * 节假日名称
     */
    private String hname;

    /**
     * 节假日天数
     */
    private Float amount;

    /**
     * 节假日月份
     */
    private String hmonth;

    /**
     * 创建时间
     */
    private Date createDateTime;

    /**
     * 上次修改时间
     */
    private Date lastUpdateTime;

    public String getHolidayState() {
        return holidayState;
    }

    public void setHolidayState(String holidayState) {
        this.holidayState = holidayState;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getHmonth() {
        return hmonth;
    }

    public void setHmonth(String hmonth) {
        this.hmonth = hmonth;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
