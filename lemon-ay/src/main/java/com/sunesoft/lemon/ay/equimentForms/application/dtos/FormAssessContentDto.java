package com.sunesoft.lemon.ay.equimentForms.application.dtos;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;

import javax.persistence.Column;

/**
 * Created by jiangkefan on 2016/8/19.
 */
public class FormAssessContentDto {

    private Long formNo;

    /**
     * 设备评估流程ID
     */
    private Long assmentId;
    private Long id ;

    /**
     * 项目参数名
     */
    private  String parameterName;

    /**
     * 参数范围
     */
    private String parameterRange;

    /**
     * 试验值
     */
    private String testValue;

    /**
     * 符合程度
     */
    private  String conform;

    /**
     * 评估或试验人员
     */
    private String testCrew;

    /**
     * 建议或措施
     */
    private String suggest;

    /**
     * 验证实施
     */
    private String implement;

    /**
     * 关联设备id
     */
    private EquipmentDto equipmentDto;


    public EquipmentDto getEquipmentDto() {
        return equipmentDto;
    }

    public void setEquipmentDto(EquipmentDto equipmentDto) {
        this.equipmentDto = equipmentDto;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssmentId() {
        return assmentId;
    }

    public void setAssmentId(Long assmentId) {
        this.assmentId = assmentId;
    }

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }
}
