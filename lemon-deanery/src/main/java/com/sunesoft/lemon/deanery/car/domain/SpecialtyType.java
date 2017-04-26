package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 专业类别配置
 * Created by zy on 2016/6/27 0027.
 */
@Entity
@Table(name = "syy_oa_SpecialtyType")
public class SpecialtyType extends BaseEntity {


    /**
     *    专业类别
     * 01-油气勘探
     * 02-油气开发
     * 03-炼油化工
     * 04-油气集输
     * 05-计 算 机
     * 06-软 科 学
     * 07-安全环保
     */
    @Column(name = "specialty_type", columnDefinition = "VARCHAR2(20) DEFAULT NULL ")
    private String specialtyType;

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }
}
