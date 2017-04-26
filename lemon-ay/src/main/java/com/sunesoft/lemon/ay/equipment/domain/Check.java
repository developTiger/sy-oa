package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备检测
 * Created by jiangkefan on 2016/8/18.
 */
@Entity
@Table(name="syy_oa_ay_check")

public class Check extends BaseEntity {
    public Check() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 设备id xzl add
     */
    private Long resId;
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
    @Column(name="service_unit")
    private String serviceUnit;

    /**
     * 设备数量
     */
    @Column(name="num")
    private String num;

    /**
     * 维修项目
     */
    @Column(name="repair_project")
    private String repairProject;


    /**
     * 维修项目运行情况
     */
    @Column(name="run_condition")
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

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }
}
