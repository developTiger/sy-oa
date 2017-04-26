package com.sunesoft.lemon.deanery.resultCertificate.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 * 成果证书实体
 */
@Entity
@Table(name = "syy_oa_resultcertificate")
public class ResultCertificate extends BaseFormEntity {
    public String getWin_Result_Name() {
        return win_Result_Name;
    }

    public void setWin_Result_Name(String win_Result_Name) {
        this.win_Result_Name = win_Result_Name;
    }



    public List<ResultCertificatePeople> getPeople() {
        return people;
    }

    public void setPeople(List<ResultCertificatePeople> people) {
        this.people = people;
    }

    public String getIssuing_Unit() {
        return issuing_Unit;
    }

    public void setIssuing_Unit(String issuing_Unit) {
        this.issuing_Unit = issuing_Unit;
    }

    public String getWin_Result_type() {
        return win_Result_type;
    }

    public void setWin_Result_type(String win_Result_type) {
        this.win_Result_type = win_Result_type;
    }

    public String getWin_Level() {
        return win_Level;
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

    public String getWin_Result_Classify() {
        return win_Result_Classify;
    }

    public void setWin_Result_Classify(String win_Result_Classify) {
        this.win_Result_Classify = win_Result_Classify;
    }

    public List<ResultCertificateFile> getFiles() {
        return files;
    }

    public void setFiles(List<ResultCertificateFile> files) {
        this.files = files;
    }

    public String getAwards_Type() {
        return awards_Type;
    }

    public void setAwards_Type(String awards_Type) {
        this.awards_Type = awards_Type;
    }

    /**
     * 成果类型
     */
    @Column(name = "awards_type")
    private String awards_Type;

    /**
     * 获奖成果名称
     */
    @Column(name = "win_result_name",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String win_Result_Name;

    public String getCertif_No() {
        return certif_No;
    }

    public void setCertif_No(String certif_No) {
        this.certif_No = certif_No;
    }

    /**
     * 证书编号
     */
    @Column(name = "certif_no",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String certif_No;

    /**
     获奖人
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultcertificate_id")
    private List<ResultCertificatePeople> people;


    /**
     * 颁发单位
     */
    @Column(name = "issuing_unit",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String issuing_Unit;

    /**
     * 获奖成果选择1，2，3，
     */
    @Column(name = "win_Result_type",
            columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String win_Result_type;

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

/*    *//**
     * 获奖项目
     *//*
    @Column(name = "win_project",
            columnDefinition = "NUMBER(19) DEFAULT NULL")
    private Long win_Project;*/


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

    /**
     * 获奖成果分类
     */
    @Column(name = "win_result_classify",
            columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String win_Result_Classify;

    public String getLevel_Awards() {
        return level_Awards;
    }

    public void setLevel_Awards(String level_Awards) {
        this.level_Awards = level_Awards;
    }
    /*
    第几获奖人
     */

    @Column(name="level_awards",columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String level_Awards;

    public String getAwards_Name() {
        return awards_Name;
    }

    public void setAwards_Name(String awards_Name) {
        this.awards_Name = awards_Name;
    }

    /**
     * 获奖人
     */
     @Column(name="awards_name",columnDefinition = "VARCHAR2(500) DEFAULT NULL")
     private String awards_Name;

    /**
     附件
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultcertificate_id")
    private List<ResultCertificateFile> files;

    public ResultCertificate() {
        this.setIsActive(true);
        this.files = new ArrayList<>();
        this.people=new ArrayList<>();
    }


}
