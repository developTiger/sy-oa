package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by xiazl on 2016/6/15.
 */
public class WorkExperienceDto {


    private Long empId;

    private Long id;
    /**
     * 是否是当前工作：false不是，true代表是
     */

    private Boolean isCurrent;//是否是当前工作：false不是，true代表是
    /**
     * 已经改为 聘用时间
     */
    private String StrStrartTime;
    /**
     * 已经改为 解聘时间
     */
    private String strOverTime;
    /**
     * 工作单位
     */
    private String company; //就职单位
    /**
     * 已经改为 职务
     */
    private String workName;
    /**
     * 工作地点
     */
    private String workPlace;//工作地点
    /**
     * 工作简介
     */
    private String breif;//工作简介

    public WorkExperienceDto() {
        this.setIsCurrent(true);
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getBreif() {
        return breif;
    }

    public void setBreif(String breif) {
        this.breif = breif;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getStrOverTime() {
        return strOverTime;
    }

    public void setStrOverTime(String strOverTime) {
        this.strOverTime = strOverTime;
    }

    public String getStrStrartTime() {
        return StrStrartTime;
    }

    public void setStrStrartTime(String strStrartTime) {
        StrStrartTime = strStrartTime;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
}
