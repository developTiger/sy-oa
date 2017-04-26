package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2016/11/7.
 */
@Entity
@Table(name = "syy_oa_hr_date_detail")
public class DateDetail extends BaseEntity {

    /**
     * 时间 (统计具体时间)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "r_date")
    private Date rDate;

    /**
     * 节假日事件 节假日类型 （关联节假日）
     */
    @Column(name = "holiday_type")
    private String holidayType;

    /**
     * 节假日事件 节假日描述
     */
    @Column(name = "description")
    private String description;

    public DateDetail() {
        this.setLastUpdateTime(new Date());
        this.setCreateDateTime(new Date());
        this.setIsActive(true);

    }

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
