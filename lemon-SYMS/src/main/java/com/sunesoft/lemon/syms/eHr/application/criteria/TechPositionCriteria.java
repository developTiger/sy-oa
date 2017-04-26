package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/16.
 */
public class TechPositionCriteria extends PagedCriteria {
    /**
     * 资格专业
     */
    private String major;//资格专业
    /**
     * 资格名称
     */
    private String name;//资格名称
    /**
     * 资格级别:高，中，初
     */
    private String level;//资格级别:高，中，初

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
