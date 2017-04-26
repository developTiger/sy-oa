package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;

import javax.persistence.Column;

/**
 * Created by zhouz on 2016/6/17.
 */
public class FormAppListDto {

    private Long id;

    private String formApproveStepName;//表单名称

    private String formKind;

    private Integer appSerial;//顺序

    private Long appUserId;//签核人

    private Long appRoleId;

    private FormAppRoleDto fmAppRole;

    private String appName; //签核人姓名
    /**
     * 签核类型
     */
    private AppType appType;

    private Integer clStep;

    private Boolean byDept;


    private int DeptType;
    /**
     * 步骤描述
     */
    private String clStepDesc;

    private String viewAction;


    private String approveAction;


    public String getClStepDesc() {
        return clStepDesc;
    }

    public void setClStepDesc(String clStepDesc) {
        this.clStepDesc = clStepDesc;
    }

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


    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public FormAppRoleDto getFmAppRole() {
        return fmAppRole;
    }

    public void setFmAppRole(FormAppRoleDto fmAppRole) {
        this.fmAppRole = fmAppRole;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(Long appRoleId) {
        this.appRoleId = appRoleId;
    }

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Integer getAppSerial() {
        return appSerial;
    }

    public void setAppSerial(Integer appSerial) {
        this.appSerial = appSerial;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public String getViewAction() {
        return viewAction;
    }

    public void setViewAction(String viewAction) {
        this.viewAction = viewAction;
    }

    public String getApproveAction() {
        return approveAction;
    }

    public void setApproveAction(String approveAction) {
        this.approveAction = approveAction;
    }

    public String getFormApproveStepName() {
        return formApproveStepName;
    }

    public void setFormApproveStepName(String formApproveStepName) {
        this.formApproveStepName = formApproveStepName;
    }

    public Boolean getByDept() {
        return byDept;
    }

    public void setByDept(Boolean byDept) {
        this.byDept = byDept;
    }

    public int getDeptType() {
        return DeptType;
    }

    public void setDeptType(Integer deptType) {
        DeptType = deptType;
    }

    public void setDeptType(int deptType) {
        DeptType = deptType;
    }
}
