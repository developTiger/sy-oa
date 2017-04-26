package com.sunesoft.lemon.syms.parameter.application.criteria;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.PagedCriteria;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zy on 2016/6/2.
 */

public class ParameterTypeCriteria  extends PagedCriteria {

    private String paramTypeName; //参数类型

    private String paramDesc;//参数描述

    private List<com.sunesoft.lemon.syms.parameter.domain.Parameter> parameters;//参数列表

    //以下get,set方法
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

    public List<com.sunesoft.lemon.syms.parameter.domain.Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<com.sunesoft.lemon.syms.parameter.domain.Parameter> parameters) {
        this.parameters = parameters;
    }
}
