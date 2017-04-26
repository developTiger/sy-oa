package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import oracle.sql.DATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by liulin on 2016/10/21.
 * 设备运行记录
 */
@Entity
@Table(name = "syy_oa_ay_equipwork_record")
public class EquipmentWorkingRecord extends BaseEntity {

    public EquipmentWorkingRecord() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 关联 设备id
     */
    @Column(name = "equipment_id")
    private String equipmentId;

    /**
     * 日期
     */
    @Column(name = "time")
    private Date time;

    /**
     *  操作人员 id
     */
    @Column(name = "person_id")
    private Long personId;

    /**
     * 操作人员 name
     */
    @Column(name = "person_name")
    private String personName;

    /**
     * 工作内容
     */
    @Column(name = "work_content",columnDefinition = "CLOB DEFAULT NULL")
    private String workContent;

    /**
     * 样品数
     */
    @Column(name = "sample_count")
    private Integer sampleCount;

    /**
     * 运转小时
     */
    @Column(name = "working_time")
    private Float workingTime;

    /**
     * 检查及运转情况
     */
    @Column(name = "check_working")
    private String checkAndWorkingSituation;


    /**
     * 备注（环境）
     */
    @Column(name = "remark",columnDefinition = "CLOB DEFAULT NULL")
    private String remark;

    /**
     * 累计运转小时
     */
    @Column(name = "work_time_count")
    private Float workingTimeCount;

    /**
     * 累计样品数
     */
    @Column(name = "all_sample")
    private Integer allSample;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
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
