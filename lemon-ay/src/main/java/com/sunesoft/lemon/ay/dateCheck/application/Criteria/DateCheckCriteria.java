package com.sunesoft.lemon.ay.dateCheck.application.Criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;

import java.util.Date;

/**
 * Created by xiazl on 2016/10/21.
 */
public class DateCheckCriteria extends PagedCriteria {
    /**
     * 仪器名称
     */
    private String equipName;
    /**
     * 仪器id
     */
    private Long id;


    /**
     * 核查时间开始
     */
    private Date beginTime;
    /**
     * 核查时间结束
     */
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        if (StringUtils.isNullOrWhiteSpace(beginTime))
            this.beginTime = null;
        else
            this.beginTime = DateHelper.parse(beginTime, "yyyy-MM-dd");
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isNullOrWhiteSpace(endTime))
            this.endTime = null;
        else
            this.endTime = DateHelper.parse(endTime, "yyyy-MM-dd");
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
