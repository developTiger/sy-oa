package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangwj on 2016/8/2 0002.
 * 宴会费用预算
 */
@Entity
@Table(name = "syy_oa_accomodation_banquet")
public class Banquet extends BaseEntity {
    /**
     * 关联主表ReceptionNB（syy_oa_reception_nb）中的主键ID
     */
    @Column(name = "reception_id",columnDefinition = "number(19)")
    private Long receptionId;
    /**
     * 宴会时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "banquet_time")
    private Date banquetTime;

    /**
     * 人数
     */
    @Column(name = "people_num",columnDefinition = "number(5)")
    private Integer peopleNum;
    /**
     * 标准（元/人）
     */
    @Column(name = "standard_cost",columnDefinition = "number(19,3)")
    private Double standardCost;

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

    public Date getBanquetTime() {
        return banquetTime;
    }

    public void setBanquetTime(Date banquetTime) {
        this.banquetTime = banquetTime;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Double getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Double standardCost) {
        this.standardCost = standardCost;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Banquet() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }
}
