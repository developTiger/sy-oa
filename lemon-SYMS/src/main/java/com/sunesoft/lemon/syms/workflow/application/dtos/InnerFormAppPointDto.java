package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;

import java.util.Date;

/**
 * Created by zhouz on 2016/8/10.
 */
public class InnerFormAppPointDto {


    private Long id;
    /**
     * 表单类型
     */
    private String formApproveStepName;//表单类型
    /**
     * 表单类型
     */
    private String formKind;//表单类型

    /**
     * 顺序
     */
    private Integer appSerial;//顺序

    /**
     * 签核类型
     */
    private AppType appType;


    /**
     * 签核人
     */
    private Long appUserId;//签核人

    /**
     * 签核人姓名
     */
    private String appName; //签核人姓名


    private Boolean byDept;


    private int DeptType;


    private Date appDate;

    private AppValue appValue;

    private Long nextPointId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormApproveStepName() {
        return formApproveStepName;
    }

    public void setFormApproveStepName(String formApproveStepName) {
        this.formApproveStepName = formApproveStepName;
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

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
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

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public AppValue getAppValue() {
        return appValue;
    }

    public void setAppValue(AppValue appValue) {
        this.appValue = appValue;
    }

    public Long getNextPointId() {
        return nextPointId;
    }

    public void setNextPointId(Long nextPointId) {
        this.nextPointId = nextPointId;
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
