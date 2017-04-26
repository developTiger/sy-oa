package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/16.
 */
public class WorkExperienceCriteria extends PagedCriteria {
    /**
     * 就职单位
     */
    private String company; //就职单位
    /**
     * 工作名称
     */
    private String workName;//工作名称

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}
