package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/16.
 */
public class FamCriteria extends PagedCriteria {
    /**
     * 姓名
     */
    private String name;//姓名
    /**
     * 称谓
     */
    private String label;//称谓

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
