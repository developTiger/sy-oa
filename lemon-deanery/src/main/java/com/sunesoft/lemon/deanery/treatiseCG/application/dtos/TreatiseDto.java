package com.sunesoft.lemon.deanery.treatiseCG.application.dtos;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFile;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public class TreatiseDto extends BaseFormDto {
    /**
     * 文件Dto
     */
    private List<TreatiseFileDto> filesDto;

    /**
     * 数据库文件表list
     * @return
     */
    private List<TreatiseFile> treatisesFiles;



    private String info;

    /**
     *论著名称
     */
    private String treatise_Name;

    /**
     *发表/出版时间
     */
    private String publish_Time_;


    /**
     *发表/出版时间对应到数据库
     */
    private Date publish_Time;

    /**
     * 截止时间
     */
    private String publish_EndTime;



    /**
     * 截止时间对应数据库类
     */
    private Date publish_EndTimes;
    /**
     *论著级别
     */
    private String treatise_Level;

    /**
     *出版社/刊物名称
     */
    private String treatise_Press;

    /**
     *是否核心。0：否，1：是；
     */
    private Boolean is_Core;

    /**
     *发表/出版单位
     */
    private String unit;

    /**
     *书/期刊号
     */
    private String make_No;

    /**
     *是否合著。0；否，1：是;
     */
    private Boolean is_cooperate;

    /**
     * 著作人合集
     */
    private String winner_info;

    /**
     * 获奖情况
     */
    private String awards_Detial;

    /**
     * 授予单位
     */
    private String grant_Unit;
    /**
     * 著作类型
     */
    private String awards_Type;

    /*
* 基层领导意见
* */
    private String leaderOpinion;

    /*
    * 成果负责部门审核
    * */
    private String deptOpinion;

    private List<Prizewinner> listPrizes;

    public String getFirst_Author() {
        return first_Author;
    }

    public void setFirst_Author(String first_Author) {
        this.first_Author = first_Author;
    }

    /**
     * 第一负责人
     * @return
     */
    private String   first_Author;

    public String getLevel_Author() {
        return level_Author;
    }

    public void setLevel_Author(String level_Author) {
        this.level_Author = level_Author;
    }

    /**
     * 第几作者
     */
    private String level_Author;

    /**
     * 退回fileid
     * @return
     */
    private List<String> already_upFileId;

    public List<String> getAlready_upFileName() {
        return already_upFileName;
    }

    public void setAlready_upFileName(List<String> already_upFileName) {
        this.already_upFileName = already_upFileName;
    }

    public List<String> getAlready_upFileId() {
        return already_upFileId;
    }

    public void setAlready_upFileId(List<String> already_upFileId) {
        this.already_upFileId = already_upFileId;
    }

    /**
     * 退回fileName
     * @return
     */
     private List<String> already_upFileName;

    public List<TreatiseFileDto> getFilesDto() {
        return filesDto;
    }

    public void setFilesDto(List<TreatiseFileDto> filesDto) {
        this.filesDto = filesDto;
    }

    public List<TreatiseFile> getTreatisesFiles() {
        return treatisesFiles;
    }

    public void setTreatisesFiles(List<TreatiseFile> treatisesFiles) {
        this.treatisesFiles = treatisesFiles;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAwards_Detial() {
        return awards_Detial;
    }

    public void setAwards_Detial(String awards_Detial) {
        this.awards_Detial = awards_Detial;
    }

    public String getPublish_EndTime() {
        return publish_EndTime;
    }

    public void setPublish_EndTime(String publish_EndTime) {
        this.publish_EndTime = publish_EndTime;
    }

    public String getGrant_Unit() {
        return grant_Unit;
    }

    public void setGrant_Unit(String grant_Unit) {
        this.grant_Unit = grant_Unit;
    }

    public String getAwards_Type() {
        return awards_Type;
    }

    public void setAwards_Type(String awards_Type) {
        this.awards_Type = awards_Type;
    }



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

    public String getPublish_Time_() {
        return publish_Time_;
    }

    public void setPublish_Time_(String publish_Time_) {
        this.publish_Time_ = publish_Time_;
    }


    public List<Prizewinner> getListPrizes() {
        return listPrizes;
    }

    public void setListPrizes(List<Prizewinner> listPrizes) {
        this.listPrizes = listPrizes;
    }


    public String getWinner_info() {
        return winner_info;
    }

    public void setWinner_info(String winner_info) {
        this.winner_info = winner_info;
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


    public Date getPublish_Time() {
        return publish_Time;
    }

    public void setPublish_Time(Date publish_Time) {
        this.publish_Time = publish_Time;
    }
}
