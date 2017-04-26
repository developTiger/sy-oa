package com.sunesoft.lemon.deanery.treatiseCG.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 * edit by pxj
 */
public class TreatiseCriteria extends PagedCriteria{
    /**
     * 第一著作人
     */
    private String first_Author;

    private String treatise_Name;

    private Date publish_Time;

    private String treatise_Level;

    private String treatise_Press;

    private Boolean is_Core;

    private String unit;

    private String make_No;

    private Boolean is_cooperate;

    private Date begin_Date;

    private Date end_Date;


    public String getTreatise_Name() {
        return treatise_Name;
    }

    public void setTreatise_Name(String treatise_Name) {
        this.treatise_Name = treatise_Name;
    }


    public String getTreatise_Level() {
        return treatise_Level;
    }

    public void setTreatise_Level(String treatise_Level) {
        this.treatise_Level = treatise_Level;
    }

    public String getTreatise_Press() {
        return treatise_Press;
    }

    public void setTreatise_Press(String treatise_Press) {
        this.treatise_Press = treatise_Press;
    }

    public Boolean getIs_Core() {
        return is_Core;
    }

    public void setIs_Core(Boolean is_Core) {
        this.is_Core = is_Core;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMake_No() {
        return make_No;
    }

    public void setMake_No(String make_No) {
        this.make_No = make_No;
    }

    public Boolean getIs_cooperate() {
        return is_cooperate;
    }

    public void setIs_cooperate(Boolean is_cooperate) {
        this.is_cooperate = is_cooperate;
    }

    public Date getPublish_Time() {
        return publish_Time;
    }

    public void setPublish_Time(Date publish_Time) {
        this.publish_Time = publish_Time;
    }

    public Date getBegin_Date() {
        return begin_Date;
    }

    public void setBegin_Date(Date begin_Date) {
        this.begin_Date = begin_Date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }
    public String getFirst_Author() {
        return first_Author;
    }

    public void setFirst_Author(String first_Author) {
        this.first_Author = first_Author;
    }
}
