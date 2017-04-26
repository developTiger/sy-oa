package com.sunesoft.lemon.ay.equipment.application.dtos;

import com.sunesoft.lemon.ay.equipment.domain.Equipment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by jiangkefan on 2016/8/5.
 */
public class EquipmentMaintenanceDto {


    /**
     * 设备编号
     */
    private String resNum;

    /**
     * 名称
     */
    private String name;

    /**
     * 规格型号
     */
    private String standard;

    /**
     * 生产厂家
     */
    private String factory;

    /**
     * 出厂编号
     */
    private String outFactoryNum;

    /**
     * 原值
     */
    private String original;


    /**
     * 申请单位
     */
    private String applyUnit;

    /**
     * 申请人（设备保管人)
     */
    private String applyPerson;

    /**
     * 数量
     */
    private String num;

    /**
     * 预算费用
     */
    private String cost;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 投产日期
     */
    private Date useDate;

    /**
     * 资产编码
     */
    private String propertyNum;

    /**
     * 记录ID
     */
    private Long linkId;

    /**
     * 申请日期
     */
    private Date createDateTime;


    /**
     * 关联设备id
     * 一个设备多条维修记录
     */
    private EquipmentDto equipmentDto;



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

    public EquipmentDto getEquipmentDto() {
        return equipmentDto;
    }

    public void setEquipmentDto(EquipmentDto equipmentDto) {
        this.equipmentDto = equipmentDto;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
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

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }
}
