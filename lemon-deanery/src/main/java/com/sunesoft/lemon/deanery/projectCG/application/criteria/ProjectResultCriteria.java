package com.sunesoft.lemon.deanery.projectCG.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 * 项目成果筛选条件
 */
public class ProjectResultCriteria extends PagedCriteria{

    /**
     * 获奖名称
     */
    private String win_Result_Name;

    /**
     * 颁发单位
     */
    private String issuing_Unit;

    /**
     * 证书编号
     *
     */
    private String certif_No;

    /**
     * 获奖级别
     */
    private String win_Level;

    /**
     * 获奖级别
     */
    private String win_Grade;

    /**
     * 开始时间
     */
    private Date win_begin_date;

    /**
     * 结束时间
     */
    private Date win_end_date;

    /**
     * 是否合作
     */
    private Boolean is_Cooperate_Result;


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

    public Date getWin_begin_date() {
        return win_begin_date;
    }

    public void setWin_begin_date(Date win_begin_date) {
        this.win_begin_date = win_begin_date;
    }

    public Date getWin_end_date() {
        return win_end_date;
    }

    public void setWin_end_date(Date win_end_date) {
        this.win_end_date = win_end_date;
    }

    public Boolean getIs_Cooperate_Result() {
        return is_Cooperate_Result;
    }

    public void setIs_Cooperate_Result(Boolean is_Cooperate_Result) {
        this.is_Cooperate_Result = is_Cooperate_Result;
    }
}
