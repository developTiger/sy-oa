package com.sunesoft.lemon.syms.workflow.application.dtos;

/**
 * Created by zhouz on 2016/5/30.
 */
public class FormListDto {

    public Long id;

    private String formKind;// 表单种类编号

    private String formName; //表单名称

    private String description; //表单描述

    private Integer formType;//表单类型

    private Integer version;//版本号

    private Integer limitTime;// 有效期

    private Boolean isBatchApproved;//批量签核

    private Integer formStatus;//表单状态

    private Boolean closeForm;// 是否已结案

    public Boolean hasApplyView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Boolean getIsBatchApproved() {
        return isBatchApproved;
    }

    public void setIsBatchApproved(Boolean isBatchApproved) {
        this.isBatchApproved = isBatchApproved;
    }

    public Integer getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(Integer formStatus) {
        this.formStatus = formStatus;
    }

    public Boolean getCloseForm() {
        return closeForm;
    }

    public void setCloseForm(Boolean closeForm) {
        this.closeForm = closeForm;
    }

    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }
}
