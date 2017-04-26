package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/5/25.
 */
@Entity
@Table(name="syy_oa_fm_approve_list")
public class FormApproveList extends BaseEntity {

    /**
     * 表单类型
     */
    @Column(name = "form_approve_step_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formApproveStepName;//表单类型



    @Column(name="by_dept")
    private Boolean byDept;

    /**
     * 表单类型
     */
    @Column(name = "form_kind", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKind;//表单类型

    /**
     * 顺序
     */
    @Column(name = "app_serial")
    private Integer appSerial;//顺序

    /**
     * 签核类型
     */
    @Column(name = "app_type")
    private AppType appType;


    /**
     * 签核人
     */
    @Column(name = "app_user_id")
    private Long appUserId;//签核人


    /**
     * 签核人姓名
     */
    @Column(name = "app_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String appName; //签核人姓名


    /**
     * 步骤
     */
    @Column(name = "cl_step")
    private Integer clStep;//步骤

    /**
     * 步骤描述
     */
    @Column(name = "cl_step_desc",columnDefinition = "varchar(100) DEFAULT NULL")
    private String clStepDesc;//步骤


    //ViewUrlAction

    //ApproveAction

    /**
     * 审核人按部门筛选类型
     * 部门类型  0 ：申请人部门  1：归口部门（blongDept）
     */
    @Column(name="dept_type")
    private int DeptType;


    /**
     * 签核角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_role_id")
    private FormApproveRole appRole;//签核角色


    /**
     * 该节点页面Action
     */
    @Column(name = "view_action", columnDefinition = "varchar(100) DEFAULT NULL")
    private String viewAction;


    /**
     * 该节点审核Action
     */
    @Column(name = "approve_action", columnDefinition = "varchar(100) DEFAULT NULL")
    private String approveAction;



    public FormApproveList(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.clStep=0;
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


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClStepDesc() {
        return clStepDesc;
    }

    public void setClStepDesc(String clStepDesc) {
        this.clStepDesc = clStepDesc;
    }

    public FormApproveRole getAppRole() {
        return appRole;
    }

    public void setAppRole(FormApproveRole appRole) {
        this.appRole = appRole;
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

    public void setDeptType(int deptType) {
        DeptType = deptType;
    }
}
