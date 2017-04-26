package com.sunesoft.lemon.deanery.delayflow.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by swb on 2016/12/22.
 */
public class FormDelayCriteria extends PagedCriteria {
    /**
     * 专业类别
     */
    private String majorType;

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    private Long approverId;

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
}
