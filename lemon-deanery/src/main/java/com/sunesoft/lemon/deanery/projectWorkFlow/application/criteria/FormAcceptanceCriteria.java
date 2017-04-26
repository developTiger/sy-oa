package com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by swb on 2016/12/29.
 */
public class FormAcceptanceCriteria extends PagedCriteria {
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
