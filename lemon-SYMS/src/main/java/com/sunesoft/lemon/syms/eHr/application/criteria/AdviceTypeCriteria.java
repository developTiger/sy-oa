package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.syms.parameter.application.criteria.ParameterCriteria;

/**
 * Created by xiazl on 2017/1/18.
 */
public class AdviceTypeCriteria extends ParameterCriteria {


    private String name;

    private Boolean forbide;

    public Boolean getForbide() {
        return forbide;
    }

    public void setForbide(Boolean forbide) {
        this.forbide = forbide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
