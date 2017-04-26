package com.sunesoft.lemon.ay.equipment.application.dtos;

import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
public class EquipmentResultDto {
    /**
     * 记录id
     */
    private Long resId;

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
    private List<Equipment> equipments;

    /**
     * 设备维修或保养
     */
    private List<EquipmentStatus> equipmentStatuses;

    /**
     * 设备运行
     */
    private List<EquipmentMaintenance> equipmentMaintenances;



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

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<EquipmentStatus> getEquipmentStatuses() {
        return equipmentStatuses;
    }

    public void setEquipmentStatuses(List<EquipmentStatus> equipmentStatuses) {
        this.equipmentStatuses = equipmentStatuses;
    }

    public List<EquipmentMaintenance> getEquipmentMaintenances() {
        return equipmentMaintenances;
    }

    public void setEquipmentMaintenances(List<EquipmentMaintenance> equipmentMaintenances) {
        this.equipmentMaintenances = equipmentMaintenances;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }
}
