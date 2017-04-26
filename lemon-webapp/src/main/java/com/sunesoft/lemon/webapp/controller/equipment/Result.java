package com.sunesoft.lemon.webapp.controller.equipment;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
public class Result {

    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String num;
    /**
     * 检定/校准时间
     */
    private Date verification;
    /**
     * 有效期
     */
    private Date periodDate;
    /**
     * 证实人
     */
    private String confirmer;
    /**
     * 证实时间
     */
    private Date verifyDate;


    /**
     * 设备信息
     */
    private String equipments;

    /**
     * 设备维修或保养
     */
    private String equipmentStatuses;

    /**
     * 设备运行
     */
    private String equipmentMaintenances;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getVerification() {
        return verification;
    }

    public void setVerification(Date verification) {
        this.verification = verification;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public String getEquipmentStatuses() {
        return equipmentStatuses;
    }

    public void setEquipmentStatuses(String equipmentStatuses) {
        this.equipmentStatuses = equipmentStatuses;
    }

    public String getEquipmentMaintenances() {
        return equipmentMaintenances;
    }

    public void setEquipmentMaintenances(String equipmentMaintenances) {
        this.equipmentMaintenances = equipmentMaintenances;
    }
}
