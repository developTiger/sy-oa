package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/7/26.
 */

@Entity
@Table(name="syy_oa_emp_appraise_detail")
public class EmpAppraiseDetail extends BaseEntity{

    public EmpAppraiseDetail()
    {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 考核信息
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "emp_appraise_id")
    private EmpAppraise empAppraise;

    /**
     * 员工信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraise_emp_id")
    private Employee employee;

    private Long deptId;

    private Integer appStatus; // 0:等待自评  1：//自评结束  2：考评完成


    /**
     * 专家组分数 已经改为考核委员会评分
     */
    private String chargeLeaderScores;

    /**
     * 专家组等级 已经改为考核委员会评价等级
     */
    private String chargeLeaderLevel;


    /**
     * 自评档次 不用
     */
    private String empSelfLevel;

    /**
     * 自评分数 不用
     */
    private Float  empSelfScores;

    /**
     * 自评等级
     */
    private String empSelfGrade;

    /**
     * 自评备注 不用
     */
    private String empSelfRemark;

    /**
     * 部门档次 不用
     */
    private String deptLevel;

    /**
     * 部门分数 已经改为基层领导评分
     */
    private Float   deptScores;

    /**
     * 部门等级 已经改为基层领导评价级别
     */
    private String  deptGrade;

    /**
     * 部门备注 不用
     */
    private String  deptRemark;

    /**
     * 专家组档次 不用
     */
    private String groupLevel;

    /**
     * 业务分管领导 评分
     */
    private Float  groupScores;

    /**
     *  业务分管领导 评价等级
     */
    private String  groupGrade;

    /**
     * 专家组备注 不用
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public EmpAppraise getEmpAppraise() {
        return empAppraise;
    }

    public void setEmpAppraise(EmpAppraise empAppraise) {
        this.empAppraise = empAppraise;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }
}
