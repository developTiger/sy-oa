package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.RuleType;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/30.
 */
public class RuleDateDto {

    private Long id;

    /**
     * 时间
     */
    private Date rDate;

    /**
     * 类型
     */
    private RuleType ruleType;

    /**
     * 假日信息
     */
    private Long infoId;



    /**
     * 节假日事件 id
     */
    private String holidayEventId;

    /**
     * 节假日事件 节假日类型 （关联节假日）
     */
    private String holidayType;

    /**
     * 节假日事件 节假日描述
     */
    private String description;

    /**
     * 节假日事件 开始时间
     */
    private Date startTime;

    /**
     * 节假日时间 结束时间
     */
    private Date endTime;

    /**
     * 节假日详细 关联id
     */
    private List<DateDetail> dateDetail;

    public List<DateDetail> getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(List<DateDetail> dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getHolidayEventId() {
        return holidayEventId;
    }

    public void setHolidayEventId(String holidayEventId) {
        this.holidayEventId = holidayEventId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    private String intoName;

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getIntoName() {
        return intoName;
    }

    public void setIntoName(String intoName) {
        this.intoName = intoName;
    }


}
