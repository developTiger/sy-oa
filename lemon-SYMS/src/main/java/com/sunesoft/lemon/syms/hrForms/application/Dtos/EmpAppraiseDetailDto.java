package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by temp on 2016/8/1.
 */
public class EmpAppraiseDetailDto  {

    private Long id;

    private String appName;

    private Integer appStatus;

    /**
     * 业务分管领导 评分
     */
    private String chargeLeaderScores;

    /**
     * 业务分管领导 评价等级
     */
    private String chargeLeaderLevel;

    /**
     * 自评档次
     */
    private String empSelfLevel;

    /**
     * 自评分数
     */
    private Float  empSelfScores;

    /**
     * 自评等级
     */
    private String empSelfGrade;

    /**
     * 自评备注
     */
    private String empSelfRemark;

    /*
     *员工ID
     */
    private Long empId;

    private String loginName;
    /**
     * 部门档次
     */
    private String deptLevel;

    /**
     * 部门分数
     */
    private Float   deptScores;

    /**
     * 部门等级
     */
    private String  deptGrade;

    /**
     * 部门备注
     */
    private String  deptRemark;
    /**
     * 专家组档次
     */
    private String groupLevel;

    /**
     * 专家组分数
     */
    private Float  groupScores;

    /**
     * 专家组等级
     */
    private String  groupGrade;

    /**
     * 专家组备注
     */
    private String groupRemark;

    public String getChargeLeaderLevel() {
        return chargeLeaderLevel;
    }

    public void setChargeLeaderLevel(String chargeLeaderLevel) {
        this.chargeLeaderLevel = chargeLeaderLevel;
    }

    public String getChargeLeaderScores() {
        return chargeLeaderScores;
    }

    public void setChargeLeaderScores(String chargeLeaderScores) {
        this.chargeLeaderScores = chargeLeaderScores;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmpSelfLevel() {
        return empSelfLevel;
    }

    public void setEmpSelfLevel(String empSelfLevel) {
        this.empSelfLevel = empSelfLevel;
    }

    public Float getEmpSelfScores() {
        return empSelfScores;
    }

    public void setEmpSelfScores(Float empSelfScores) {
        this.empSelfScores = empSelfScores;
    }

    public String getEmpSelfGrade() {
        return empSelfGrade;
    }

    public void setEmpSelfGrade(String empSelfGrade) {
        this.empSelfGrade = empSelfGrade;
    }

    public String getEmpSelfRemark() {
        return empSelfRemark;
    }

    public void setEmpSelfRemark(String empSelfRemark) {
        this.empSelfRemark = empSelfRemark;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }


    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Float getDeptScores() {
        return deptScores;
    }

    public void setDeptScores(Float deptScores) {
        this.deptScores = deptScores;
    }

    public String getDeptGrade() {
        return deptGrade;
    }

    public void setDeptGrade(String deptGrade) {
        this.deptGrade = deptGrade;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Float getGroupScores() {
        return groupScores;
    }

    public void setGroupScores(Float groupScores) {
        this.groupScores = groupScores;
    }

    public String getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(String groupGrade) {
        this.groupGrade = groupGrade;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }
}
