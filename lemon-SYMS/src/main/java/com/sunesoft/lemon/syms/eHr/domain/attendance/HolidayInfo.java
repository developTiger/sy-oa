package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xiazl on 2016/6/30.
 */
@Entity
@Table(name="syy_oa_hr_holiday_info")
public class HolidayInfo extends BaseEntity {

    /**
     * 节假日类型
     * 上班，休息，加班
     */
    @Column(name = "holiday_state")
    private String holidayState;

    /**
     * 节假日名称
     */
    @Column(name = "h_name")
    private String hname;

    /**
     * 节假日天数(弃用)
     */
    private Float amount;

    /**
     * 节假日月份
     */
    @Column(name = "h_month")
    private String hmonth;

    

    public HolidayInfo() {
        this.setIsActive(true);
        this.setLastUpdateTime(new Date());
        this.setCreateDateTime(new Date());
        this.setAmount(0f);
    }

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
}
