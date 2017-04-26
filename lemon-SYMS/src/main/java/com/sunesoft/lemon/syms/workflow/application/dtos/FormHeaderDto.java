package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/6/17.
 */
public class FormHeaderDto {

    public Boolean getStepComplete() {
        return isStepComplete;
    }

    public void setStepComplete(Boolean stepComplete) {
        isStepComplete = stepComplete;
    }

    private Long id;
    private Long applyer;

    private String applyerName;

    private Long deptId;

    private String deptName;


    private Long formNo;

    private String formKind;
    private String formKindName;

    private FormStatus formStatus;

    private Date beginDate;

    private Date dueDate;

    private Date endDate;

    private Long  blongDept;

    private String blongDeptName;

    private String lifeCycle;//R开始(运行)run    F 结束 finish    P 暂停(保留)

    private Boolean noticeFlag;//是否已通知到签核人

    private String proviousApprover;

    private String content;

    private String remark;

    private String summery;

    private String viewUrl;

    private Integer clStep; //当前步骤

    /**
     * 是否已完成
     */
    private Boolean isStepComplete;

    private AppType currentAppType;

    public Boolean hasApplyView;
    /**
     * 当前节点页面Action
     */
    private String currentViewAction;


    /**
     * 当前节点审核Action
     */
    private String currentApproveAction;


   private List<InnerFormAppPointDto> innerFormAppPointDtos;

    /**
     * 当前审核节点Id
     */
    private Long currentAppPointId;
  ;
    private String currentPointName;
    /**
     * 下一个审核节点Id
     */
    private Long nextAppPointId;

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

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Boolean getIsStepComplete() {
        return isStepComplete;
    }

    public void setIsStepComplete(Boolean isStepComplete) {
        this.isStepComplete = isStepComplete;
    }

    private List<FormApprover> formApprovers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
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

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public Boolean getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(Boolean noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public String getProviousApprover() {
        return proviousApprover;
    }

    public void setProviousApprover(String proviousApprover) {
        this.proviousApprover = proviousApprover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<FormApprover> getFormApprovers() {

        Collections.sort(formApprovers, new Comparator<FormApprover>() {
            public int compare(FormApprover arg0, FormApprover arg1) {
                return arg0.getCreateDateTime().compareTo(arg1.getCreateDateTime());
            }
        });
        return formApprovers;
    }

    public void setFormApprovers(List<FormApprover> formApprovers) {
        this.formApprovers = formApprovers;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getFormKindName() {
        return formKindName;
    }

    public void setFormKindName(String formKindName) {
        this.formKindName = formKindName;
    }

    public Long getBlongDept() {
        return blongDept;
    }

    public void setBlongDept(Long blongDept) {
        this.blongDept = blongDept;
    }

    public String getBlongDeptName() {
        return blongDeptName;
    }

    public void setBlongDeptName(String blongDeptName) {
        this.blongDeptName = blongDeptName;
    }
//
//    public String getSite() {
//        return site;
//    }
//
//    public void setSite(String site) {
//        this.site = site;
//    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public AppType getCurrentAppType() {
        return currentAppType;
    }

    public void setCurrentAppType(AppType currentAppType) {
        this.currentAppType = currentAppType;
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

    public List<InnerFormAppPointDto> getInnerFormAppPointDtos() {
        return innerFormAppPointDtos;
    }

    public void setInnerFormAppPointDtos(List<InnerFormAppPointDto> innerFormAppPointDtos) {
        this.innerFormAppPointDtos = innerFormAppPointDtos;
    }

    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }


    public Long getCurrentAppPointId() {
        return currentAppPointId;
    }

    public void setCurrentAppPointId(Long currentAppPointId) {
        this.currentAppPointId = currentAppPointId;
    }

    public Long getNextAppPointId() {
        return nextAppPointId;
    }

    public void setNextAppPointId(Long nextAppPointId) {
        this.nextAppPointId = nextAppPointId;
    }

    public String getCurrentPointName() {
        return currentPointName;
    }

    public void setCurrentPointName(String currentPointName) {
        this.currentPointName = currentPointName;
    }
}
