package com.sunesoft.lemon.ay.equimentForms.application.dtos;

import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */
public class FormAssessmentDto extends BaseFormDto{

    /**
     * 设备评估时间
     */
    private Date time;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 评估类型
     */
    private String assessType;

//    /**
//     * 规格型号
//     */
//    private String standard;
//
//    /**
//     * 出厂编号
//     */
//    private String outFactoryNum;
//
//    /**
//     * 资产编码
//     */
//    private String assetNum;
//
//    /**
//     * 出厂投产日期
//     */
//    private Date investDate;
//
//    /**
//     * 使用存放地点
//     */
//    private String ammunition;
//
//    /**
//     * 产品或用途
//     */
//    private String productUse;
//
//    /**
//     * 设备功率
//     */
//    private String equPower;
//
//    /**
//     * 生产厂家
//     */
//    private String factory;
//
//    /**
//     * 原值
//     */
//    private String original;
//
//    /**
//     * 设备使用单位
//     */
//    private String useUnit;
//
//    /**
//     * 设备管理部门
//     */
//    private String managerDept;
//
//    /**
//     * 检测项目
//     */
//    private String checkProject;

    /**
     * 设备技术评估内容 formAssessment
     */
    private List<AssessContentDto> assessContentDtos;

    private String assessResult;
    /**
     * 关联设备id
     */
    private EquipmentDto equipmentDto;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public EquipmentDto getEquipmentDto() {
        return equipmentDto;
    }

    public void setEquipmentDto(EquipmentDto equipmentDto) {
        this.equipmentDto = equipmentDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssessType() {
        return assessType;
    }

    public void setAssessType(String assessType) {
        this.assessType = assessType;
    }



    public List<AssessContentDto> getAssessContentDtos() {
        return assessContentDtos;
    }

    public void setAssessContentDtos(List<AssessContentDto> assessContentDtos) {
        this.assessContentDtos = assessContentDtos;
    }

    public String getAssessResult() {
        return assessResult;
    }

    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }
}
