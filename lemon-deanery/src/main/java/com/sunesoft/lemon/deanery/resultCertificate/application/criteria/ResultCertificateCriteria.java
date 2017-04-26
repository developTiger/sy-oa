package com.sunesoft.lemon.deanery.resultCertificate.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by pxj on 2016/9/28.
 */
public class ResultCertificateCriteria extends PagedCriteria{
    public String getWin_Result_Name() {
        return win_Result_Name;
    }

    public void setWin_Result_Name(String win_Result_Name) {
        this.win_Result_Name = win_Result_Name;
    }

    public String getWin_Level() {
        return win_Level;
    }

    public void setWin_Level(String win_Level) {
        this.win_Level = win_Level;
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

    public String getAwards_Type() {
        return awards_Type;
    }

    public void setAwards_Type(String awards_Type) {
        this.awards_Type = awards_Type;
    }

    public String getCertif_No() {
        return certif_No;
    }

    public void setCertif_No(String certif_No) {
        this.certif_No = certif_No;
    }

    /**
     * 获奖成果名称
     */
    private String win_Result_Name;

    /**
     * 证书编号
     */
    private String certif_No;

    /**
     * 成果类型
     */
    private String awards_Type;

    /**查询条件获得开始日期
     *
     */
    private String begin_Time;


    /**查询条件获得结束时间
     *
     */
    private String end_Time;

    /**
     * 获奖级别
     */
    private String win_Level;




}
