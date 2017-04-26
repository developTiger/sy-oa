package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by xiazl on 2016/6/15.
 */
public class TechPositionDto {
    /**
     * id of emp
     */
    private Long empId;

    private Long id;
    /**
     * 是否是当前职务
     */
    private Boolean isCurrent;//是否是当前职务
    /**
     * 评聘时间
     */
    private String strInTime;
    /**
     * 解聘时间
     */
    private String strOutTime;
    /**
     * 已经改为 技术职务名称
     */
    private String major;
    /**
     * 资格名称
     */
    private String name;//资格名称
    /**
     * 资格级别:高，中，初
     */
    private String level;//资格级别:高，中，初
//    /**
//     * 获取时间
//     */
//    private String strInObtian;//获取时间
    /**
     * 获取途径
     */
    private String way;//获取途径
    /**
     * 专业技术职务信息介绍
     */
    private String brief;//专业技术职务信息介绍

    public TechPositionDto() {
        this.setIsCurrent(true);
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }



    public String getStrInTime() {
        return strInTime;
    }

    public void setStrInTime(String strInTime) {
        this.strInTime = strInTime;
    }

    public String getStrOutTime() {
        return strOutTime;
    }

    public void setStrOutTime(String strOutTime) {
        this.strOutTime = strOutTime;
    }


}
