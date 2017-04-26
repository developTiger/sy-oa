package com.sunesoft.lemon.deanery.projectCG.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 * 项目成果实体类
 */
@Entity
@Table(name = "syy_oa_project_result")
public class ProjectResult extends BaseFormEntity {

    /**
     * 获奖名称
     */
    @Column(name = "win_result_name",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String win_Result_Name;


    /**
     * 颁发单位
     */
    @Column(name = "issuing_unit",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String issuing_Unit;

    /**
     * 证书编号
     */
    @Column(name = "certif_no",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String certif_No;

    /**
     * 获奖项目
     */
    @Column(name = "win_project",
            columnDefinition = "NUMBER(19) DEFAULT NULL")
    private Long win_Project;


    /**
     * 获奖成果选择1，2，3，
     */
    @Column(name = "win_Result_type",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String win_Result_type;

    /**
     * 获奖成果分类
     */
    @Column(name = "win_result_classify",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String win_Result_Classify;

    /**
     * 获奖级别
     */
    @Column(name = "win_level",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String win_Level;

    /**
     * 获奖等级
     */
    @Column(name = "win_grade",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String win_Grade;

    /**
     * 获奖日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "win_date")
    private Date win_Date;

    /**
     * 是否合作成果。0：否，1：是；
     */
    @Column(name = "is_cooperate_result")
    private Boolean is_Cooperate_Result;

    /*
    * 基层领导意见
    * */
    @Column(name = "leader_opinion")
    private String leaderOpinion;

    /*
    * 成果负责部门审核
    * */
    @Column(name = "dept_opinion")
    private String deptOpinion;


    public ProjectResult() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public String getWin_Result_Name() {
        return win_Result_Name;
    }

    public void setWin_Result_Name(String win_Result_Name) {
        this.win_Result_Name = win_Result_Name;
    }

    public String getIssuing_Unit() {
        return issuing_Unit;
    }

    public void setIssuing_Unit(String issuing_Unit) {
        this.issuing_Unit = issuing_Unit;
    }

    public String getCertif_No() {
        return certif_No;
    }

    public void setCertif_No(String certif_No) {
        this.certif_No = certif_No;
    }

    public Long getWin_Project() {
        return win_Project;
    }

    public void setWin_Project(Long win_Project) {
        this.win_Project = win_Project;
    }

    public String getWin_Result_Classify() {
        return win_Result_Classify;
    }

    public void setWin_Result_Classify(String win_Result_Classify) {
        this.win_Result_Classify = win_Result_Classify;
    }

    public String getWin_Level() {
        return win_Level;
    }

    public String getWin_Result_type() {
        return win_Result_type;
    }

    public void setWin_Result_type(String win_Result_type) {
        this.win_Result_type = win_Result_type;
    }

    public void setWin_Level(String win_Level) {
        this.win_Level = win_Level;
    }

    public String getWin_Grade() {
        return win_Grade;
    }

    public void setWin_Grade(String win_Grade) {
        this.win_Grade = win_Grade;
    }

    public Date getWin_Date() {
        return win_Date;
    }

    public void setWin_Date(Date win_Date) {
        this.win_Date = win_Date;
    }

    public Boolean getIs_Cooperate_Result() {
        return is_Cooperate_Result;
    }

    public void setIs_Cooperate_Result(Boolean is_Cooperate_Result) {
        this.is_Cooperate_Result = is_Cooperate_Result;
    }

    public String getLeaderOpinion() {
        return leaderOpinion;
    }

    public void setLeaderOpinion(String leaderOpinion) {
        this.leaderOpinion = leaderOpinion;
    }

    public String getDeptOpinion() {
        return deptOpinion;
    }

    public void setDeptOpinion(String deptOpinion) {
        this.deptOpinion = deptOpinion;
    }
}
