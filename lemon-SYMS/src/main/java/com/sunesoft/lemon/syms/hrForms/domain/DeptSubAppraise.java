package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/26.
 */
@Entity
@Table(name = "syy_oa_dept_sub_appraise")
public class DeptSubAppraise extends BaseFormEntity {


    @Column(name="appraise_year")
    private Long appraisYear;

    @Column(name="apprais_title", columnDefinition = "varchar(100) DEFAULT NULL")
    private String appraisTitle;

    @Column(name="description", columnDefinition = "varchar(500) DEFAULT NULL")
    private String description;


    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    /**
     * 部门档次
     */
    @Column(name="dept_level", columnDefinition = "varchar(200) DEFAULT NULL")
    private String deptLevel;

    /**
     * 部门分数
     */
    @Column(name="dept_scores")
    private Float   deptScores;

    /**
     * 部门等级
     */
    @Column(name="dept_grade", columnDefinition = "varchar(200) DEFAULT NULL")
    private String  deptGrade;

    @Column(name="remark", columnDefinition = "varchar(200) DEFAULT NULL")
    private String remark;
    /**
     * 专家组档次
     */
    @Column(name="group_lLevel", columnDefinition = "varchar(100) DEFAULT NULL")
    private String groupLevel;

    /**
     * 专家组分数
     */
    @Column(name="group_scores")
    private Float  groupScores;

    /**
     * 专家组等级
     */
    @Column(name="group_grade", columnDefinition = "varchar(100) DEFAULT NULL")
    private String  groupGrade;

    /**
     * 专家组备注
     */
    @Column(name="group_remark", columnDefinition = "varchar(100) DEFAULT NULL")
    private String groupRemark;

    public DeptSubAppraise(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.setViewUrl("forms");
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}
