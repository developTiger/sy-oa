package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wangwj on 2016/8/2 0002.
 * 住宿费用预算表
 */
@Entity
@Table(name = "syy_oa_reception_accommodation")
public class Accommodation extends BaseEntity {
    /**
     * 关联主表ReceptionNB（syy_oa_reception_nb）中的主键ID
     */
    @Column(name = "reception_id",columnDefinition = "number(19)")
    private Long receptionId;
    /**
     * 套间数量
     */
    @Column(name = "suite_num",columnDefinition = "number(5)")
    private Integer suiteNum;
    //套间单价
    @Column(name = "suite_price",columnDefinition = "number(5)")
    private Double suitePrice;

    /**
     * 单间数量
     */
    @Column(name = "single_room_num",columnDefinition = "number(5)")
    private Integer singleRoomNum;
    //单间单价
    @Column(name = "single_room_price",columnDefinition = "number(5)")
    private  Double singleRoomPrice;
    /**
     * 标准间数量
     */
    @Column(name = "standard_room_num",columnDefinition = "number(5)")
    private Integer standardRoomNum;
    //标准间单价
    @Column(name = "standard_room_price",columnDefinition = "number(5)")
    private Double standardRoomPrice;
    /**
     * 其他房型数量
     */
    @Column(name = "other_room_num",columnDefinition = "number(5)")
    private Integer otherRoomNum;
    //其他房型单价
    @Column(name = "other_room_price",columnDefinition = "number(5)")
    private Double otherRoomPrice;
    /**
     * 预算费用
     */
    @Column(name = "budget",columnDefinition = "number(19,3)")
    private Double budget;


    public Long getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Long receptionId) {
        this.receptionId = receptionId;
    }

    public Integer getSuiteNum() {
        return suiteNum;
    }

    public void setSuiteNum(Integer suiteNum) {
        this.suiteNum = suiteNum;
    }

    public Integer getSingleRoomNum() {
        return singleRoomNum;
    }

    public void setSingleRoomNum(Integer singleRoomNum) {
        this.singleRoomNum = singleRoomNum;
    }

    public Integer getStandardRoomNum() {
        return standardRoomNum;
    }

    public void setStandardRoomNum(Integer standardRoomNum) {
        this.standardRoomNum = standardRoomNum;
    }

    public Integer getOtherRoomNum() {
        return otherRoomNum;
    }

    public void setOtherRoomNum(Integer otherRoomNum) {
        this.otherRoomNum = otherRoomNum;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getSuitePrice() {
        return suitePrice;
    }

    public void setSuitePrice(Double suitePrice) {
        this.suitePrice = suitePrice;
    }

    public Double getSingleRoomPrice() {
        return singleRoomPrice;
    }

    public void setSingleRoomPrice(Double singleRoomPrice) {
        this.singleRoomPrice = singleRoomPrice;
    }

    public Double getStandardRoomPrice() {
        return standardRoomPrice;
    }

    public void setStandardRoomPrice(Double standardRoomPrice) {
        this.standardRoomPrice = standardRoomPrice;
    }

    public Double getOtherRoomPrice() {
        return otherRoomPrice;
    }

    public void setOtherRoomPrice(Double otherRoomPrice) {
        this.otherRoomPrice = otherRoomPrice;
    }

    public Accommodation() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }
}
