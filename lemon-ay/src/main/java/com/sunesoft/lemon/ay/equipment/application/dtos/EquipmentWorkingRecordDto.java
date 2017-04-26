package com.sunesoft.lemon.ay.equipment.application.dtos;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by liulin on 2016/10/21.
 */
public class EquipmentWorkingRecordDto{


    private Long id;

    /**
     * 关联 设备id
     */
    private String equipmentId;

    /**
     * 日期
     */
    private Date time;

    /**
     *  操作人员 id
     */
    private Long personId;

    /**
     * 操作人员 name
     */
    private String personName;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 样品数
     */
    private Integer sampleCount;

    /**
     * 运转小时
     */
    private Float workingTime;

    /**
     * 检查及运转情况
     */
    private String checkAndWorkingSituation;


    /**
     * 备注（环境）
     */
    private String remark;

    /**
     * 累计运转小时
     */
    private Float workingTimeCount;

    /**
     * 累计样品数
     */
    private Integer allSample;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(Integer sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Float getWorkingTime() {
        return workingTime;
    }



    public void setWorkingTime(Float workingTime) {
        this.workingTime = workingTime;
    }

    public String getCheckAndWorkingSituation() {
        return checkAndWorkingSituation;
    }

    public void setCheckAndWorkingSituation(String checkAndWorkingSituation) {
        this.checkAndWorkingSituation = checkAndWorkingSituation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getWorkingTimeCount() {
        return workingTimeCount;
    }

    public void setWorkingTimeCount(Float workingTimeCount) {
        this.workingTimeCount = workingTimeCount;
    }

    public Integer getAllSample() {
        return allSample;
    }

    public void setAllSample(Integer allSample) {
        this.allSample = allSample;
    }
}
