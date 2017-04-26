package com.sunesoft.lemon.deanery.treatiseCG.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xubo on 2016/7/6 0006.
 * 论著实体类
 */
@Entity
@Table(name = "syy_oa_treatise")
public class Treatise extends BaseFormEntity {
    /**
     *论著名称
     */
    @Column(name = "treatise_name",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String treatise_Name;

    /**
     *发表/出版时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_time")
    private Date publish_Time;

    /**
     * 截止时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="publish_endtimes")
    private Date publish_EndTimes;

    /**
     *论著级别
     */
    @Column(name = "treatise_level",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String treatise_Level;

    /**
     *出版社/刊物名称
     */
    @Column(name = "treatise_press",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String treatise_Press;

    /**
     *是否核心。0：否，1：是；
     */
    @Column(name = "is_core")
    private Boolean is_Core;

    /**
     *发表/出版单位
     */
    @Column(name = "unit",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String unit;

    /**
     *书/期刊号
     */
    @Column(name = "make_no",
            columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String make_No;

    /**
     *是否合著。0；否，1：是;
     */
    @Column(name = "is_cooperate")
    private Boolean is_cooperate;

    /**
     * 获奖情况
     */
    @Column(name ="awards_detial")
    private String awards_Detial;

    /**
     * 授予单位
     */
    @Column(name ="grant_unit")
    private String grant_Unit;
    /**
     * 著作类型
     */
    @Column(name ="awards_type")
    private String awards_Type;
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

    public String getFirst_Author() {
        return first_Author;
    }

    public void setFirst_Author(String first_Author) {
        this.first_Author = first_Author;
    }

    /**
     * 著作人
     */
    @Column(name="first_author")
    private String first_Author;

    public String getLevel_Author() {
        return level_Author;
    }

    public void setLevel_Author(String level_Author) {
        this.level_Author = level_Author;
    }

    /**
     * 第几作者
     */
    @Column(name ="level_Author")
    private String level_Author;

/*    *//**
     * 文件
     *//*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatise_fileid")
    private List<TreatiseFile> files;
    public List<TreatiseFile> getFiles() {
        return files;
    }

    public void setFiles(List<TreatiseFile> files) {
        this.files = files;
    }*/
    public Treatise(){
        this.setIsActive(true);
        //this.files = new ArrayList<>();
        //this.setViewUrl("forms");
    }

    public String getTreatise_Name() {
        return treatise_Name;
    }

    public void setTreatise_Name(String treatise_Name) {
        this.treatise_Name = treatise_Name;
    }

    public Date getPublish_Time() {
        return publish_Time;
    }

    public void setPublish_Time(Date publish_Time) {
        this.publish_Time = publish_Time;
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

    public Date getPublish_EndTimes() {
        return publish_EndTimes;
    }

    public void setPublish_EndTimes(Date publish_EndTimes) {
        this.publish_EndTimes = publish_EndTimes;
    }

    public String getGrant_Unit() {
        return grant_Unit;
    }

    public void setGrant_Unit(String grant_Unit) {
        this.grant_Unit = grant_Unit;
    }

    public String getAwards_Detial() {
        return awards_Detial;
    }

    public void setAwards_Detial(String awards_Detial) {
        this.awards_Detial = awards_Detial;
    }

    public String getAwards_Type() {
        return awards_Type;
    }

    public void setAwards_Type(String awards_Type) {
        this.awards_Type = awards_Type;
    }
}
