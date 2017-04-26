package com.sunesoft.lemon.deanery.resultCertificate.application.dtos;

import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateFile;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificatePeople;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 */
public class ResultCertificateDto extends BaseFormDto {

    public List<ResultCertificatePeopleDto> getPeople() {
        return people;
    }

    public void setPeople(List<ResultCertificatePeopleDto> people) {
        this.people = people;
    }

    public String getCertif_No() {
        return certif_No;
    }

    public void setCertif_No(String certif_No) {
        this.certif_No = certif_No;
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

    public String getWin_Date_Str() {
        return win_Date_Str;
    }

    public void setWin_Date_Str(String win_Date_Str) {
        this.win_Date_Str = win_Date_Str;
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

    public List<ResultCertificateFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<ResultCertificateFileDto> files) {
        this.files = files;
    }

    public String getAwards_Type() {
        return awards_Type;
    }

    public void setAwards_Type(String awards_Type) {
        this.awards_Type = awards_Type;
    }

    public String getWin_Result_Name() {
        return win_Result_Name;
    }

    public void setWin_Result_Name(String win_Result_Name) {
        this.win_Result_Name = win_Result_Name;
    }

    public String getWin_People_Str() {
        return win_People_Str;
    }

    public void setWin_People_Str(String win_People_Str) {
        this.win_People_Str = win_People_Str;
    }

    public String getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(String end_Time) {
        this.end_Time = end_Time;
    }

    public String getBegin_Time() {
        return begin_Time;
    }

    public void setBegin_Time(String begin_Time) {
        this.begin_Time = begin_Time;
    }

    /**
     * 成果类型
     */
    private String awards_Type;

    /**
     * 获奖成果名称
     */
    private String win_Result_Name;

    /**
     * 证书编号
     */
    private String certif_No;

    /**
     获奖人
     */
    private List<ResultCertificatePeopleDto> people;



    /**
     * 获奖人String
     */
    private String win_People_Str;

    /**
     * 颁发单位
     */
    private String issuing_Unit;

    /**
     * 获奖成果选择1，2，3，
     */
    private String win_Result_type;

    /**
     * 获奖级别
     */
    private String win_Level;

    /**
     * 获奖等级
     */
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
    private Date win_Date;

    /**
     * 获奖日期string
     */
    private  String win_Date_Str;

    /**查询条件获得开始日期
     *
     */
    private String begin_Time;


    /**查询条件获得结束时间
     *
     */
    private String end_Time;

    /**
     * 是否合作成果。0：否，1：是；
     */
    private Boolean is_Cooperate_Result;

    public String getLevel_Awards() {
        return level_Awards;
    }

    public void setLevel_Awards(String level_Awards) {
        this.level_Awards = level_Awards;
    }

    /**
     * 第几获奖人
     */
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
    private String awards_Name;

    /**
     * 获奖成果分类
     */
    private String win_Result_Classify;
    /**
     附件
     */
    private List<ResultCertificateFileDto> files;

    /**
     * 附件退回id
     */
    private List<String> already_upFileId;

    public List<String> getAlready_upFileId() {
        return already_upFileId;
    }

    public void setAlready_upFileId(List<String> already_upFileId) {
        this.already_upFileId = already_upFileId;
    }

    public List<String> getAlready_upFileName() {
        return already_upFileName;
    }

    public void setAlready_upFileName(List<String> already_upFileName) {
        this.already_upFileName = already_upFileName;
    }

    /**
     * 附件退回name
     */
    private List<String> already_upFileName;

}
