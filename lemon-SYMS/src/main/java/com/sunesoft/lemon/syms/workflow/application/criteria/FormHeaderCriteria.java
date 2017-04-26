package com.sunesoft.lemon.syms.workflow.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
public class FormHeaderCriteria extends PagedCriteria {

    private String formKind;

    private Long formNo;

    private Long blongDept;

    private String applyerName;

    private List<FormStatus> arrFormStatus;

    private Long applyer;

    private String beginDate;

    private String endDate;

    private Long approverId;

    public Long getApproverDeptId() {
        return approverDeptId;
    }

    public void setApproverDeptId(Long approverDeptId) {
        this.approverDeptId = approverDeptId;
    }

    private Long approverDeptId;

    private  ApproveStatus approveStatus;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
    }

    public Long getBlongDept() {
        return blongDept;
    }

    public void setBlongDept(Long blongDept) {
        this.blongDept = blongDept;
    }

    public List<FormStatus> getArrFormStatus() {
        return arrFormStatus;
    }

    public void setArrFormStatus(List<FormStatus> arrFormStatus) {
        this.arrFormStatus = arrFormStatus;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public ApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }
}
