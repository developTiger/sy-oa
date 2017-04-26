package com.sunesoft.lemon.ay.equimentForms.application.dtos;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/8/17.
 */
public class FormEquiMainDto extends BaseFormDto {



    /**
     * 设备id
     */
    private Long  resId;

    /**
     * 设备编号(未用到)
     */
    private String resNum;

    /**
     * 名称 formEquiMain
     */
    private String name;

    /**
     * 规格
     */
    private String standard;

    /**
     * 制造厂名称（生产厂家）
     */
    private String factory;

    /**
     * 出厂标号（编号）
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
     * 报修时间
     */
    private Date applyDate=new Date();

  /**
     * 预定回收设备时间
     */
    private Date expectDate;

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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }
}
