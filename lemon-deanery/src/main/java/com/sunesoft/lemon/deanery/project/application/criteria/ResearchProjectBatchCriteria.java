package com.sunesoft.lemon.deanery.project.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by user on 2016/8/5.
 */
public class ResearchProjectBatchCriteria extends PagedCriteria {
    private String dept_name;
    private String applyer_name;

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getApplyer_name() {
        return applyer_name;
    }

    public void setApplyer_name(String applyer_name) {
        this.applyer_name = applyer_name;
    }
}
