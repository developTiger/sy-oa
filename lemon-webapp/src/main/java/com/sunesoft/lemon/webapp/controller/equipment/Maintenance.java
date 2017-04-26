package com.sunesoft.lemon.webapp.controller.equipment;

import java.util.Date;

/**
 * Created by admin on 2016/8/15.
 */
public class Maintenance {

    /**
     * 维修或者保养
     * 1.维修，2保养
     */
    private String maintenanceOrrepair;

    /**
     * 名称
     */
    private String name;

    /**
     * 上次保养时间
     */
    private Date formMaintenanceDate;
    /**
     * 保养费
     */
    private String cost;
    /**
     * 保养项目
     */
    private String project;
    /**
     * 设备保养人
     */
    private String maintenancePerson;
    /**
     * 保养描述
     */
    private String maintenanceDescription;
    /**
     * 保养结束时间
     */
    private Date endDate;

    /**
     * 申请日期
     */
    private Date createDateTime;

    public String getMaintenanceOrrepair() {
        return maintenanceOrrepair;
    }

    public void setMaintenanceOrrepair(String maintenanceOrrepair) {
        this.maintenanceOrrepair = maintenanceOrrepair;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFormMaintenanceDate() {
        return formMaintenanceDate;
    }

    public void setFormMaintenanceDate(Date formMaintenanceDate) {
        this.formMaintenanceDate = formMaintenanceDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMaintenancePerson() {
        return maintenancePerson;
    }

    public void setMaintenancePerson(String maintenancePerson) {
        this.maintenancePerson = maintenancePerson;
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }

    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
