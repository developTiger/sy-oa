package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wangwj on 2016/8/2 0002.
 * 工作餐预算表
 */
@Entity
@Table(name = "syy_oa_reception_lunch")
public class WorkingLunch extends BaseEntity {
    /**
     * 关联主表ReceptionNB（syy_oa_reception_nb）中的主键ID
     */
    @Column(name = "reception_id",columnDefinition = "number(19)")
    private Long receptionId;
    /**
     * 人数
     */
    @Column(name = "people_num",columnDefinition = "number(5)")
    private Integer peopleNum;

    /**
     * 吃饭次数
     */
    @Column(name = "eat_num",columnDefinition = "number(5)")
    private Integer eatNum;

    /*
    * 单价
    * */
    @Column(name = "price",columnDefinition = "number(5)")
    private Double price;
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

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getEatNum() {
        return eatNum;
    }

    public void setEatNum(Integer eatNum) {
        this.eatNum = eatNum;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public WorkingLunch() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }
}
