package com.sunesoft.lemon.syms.workflow.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by zhouz on 2016/5/19.
 */
public class FormListCriteria extends PagedCriteria {
    private  Long Id;
    private String formKind;

    private Long formNo;

    private String formName;

    private Long appUserId;

    private Boolean hasApplyView;

    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
