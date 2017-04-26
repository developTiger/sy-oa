package com.sunesoft.lemon.ay.equimentForms.application.dtos;

/**
 * Created by jiangkefan on 2016/8/17.
 */

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.List;


/**
 * 设备维修验收通知单
 */
public class FormCheckDto extends BaseFormDto {


    /**
     * 设备的id//xzl add
     */
    private Long resId;
    /**
     * 设备名称
     */
    private String resName;


    /**
     * 维修方
     */
    private String serviceUnit;

    /**
     * 设备数量
     */
    private String num;

    /**
     * 维修项目
     */
    private String repairProject;

    /**
     * 使用单位
     */
    private String unit;

    /**
     * 使用单位标识
     */
    private Long belongUnitId;

    /**
     * 规格型号
     */
    private String  standard;



    /**
     * 维修项目运行情况
     */
    private String runCondition;

    private String testers;

    public String getTesters() {
        return testers;
    }

    public void setTesters(String testers) {
        this.testers = testers;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRepairProject() {
        return repairProject;
    }

    public void setRepairProject(String repairProject) {
        this.repairProject = repairProject;
    }

    public String getRunCondition() {
        return runCondition;
    }

    public void setRunCondition(String runCondition) {
        this.runCondition = runCondition;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public Long getBelongUnitId() {
        return belongUnitId;
    }

    public void setBelongUnitId(Long belongUnitId) {
        this.belongUnitId = belongUnitId;
    }
}
