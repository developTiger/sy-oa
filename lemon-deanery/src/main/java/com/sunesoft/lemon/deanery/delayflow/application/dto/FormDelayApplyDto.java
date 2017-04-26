package com.sunesoft.lemon.deanery.delayflow.application.dto;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by swb on 2016/8/25.
 */
public class FormDelayApplyDto extends BaseFormDto{
    /**
     * 基层单位领导审批
     */
    private String leaderWord;

    public String getLeaderWord() {
        return leaderWord;
    }

    public void setLeaderWord(String leaderWord) {
        this.leaderWord = leaderWord;
    }

    /**
     * 专业类别
     */
    private String majorType;

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    private String delayName;
    private String delayReason;
    private Date delayTimes;
    private String delayTime;

    private String projectNo;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    /**
     * 项目名称
     */
    private String delayNamesel;
/*
* 项目名称
* */
    private String delayname2;

    public String getDelayNamesel() {
        return delayNamesel;
    }

    public void setDelayNamesel(String delayNamesel) {
        this.delayNamesel = delayNamesel;
    }

    /**
     * 附件id
     */
    private String fileId;

    /**
     * 附件name
     */
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 审核第一步意见
     */
    private String opinion;
    /**
     * 审核第二步意见
     */
    private String opinion1;
    /**
     * 审核第三步意见
     */
    private String opinion2;
    /**
     * 审核第四步意见
     */
    private String opinion3;
    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOpinion1() {
        return opinion1;
    }

    public void setOpinion1(String opinion1) {
        this.opinion1 = opinion1;
    }

    public String getOpinion2() {
        return opinion2;
    }

    public void setOpinion2(String opinion2) {
        this.opinion2 = opinion2;
    }

    public String getDelayName() {
        return delayName;
    }

    public void setDelayName(String delayName) {
        this.delayName = delayName;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public Date  getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(Date delayTimes) {
        this.delayTimes = delayTimes;
    }

    public String getOpinion3() {
        return opinion3;
    }

    public void setOpinion3(String opinion3) {
        this.opinion3 = opinion3;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }



    public String getDelayname2() {
        return delayname2;
    }

    public void setDelayname2(String delayname2) {
        this.delayname2 = delayname2;
    }
}
