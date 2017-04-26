package com.sunesoft.lemon.syms.workflow.application.dtos;

/**
 * Created by zhouz on 2016/7/28.
 */
public class BaseFormDto {

    private Long id;

    private Long formNo;

    private Long parentFormNo;

    private String formKind;

    private String formKindName;

    private Long applyer;

    private String applyerName;

    private Long deptId;


    private String deptName;

    public Boolean hasApplyView;

    private Long blongDeptId;

    private String blongDeptName;
    /**
     * 是否只查看  true  只查看，false  可编辑
     */
    public Boolean isViewOnly;


    private Integer clStep; //当前步骤

    private Boolean isComplete;

    private Integer formType;//表单类型   1 静态签核人表单 0 动态签核人表单


    private String fTemp;

    public Boolean getIsViewOnly() {
        return isViewOnly;
    }

    public void setIsViewOnly(Boolean isViewOnly) {
        this.isViewOnly = isViewOnly;
    }

    public String getBlongDeptName() {
        return blongDeptName;
    }

    public void setBlongDeptName(String blongDeptName) {
        this.blongDeptName = blongDeptName;
    }

    public Long getBlongDeptId() {
        return blongDeptId;
    }

    public void setBlongDeptId(Long blongDeptId) {
        this.blongDeptId = blongDeptId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public String getFormKindName() {
        return formKindName;
    }

    public void setFormKindName(String formKindName) {
        this.formKindName = formKindName;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }

    public Long getParentFormNo() {
        return parentFormNo;
    }

    public void setParentFormNo(Long parentFormNo) {
        this.parentFormNo = parentFormNo;
    }

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public String getfTemp() {
        return fTemp;
    }

    public void setfTemp(String fTemp) {
        this.fTemp = fTemp;
    }
}
