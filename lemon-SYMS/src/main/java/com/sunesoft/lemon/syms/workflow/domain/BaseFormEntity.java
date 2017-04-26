package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by zhouz on 2016/6/29.
 */
@MappedSuperclass
public class BaseFormEntity extends BaseEntity {

    @Column(name="form_no")
    private Long formNo;

    @Column(name="form_kind", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKind;

    @Column(name="form_kind_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKindName;
/*
* 申请人
* */
    @Column(name="applyer")
    private Long applyer;

    @Column(name="applyer_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String applyerName;

    @Column(name="dept_id")
    private Long deptId;

    @Column(name="dept_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String deptName;

    @Column(name="apply_date")
    private Date applyDate;

    @Column(name="form_status")
    private FormStatus formStatus;

    @Column(name="form_type")
    private Integer formType;//表单类型   1 静态签核人表单 0 动态签核人表单

    @Column(name="cl_step")
    private Integer clStep; //当前步骤

    @Column(name="is_complete")
    private Boolean isComplete;


    @Column(name="parent_form_no")
    private Long parentFormNo;

    @Column(name = "view_url", columnDefinition = "varchar(100) DEFAULT NULL")
    private String viewUrl;

    /**
     * 归口部门
     */
    @Column(name="blong_dept_id")
    private Long blongDeptId;

    /**
     * 归属部门名称
     */
    @Column(name = "blong_dept_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String blongDeptName;//归属部门名称

    @Column(name="has_apply_view")
    public Boolean hasApplyView;

    /**
     * 该节点页面Action
     */
    @Column(name = "view_action")
    private String currentViewAction;


    /**
     * 该节点审核Action
     */
    @Column(name = "approve_action")
    private String currentApproveAction;

    /**
     *
     */
    private String fTemp;

    public String getBlongDeptName() {
        return blongDeptName;
    }

    public void setBlongDeptName(String blongDeptName) {
        this.blongDeptName = blongDeptName;
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

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public String getFormKindName() {
        return formKindName;
    }

    public void setFormKindName(String formKindName) {
        this.formKindName = formKindName;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
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

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public Long getBlongDeptId() {
        return blongDeptId;
    }

    public void setBlongDeptId(Long blongDeptId) {
        this.blongDeptId = blongDeptId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public String getCurrentViewAction() {
        return currentViewAction;
    }

    public void setCurrentViewAction(String currentViewAction) {
        this.currentViewAction = currentViewAction;
    }

    public String getCurrentApproveAction() {
        return currentApproveAction;
    }

    public void setCurrentApproveAction(String currentApproveAction) {
        this.currentApproveAction = currentApproveAction;
    }

    public String getfTemp() {
        return fTemp;
    }

    public void setfTemp(String fTemp) {
        this.fTemp = fTemp;
    }
}
