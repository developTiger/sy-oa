package com.sunesoft.lemon.deanery.patentCG.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public class PatentCriteria extends PagedCriteria {

    private String patent_Name;

    private String app_No;

    private String patent_Type;

    private Date apply_Date;

    private Date beginDate;

    private Date endDate;

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPatent_Name() {
        return patent_Name;
    }

    public void setPatent_Name(String patent_Name) {
        this.patent_Name = patent_Name;
    }

    public String getApp_No() {
        return app_No;
    }

    public void setApp_No(String app_No) {
        this.app_No = app_No;
    }

    public String getPatent_Type() {
        return patent_Type;
    }

    public void setPatent_Type(String patent_Type) {
        this.patent_Type = patent_Type;
    }

    public Date getApply_Date() {
        return apply_Date;
    }

    public void setApply_Date(Date apply_Date) {
        this.apply_Date = apply_Date;
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
}
