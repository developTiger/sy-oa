package com.sunesoft.lemon.ay.equipment.application.dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;

/**
 * Created by jiangkefan on 2016/8/18.
 */
public class CheckDto extends BaseFormDto {


    /**
     * 设备名称
     */
    private String resName;

    /**
     * 使用单位
     */
    private String unit;

    /**
     * 规格型号
     */
    private String  standard;

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
     * 维修项目运行情况
     */
    private String runCondition;


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

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
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

}
