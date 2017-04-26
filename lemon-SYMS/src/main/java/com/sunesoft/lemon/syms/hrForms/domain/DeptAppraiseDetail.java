package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/7/26.
 */
@Entity
@Table(name="syy_oa_dept_appraise_detail")
public class DeptAppraiseDetail extends BaseEntity{

    public DeptAppraiseDetail()
    {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

//
//    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
//    @JoinColumn(name="dept_appraise_id")
//    private DeptAppraise formDeptAppraise;
    /**
     * 部门信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraise_dept_id")
    private Deptment deptment;

    /**
     * 部门档次 已改为自评档次
     */
    @Column(name="dept_level", columnDefinition = "varchar(100) DEFAULT NULL")
    private String deptLevel;

    /**
     * 部门分数 已改为自评得分
     */
    @Column(name="dept_scores")
    private Float   deptScores;

    /**
     * 部门等级 已改为自评等级
     */
    @Column(name="dept_grade", columnDefinition = "varchar(100) DEFAULT NULL")
    private String  deptGrade;


    /**
     * 部门备注
     */
    @Column(name="dept_remark", columnDefinition = "varchar(100) DEFAULT NULL")
    private String  deptRemark;

    /**
     * 专家组档次 已改为最终档次
     */
    @Column(name="group_lLevel", columnDefinition = "varchar(100) DEFAULT NULL")
    private String groupLevel;

    /**
     * 专家组分数 已改为最终得分
     */
    @Column(name="group_scores")
    private Float  groupScores;

    /**
     * 专家组等级 已改为最终等级
     */
    @Column(name="group_grade", columnDefinition = "varchar(100) DEFAULT NULL")
    private String  groupGrade;

    /**
     * 专家组备注 已改为审核意见
     */
    @Column(name="group_remark", columnDefinition = "varchar(100) DEFAULT NULL")
    private String groupRemark;

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

    public Deptment getDeptment() {
        return deptment;
    }

    public void setDeptment(Deptment deptment) {
        this.deptment = deptment;
    }


}
