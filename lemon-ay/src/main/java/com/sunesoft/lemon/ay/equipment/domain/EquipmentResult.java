package com.sunesoft.lemon.ay.equipment.domain;


/**
 * Created by jiangkefan on 2016/8/6.
 */

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Education;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 检定/校准结果证实记录
 */
@Entity
@Table(name="syy_oa_ay_equipmentresult")
public class EquipmentResult extends BaseEntity{

    public EquipmentResult() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }


    /**
     * 名称
     */
    @Column(name ="name")
    private String name;
    /**
     * 编号
     */
    @Column(name="num")
    private String num;
    /**
     * 检定/校准时间
     */
    @Column(name="verification")
    private Date verification;
    /**
     * 有效期
     */
    @Column(name="period_date")
    private Date periodDate;
    /**
     * 证实人
     */
    @Column(name="confirmer")
    private String confirmer;
    /**
     * 证实时间
     */
    @Column(name="verify_date")
    private Date verifyDate;

    /**
     * 记录设备
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "res_id")
    private List<Equipment> equipments;

    /**
     * 记录设备维修或保养
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "res_id")
    private List<EquipmentStatus> equipmentStatuses;

    /**
     * 记录设备运行
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "res_id")
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

    public List<EquipmentMaintenance> getEquipmentMaintenances() {
        return equipmentMaintenances;
    }

    public void setEquipmentMaintenances(List<EquipmentMaintenance> equipmentMaintenances) {
        this.equipmentMaintenances = equipmentMaintenances;
    }

    public List<EquipmentStatus> getEquipmentStatuses() {
        return equipmentStatuses;
    }

    public void setEquipmentStatuses(List<EquipmentStatus> equipmentStatuses) {
        this.equipmentStatuses = equipmentStatuses;
    }
}
