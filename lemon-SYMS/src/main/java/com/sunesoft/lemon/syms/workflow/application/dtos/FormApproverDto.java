package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.AssignType;

import java.util.Date;

/**
 * Created by zhouz on 2016/6/17.
 */
public class FormApproverDto {

    private Long formNo;

    private String formKind;

    private Integer appSerial;//顺序

    private Long appAssigner;//加签人或者是系统

    private Long appUserId;//签核人

    private Integer clStep;
    //private FormApproveRole appRole;
    private Long nextApproverId;//下一个签核人

    private Long appActor;//实际完成人

    private String appName; //签核人姓名

    private ApproveStatus approveStatus;//工作状态

    private Long roleId;

    private AppType appType;//工作类型

    private AssignType assignType;//分配类型

    private Date beginDate;

    private Date endDate;

    private AppValue appValue;//签核值

    private String appContent;//签核意见

    private String assignReason;//加签 转签意见

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Integer getAppSerial() {
        return appSerial;
    }

    public void setAppSerial(Integer appSerial) {
        this.appSerial = appSerial;
    }

    public Long getAppAssigner() {
        return appAssigner;
    }

    public void setAppAssigner(Long appAssigner) {
        this.appAssigner = appAssigner;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Long getNextApproverId() {
        return nextApproverId;
    }

    public void setNextApproverId(Long nextApproverId) {
        this.nextApproverId = nextApproverId;
    }

    public Long getAppActor() {
        return appActor;
    }

    public void setAppActor(Long appActor) {
        this.appActor = appActor;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public AssignType getAssignType() {
        return assignType;
    }

    public void setAssignType(AssignType assignType) {
        this.assignType = assignType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AppValue getAppValue() {
        return appValue;
    }

    public void setAppValue(AppValue appValue) {
        this.appValue = appValue;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }

    public String getAssignReason() {
        return assignReason;
    }

    public void setAssignReason(String assignReason) {
        this.assignReason = assignReason;
    }
}
