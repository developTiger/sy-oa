package com.sunesoft.lemon.ay.equipment.domain;

/**
 * Created by jiangkefan on 2016/8/5.
 */

/**
 * 设备表
 */

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="syy_oa_ay_equi")
public class Equipment extends BaseEntity{


    /**
     * 设备最终字段
     *
     * 资产编号、资产类别名称、计量单位、规格型号、制造厂家、出厂编号、所属单位、设备类型、
     * 存放地点、保管人、使用岗位、原值、净值、投产日期、检定周期(/月)、技术参数标准值。
     * 参数范围（最小值，最大值）、符合检定或者校准结果（检定，校准，其他）、检定或校准时间、
     * 下次检定或校准时间、使用状况、备注。
     */

    public Equipment() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    private Long resId;
    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }


    //region 文档定义

    /**
     * 设备名称
     */
    @Column(name = "measuring_name")
    private String measuringName;

    /**
     * 资产编码
     */
    @Column(name = "property_num")
    private String propertyNum;

    /**
     * 规格型号
     */
    @Column(name = "e_standard")
    private String standard;

    /**
     * 厂家名称
     */
    @Column(name = "factory_name")
    private String factoryName;

    /**
     * 出厂编号
     */
    @Column(name = "outFactory_num")
    private String outFactoryNum;

    /**
     * 保管人
     */
    @Column(name = "save_name")
    private String savePersonName;

    @Column(name = "save_id")
    private Long savePersonId;

    /**
     * 使用单位Id
     */
    @Column(name = "unit_id")
    private String unitId;

    /**
     * 使用单位
     */
    @Column(name = "unit_name")
    private String unitName;

    /**
     * 使用地址
     */
    private String address;

    /**
     * 使用岗位
     */
    private String post;

    /**
     * 当前状态
     */
    @Column(name = "current_station")
    private EquipmentStation currentStation;

    /**
     * 原值
     */
    @Column(name = "original")
    private String original;

    /**
     * 净值
     */
    private String netValue;

    /**
     * 购进日期
     */
    private Date buyTime;

    /**
     * 投产日期
     */
    private Date productTime;

    /**
     * 检定周期
     */
    private Integer checkdate;

    /**
     * 技术参数标准值
     */
    private Double standardValue;

    /**
     * 参数范围最小值
     */
    private Double minValue;

    /**
     * 参数范围最大值
     */
    private Double maxValue;

    /**
     * 配件清单
     */
    @Lob
    @Column(name = "parts",columnDefinition = "CLOB",nullable = true)
    private String parts;

    /**
     * 符合检定或者校准结果
     */
    @Column(name = "test_result")
    private Boolean testResult;

    /**
     * 检定或校准值
     */
    private Double testValue;

    /**
     * 检定校准时间
     */
    private Date testTime;

    /**
     * 下次检定校准时间
     */
    private Date nextTestTime;

    /**
     * 计量管理系统编号
     */
    @Column(unique = true)
    private String systemNumber;

    /**
     * 备注
     */
    @Lob
    @Column(name = "e_remark",columnDefinition = "CLOB",nullable = true)
    private String remark;

    /**
     * 文件标识
     */
    @Column(name = "file_id")
    private String fileId;
    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;
/************后加字段**********/
    /**
     * 资产类别名称
     */
    @Column(name = "property_type")
    private String propertyType;
    /**
     * 计量单位
     */
    private String measurement;
    /**
     * 所属单位
     */
    private String institute;
    /**
     * 设备类型
     */
    @Column(name = "meas_type")
    private String type;
    /**
     * 存放地点
     */
    private String deposit;
    /**
     * 使用状况
     */
    @Column(name = "use_station",columnDefinition = "CLOB",nullable = true)
    private String useStation;

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseStation() {
        return useStation;
    }

    public void setUseStation(String useStation) {
        this.useStation = useStation;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Integer checkdate) {
        this.checkdate = checkdate;
    }



    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public String getMeasuringName() {
        return measuringName;
    }

    public void setMeasuringName(String measuringName) {
        this.measuringName = measuringName;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public Date getNextTestTime() {
        return nextTestTime;
    }

    public void setNextTestTime(Date nextTestTime) {
        this.nextTestTime = nextTestTime;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOutFactoryNum() {
        return outFactoryNum;
    }

    public void setOutFactoryNum(String outFactoryNum) {
        this.outFactoryNum = outFactoryNum;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getProductTime() {
        return productTime;
    }

    public void setProductTime(Date productTime) {
        this.productTime = productTime;
    }

    public String getPropertyNum() {
        return propertyNum;
    }

    public void setPropertyNum(String propertyNum) {
        this.propertyNum = propertyNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSavePersonName() {
        return savePersonName;
    }

    public void setSavePersonName(String savePersonName) {
        this.savePersonName = savePersonName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public EquipmentStation getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(EquipmentStation currentStation) {
        this.currentStation = currentStation;
    }

    public Boolean getTestResult() {
        return testResult;
    }

    public void setTestResult(Boolean testResult) {
        this.testResult = testResult;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public Double getTestValue() {
        return testValue;
    }

    public void setTestValue(Double testValue) {
        this.testValue = testValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Long getSavePersonId() {
        return savePersonId;
    }

    public void setSavePersonId(Long savePersonId) {
        this.savePersonId = savePersonId;
    }
}
