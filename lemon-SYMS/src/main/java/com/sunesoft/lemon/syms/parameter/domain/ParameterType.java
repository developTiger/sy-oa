package com.sunesoft.lemon.syms.parameter.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhouz on 2016/6/1.
 */
@Entity
@Table(name="syy_oa_sys_param_type")
public class ParameterType extends BaseEntity {

    @Column(name = "param_type_name")
    private String paramTypeName; //参数类型

    @Column(name="param_desc")
    private String paramDesc;//参数描述

//    @OneToMany
//    @JoinColumn(name = "param_type_id")
//    private List<com.sunesoft.lemon.syms.parameter.domain.Parameter> parameters;//参数列表

    public String getParamTypeName() {
        return paramTypeName;
    }

    public void setParamTypeName(String paramTypeName) {
        this.paramTypeName = paramTypeName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }



}
