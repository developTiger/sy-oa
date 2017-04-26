package com.sunesoft.lemon.ay.equimentForms.application.dtos;

/**
 * Created by xiazl on 2016/9/23.
 */
public class CheckEquipInfo {


    //---------------------------------------------------------------

    private String measuringName;

    private String propertyNum;

//    private String propertyType;

    private String measurement;

    private String standard;

    private String factoryName;

    private String outFactoryNum;

    private String type;

//    private String deposit;

    private String post;

    private String original;

    private String netValue;

    private Double standardValue;

    private Double minValue;

    private Double maxValue;

    private Double testValue;

    private String useStation;

    private String unitName;//使用单位

    private String remark;

//    private String unitId;//使用单位id


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }



    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getMeasuringName() {
        return measuringName;
    }

    public void setMeasuringName(String measuringName) {
        this.measuringName = measuringName;
    }

    public String getPropertyNum() {
        return propertyNum;
    }

    public void setPropertyNum(String propertyNum) {
        this.propertyNum = propertyNum;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getOutFactoryNum() {
        return outFactoryNum;
    }

    public void setOutFactoryNum(String outFactoryNum) {
        this.outFactoryNum = outFactoryNum;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getTestValue() {
        return testValue;
    }

    public void setTestValue(Double testValue) {
        this.testValue = testValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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
}
