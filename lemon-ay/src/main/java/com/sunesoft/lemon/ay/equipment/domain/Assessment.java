package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */

/**
 * 设备评估
 */
@Entity
@Table(name ="syy_oa_ay_assess")
public class Assessment extends BaseEntity
{
    public Assessment() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }
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
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "assment_id")
    private List<AssessContent> assessContents;

    /**
     * 关联设备id
     */
    @Column(name = "equipment_id")
    private Long equipmentId;

    /**
     * 评估时间
     */
    @Column(name = "ass_time")
    private Date time;

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


    public List<AssessContent> getAssessContents() {
        return assessContents;
    }

    public void setAssessContents(List<AssessContent> assessContents) {
        this.assessContents = assessContents;
    }

}
