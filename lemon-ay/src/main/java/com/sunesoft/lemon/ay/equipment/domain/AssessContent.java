package com.sunesoft.lemon.ay.equipment.domain;

/**
 * Created by jiangkefan on 2016/8/19.
 */

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 评估内容表
 */
@Entity
@Table(name="syy_oa_ay_assess_con")
public class AssessContent extends BaseEntity {


    public AssessContent(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    /**
     * 设备评估流程ID
     */
    @Column(name = "assment_id")
    private Long assmentId;

    /**
     * 项目参数名
     */
    @Column(name ="parameter_name")
    private  String parameterName;

    /**
     * 参数范围
     */
    @Column(name="parameter_range")
    private String parameterRange;

    /**
     * 试验值
     */
    @Column(name="test_value")
    private String testValue;

    /**
     * 符合程度
     */
    @Column(name="conform")
    private  String conform;

    /**
     * 评估或试验人员
     */
    @Column(name="test_crew")
    private String testCrew;

    /**
     * 建议或措施
     */
    @Column(name="suggest")
    private String suggest;

    /**
     * 验证实施
     */
    @Column(name="implement")
    private String implement;

    /**
     * 关联设备id
     */
    @Column(name = "equipment_id")
    private Long equipmentId;

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterRange() {
        return parameterRange;
    }

    public void setParameterRange(String parameterRange) {
        this.parameterRange = parameterRange;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getConform() {
        return conform;
    }

    public void setConform(String conform) {
        this.conform = conform;
    }

    public String getTestCrew() {
        return testCrew;
    }

    public void setTestCrew(String testCrew) {
        this.testCrew = testCrew;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }


    public Long getAssmentId() {
        return assmentId;
    }

    public void setAssmentId(Long assmentId) {
        this.assmentId = assmentId;
    }
}
