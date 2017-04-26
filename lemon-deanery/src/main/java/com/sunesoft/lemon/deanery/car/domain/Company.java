package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
@Entity
@Table(name = "syy_oa_car_company")
public class Company extends BaseEntity{
    /**
     * 所属公司名称
     */
    @Column(name = "name",columnDefinition = "VARCHAR2(100)")
    private String name;
    /**
     * 创建人ID
     */
    @Column(name = "user_id",columnDefinition = "NUMBER(19)")
    private long userId;

    public Company(){
        this.setIsActive(true);
        this.setLastUpdateTime(new Date());
        this.setCreateDateTime(new Date());
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
