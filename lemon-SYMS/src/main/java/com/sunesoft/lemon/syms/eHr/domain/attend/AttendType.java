package com.sunesoft.lemon.syms.eHr.domain.attend;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 假日类型
 * Created by xiazl on 2017/3/2.
 */
@Entity
@Table(name = "syy_oa_hr_attType")
public class AttendType extends BaseEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 标记符
     */
    private String cord;

    /**
     * 描述(例如：婚假用于结婚的假期)
     */
    private String description;

    public AttendType() {
        setIsActive(true);
    }


    public void setCord(String cord) {
        this.cord = cord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCord() {
        return cord;
    }
}
