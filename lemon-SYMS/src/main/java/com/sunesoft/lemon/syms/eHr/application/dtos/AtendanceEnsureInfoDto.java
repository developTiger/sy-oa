package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by zhouz on 2016/8/11.
 */
public class AtendanceEnsureInfoDto {


    private Long id;
    /**
     * 部门编号
     */
    private Long depId; //部门编号

    /**
     * 打卡日期(yyyy-MM-dd)
     */
    private Date attDate;

    /**
     * 确认时间(yyyy-MM-dd)
     */
    private Date sureDate;


    /**
     * 是否确认：false否，ture为是
     */
    private Boolean isSure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public Date getSureDate() {
        return sureDate;
    }

    public void setSureDate(Date sureDate) {
        this.sureDate = sureDate;
    }

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
    }
}
