package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * 审核节点列表
 * Created by zhouz on 2016/8/8.
 */
@Entity
@Table(name = "syy_oa_inner_form_app_point")
public class InnerFormApprovePoint extends BaseEntity {
    /**
     * 表单类型
     */
    @Column(name = "form_approve_step_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formApproveStepName;//表单类型
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

    @Column(name="by_dept")
    private Boolean byDept;


    @Column(name="dept_type")
    private int DeptType;


    @Column(name="reset_approver_ids")
    private String resetApproverIds;
//    /**
//     * 签核人
//     */
//    @Column(name = "app_user_id")
//    private Long appUserId;//签核人
//
//    /**
//     * 签核人姓名
//     */
//    @Column(name = "app_name", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
//    private String appName; //签核人姓名
//


//
//    @Column(name = "app_date")
//    private Date appDate;
//
//
//    @Column(name = "app_value")
//    private AppValue appValue;
//
//
//    @Column(name="approve_status")
//    private ApproveStatus approveStatus;

    @Column(name = "remark", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String Remark;



    /**
     * 步骤
     */
    @Column(name = "cl_step")
    private Integer clStep;//步骤

    /**
     * 步骤描述
     */
    @Column(name = "cl_step_desc", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String clStepDesc;//步骤

    /**
     * 该节点页面Action
     */
    @Column(name = "view_action", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String viewAction;


    /**
     * 该节点审核Action
     */
    @Column(name = "approve_action", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String approveAction;


    //ApproveAction

    /**
     * 签核角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_role_id")
    private FormApproveRole appRole;//签核角色

    @JoinColumn(name = "next_app_id")
    private Long nextPointId;


    public InnerFormApprovePoint(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
//        this.approveStatus=ApproveStatus.A;
    }

//
//    public void setActive(){
//        this.approveStatus=ApproveStatus.U;
//    }

//    @Column(name = "role_id")
//    private Long roleId;
//
//
//    @Column(name = "approve_action", columnDefinition = "VARCHAR2(100) DEFAULT NULL")
//    private String roleName;

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
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
//
//    public Long getAppUserId() {
//        return appUserId;
//    }
//
//    public void setAppUserId(Long appUserId) {
//        this.appUserId = appUserId;
//    }
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }

//    public Date getAppDate() {
//        return appDate;
//    }
//
//    public void setAppDate(Date appDate) {
//        this.appDate = appDate;
//    }
//
//    public AppValue getAppValue() {
//        return appValue;
//    }
//
//    public void setAppValue(AppValue appValue) {
//        this.appValue = appValue;
//    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public String getClStepDesc() {
        return clStepDesc;
    }

    public void setClStepDesc(String clStepDesc) {
        this.clStepDesc = clStepDesc;
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

    public FormApproveRole getAppRole() {
        return appRole;
    }

    public void setAppRole(FormApproveRole appRole) {
        this.appRole = appRole;
    }

//    public ApproveStatus getApproveStatus() {
//        return approveStatus;
//    }
//
//    public void setApproveStatus(ApproveStatus approveStatus) {
//        this.approveStatus = approveStatus;
//    }

    public Long getNextPointId() {
        return nextPointId;
    }

    public void setNextPointId(Long nextPointId) {
        this.nextPointId = nextPointId;
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

    public String getResetApproverIds() {
        return resetApproverIds;
    }

    public void setResetApproverIds(String resetApproverIds) {
        this.resetApproverIds = resetApproverIds;
    }
}
