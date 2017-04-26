package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by temp on 2016/8/1.
 */
public class EmpSubAppraiseDetailDto  {

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

    private Date empSelfDate;


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

    /*
     *员工ID
     */
    private Long empId;

    private String loginName;

  /*  private Long detail_id;

    public Long getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Long detail_id) {
        this.detail_id = detail_id;
    }*/


    public Date getEmpSelfDate() {
        return empSelfDate;
    }

    public void setEmpSelfDate(Date empSelfDate) {
        this.empSelfDate = empSelfDate;
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



}
