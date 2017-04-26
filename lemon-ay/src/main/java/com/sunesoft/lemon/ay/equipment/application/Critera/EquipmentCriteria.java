package com.sunesoft.lemon.ay.equipment.application.Critera;

import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by admin on 2016/8/20.
 */
public class EquipmentCriteria extends PagedCriteria {

    private String deptId;//部门id

    /**
     * 设备名称
     */
    private String measuringName;
    /**
     * 资产编号
     */
    private String propertyNum;
    /**
     * 当前状态
     */
    private EquipmentStation currentStation;

    /**
     * 检定结果
     */
    private Boolean testResult;
    /**
     * 下次检定时间,开始时间
     */
    private String beginDate;
     /**
     * 下次检定时间,结束时间
     */
    private String endDate;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }



    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
