package com.sunesoft.lemon.ay.equimentForms.domain;

import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */

/**
 * 设备评估流程表
 */
@Entity
@Table(name="syy_oa_ay_form_assess")
public class FormAssessment extends BaseFormEntity{


    public FormAssessment(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
        this.formAssessContents = Collections.EMPTY_LIST;
        this.setViewUrl("equipForms");
    }

    /**
     * 设备评估时间
     */
    @Column(name = "assement_time")
    private Date time;

    /**
     * 设备名称
     */
    @Column(name="name")
    private String name;

    /**
     * 评估类型
     */
    @Column(name="assess_type")
    private String assessType;


//    /**
//     * 规格型号
//     */
//    @Column(name="standard")
//    private String standard;
//
//    /**
//     * 出厂编号
//     */
//    @Column(name="out_factory_num")
//    private String outFactoryNum;
//
//    /**
//     * 资产编码
//     */
//    @Column(name="asset_num")
//    private String assetNum;
//
//    /**
//     * 出厂投产日期
//     */
//    @Column(name="invest_date")
//    private Date investDate;
//
//    /**
//     * 使用存放地点
//     */
//    @Column(name="ammunition")
//    private String ammunition;
//
//    /**
//     * 产品或用途
//     */
//    @Column(name="product_use")
//    private String productUse;
//
//    /**
//     * 设备功率
//     */
//    @Column(name="equPower")
//    private String equPower;
//
//    /**
//     * 生产厂家
//     */
//    @Column(name="f_factory")
//    private String factory;
//
//    /**
//     * 原值
//     */
//    @Column(name="original")
//    private String original;
//
//
//    /**
//     * 设备使用单位
//     */
//    @Column(name="use_unit")
//    private String useUnit;
//
//    /**
//     * 设备管理部门
//     */
//    @Column(name="manager_dept")
//    private String managerDept;
//
//    /**
//     * 检测项目
//     */
//    @Column(name="check_project")
//    private String checkProject;


    /**
     * 设备技术评估内容
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "assment_id")
    private List<FormAssessContent> formAssessContents;

    /**
     * 关联设备id
     */
    @Column(name = "equipment_id")
    private Long equipmentId;


    /**
     * 评估结论
     */
    @Lob
    @Column(name = "assess_result1",columnDefinition = "CLOB")
    private String assessResult;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
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



    public List<FormAssessContent> getFormAssessContents() {
        return formAssessContents;
    }

    public void setFormAssessContents(List<FormAssessContent> formAssessContents) {
        this.formAssessContents = formAssessContents;
    }

    public String getAssessResult() {
        return assessResult;
    }

    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }
}
