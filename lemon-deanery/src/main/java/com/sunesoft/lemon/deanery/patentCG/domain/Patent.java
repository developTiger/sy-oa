package com.sunesoft.lemon.deanery.patentCG.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 * 专利实体类
 */
@Entity
@Table(name = "syy_oa_patent")
public class Patent extends BaseFormEntity{

    /**
     *专利名称
     */
    @Column(name = "patent_name",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String patent_Name;

    /**
     *专利编号
     */
    @Column(name = "app_no",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String app_No;

    /**
     *专利类型
     */
    @Column(name = "patent_type",
            columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String patent_Type;

    /**
     *专利申请日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_dates")
    private Date apply_Date;

    /**
     *专利有效日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_date")
    private Date valid_Date;


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

    public Date getValid_Date() {
        return valid_Date;
    }

    public void setValid_Date(Date valid_Date) {
        this.valid_Date = valid_Date;
    }

    public Patent(){
        this.setIsActive(true);
    }
}


