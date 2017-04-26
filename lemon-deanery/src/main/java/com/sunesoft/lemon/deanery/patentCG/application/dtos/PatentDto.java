package com.sunesoft.lemon.deanery.patentCG.application.dtos;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;
import java.util.List;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public class PatentDto extends BaseFormDto {

     //ID

    //private Long id;

    private String winner_info;
     //解析字符串
     private String info;
     private String patent_Name;

     private String app_No;

     private String patent_Type;

     private Date apply_Date;

     private Date valid_Date;

     private List<Prizewinner> listPrizers;
    //页面显示文本日期
     private String apply_Date_Str;
    //页面显示文本日期
     private String valid_Date_Str;
    public List<Prizewinner> getListPrizers() {
        return listPrizers;
    }

    public void setListPrizers(List<Prizewinner> listPrizers) {
        this.listPrizers = listPrizers;
    }



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

    public Date getValid_Date() {
        return valid_Date;
    }

    public void setValid_Date(Date valid_Date) {
        this.valid_Date = valid_Date;


    }


    public String getWinner_info() {
        return winner_info;
    }

    public void setWinner_info(String winner_info) {
        this.winner_info = winner_info;
    }

    public String getApply_Date_Str() {
        return apply_Date_Str;
    }

    public void setApply_Date_Str(String apply_Date_Str) {
        this.apply_Date_Str = apply_Date_Str;
    }

    public String getValid_Date_Str() {
        return valid_Date_Str;
    }

    public void setValid_Date_Str(String valid_Date_Str) {
        this.valid_Date_Str = valid_Date_Str;
    }
}
