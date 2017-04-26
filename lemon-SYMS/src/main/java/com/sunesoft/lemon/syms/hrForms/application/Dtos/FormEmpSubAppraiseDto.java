package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/29.
 */
public class FormEmpSubAppraiseDto extends BaseFormDto {

    private Long appraisYear;

    private String appraisTitle;

    private String description;

    private String empName;


    private Date beginDate;

    private Date endDate;

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

    private String remark;


    private List<EmpAppraiseDetailDto> empSubAppraiseDetailDtos;

    private Date empSelfDate;

    public Date getEmpSelfDate() {
        return empSelfDate;
    }

    public void setEmpSelfDate(Date empSelfDate) {
        this.empSelfDate = empSelfDate;
    }


    public Long getAppraisYear() {
        return appraisYear;
    }

    public void setAppraisYear(Long appraisYear) {
        this.appraisYear = appraisYear;
    }

    public String getAppraisTitle() {
        return appraisTitle;
    }

    public void setAppraisTitle(String appraisTitle) {
        this.appraisTitle = appraisTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<EmpAppraiseDetailDto> getEmpSubAppraiseDetailDtos() {
        return empSubAppraiseDetailDtos;
    }

    public void setEmpSubAppraiseDetailDtos(List<EmpAppraiseDetailDto> empSubAppraiseDetailDtos) {
        this.empSubAppraiseDetailDtos = empSubAppraiseDetailDtos;
    }
}
