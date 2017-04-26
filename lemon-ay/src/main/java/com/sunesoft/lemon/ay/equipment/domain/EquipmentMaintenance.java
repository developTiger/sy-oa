package com.sunesoft.lemon.ay.equipment.domain;

/**
 * Created by jiangkefan on 2016/8/5.
 */

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备维护保养
 */
@Entity
@Table(name="syy_oa_ay_equi_maint")
public class EquipmentMaintenance  extends BaseEntity {

    public EquipmentMaintenance() {
        this.setCreateDateTime(new Date());//即维修申请日期
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }



    /**
     * 设备编号
     */
    @Column(name="res_num")
    private String resNum;

    /**
     * 名称
     */
    @Column(name="name")
    private String name;

    /**
     * 规格
     */
    @Column(name="e_standard")
    private String standard;

    /**
     * 制造厂名称（生产厂家）
     */
    @Column(name="e_factory")
    private String factory;

    /**
     * 出厂标号（编号）
     */
    @Column(name="outFactory_num")
    private String outFactoryNum;

    /**
     * 原值
     */
    @Column(name="original")
    private String original;


    /**
     * 申请单位
     */
    @Column(name="apply_unit")
    private String applyUnit;

    /**
     * 数量
     */
    @Column(name="num")
    private String num;

    /**
     * 预算费用
     */
    @Column(name="cost")
    private String cost;

    /**
     * 申请理由
     */
    @Column(name="apply_reason")
    private String applyReason;

    /**
     * 维修申请人（即设备保养人）
     */
    private String applyPerson;

    /**
     * 投产日期
     */
    @Column(name="use_date")
    private Date useDate;

    /**
     * 资产编码
     */
    @Column(name="property_num")
    private String propertyNum;

//    /**
//     * 记录ID
//     */
//    @Column(name = "second_res_id")
//    private Long resId;
   /**
     * 记录ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 关联设备id
     * 一个设备多条维修记录
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="equi_id")
    private Equipment equipment;




    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getOutFactoryNum() {
        return outFactoryNum;
    }

    public void setOutFactoryNum(String outFactoryNum) {
        this.outFactoryNum = outFactoryNum;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getPropertyNum() {
        return propertyNum;
    }

    public void setPropertyNum(String propertyNum) {
        this.propertyNum = propertyNum;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getApplyPerson() {
        return equipment.getSavePersonName();
    }

}
