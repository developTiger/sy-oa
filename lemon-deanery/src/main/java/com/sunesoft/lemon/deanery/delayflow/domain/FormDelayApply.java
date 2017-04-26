package com.sunesoft.lemon.deanery.delayflow.domain;

import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/8/25.
 * editer by pxj on 2016/9/8
 */
@Entity
@Table(name="syy_oa_form_delay_apply")
public class FormDelayApply extends BaseFormEntity{
    /**
     * 基层单位领导审批
     */
    private String leaderWord;
    /**
     * 专业类别
     */
    private String majorType;
    /**
     * 项目编号
     */
    @Column(name = "project_no",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String projectNo;

    /**
     * 项目名称
     */
    @Column(name = "project_name_ys",columnDefinition = "varchar2(35)")
    private String delayNamesel;

    /*
    * 项目名称
    * */
    private String delayname2;

    /**
     * 附件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 附件name
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * delayName 为开题报告的formNo
     */
    private String delayName;
    private String delayReason;

    private Date delayTimes;
    private String delayTimes_str;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "form_delay_id")
    private List<FormOpenProjectFile> formOpenProjectFiles;

    public List<FormOpenProjectFile> getFormOpenProjectFiles() {
        return formOpenProjectFiles;
    }

    public void setFormOpenProjectFiles(List<FormOpenProjectFile> formOpenProjectFiles) {
        this.formOpenProjectFiles = formOpenProjectFiles;
    }

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

   /* public Date getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(Date delayTimes) {
        this.delayTimes = delayTimes;
    }*/

    public FormDelayApply(String delayName, String delayReason, Date delayTimes) {
        this.delayName = delayName;
        this.delayReason = delayReason;
        this.delayTimes = delayTimes;
        this.setIsActive(true);
    }

    public FormDelayApply(){
        this.setIsActive(true);
    }

    public String getOpinion3() {
        return opinion3;
    }

    public void setOpinion3(String opinion3) {
        this.opinion3 = opinion3;
    }

    public Date getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(Date delayTimes) {
        this.delayTimes = delayTimes;
    }

    public String getDelayTimes_str() {
        return delayTimes_str;
    }

    public void setDelayTimes_str(String delayTimes_str) {
        this.delayTimes_str = delayTimes_str;
    }

    public String getLeaderWord() {
        return leaderWord;
    }

    public void setLeaderWord(String leaderWord) {
        this.leaderWord = leaderWord;
    }
    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }
    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getDelayname2() {
        return delayname2;
    }

    public void setDelayname2(String delayname2) {
        this.delayname2 = delayname2;
    }

    public String getDelayNamesel() {
        return delayNamesel;
    }

    public void setDelayNamesel(String delayNamesel) {
        this.delayNamesel = delayNamesel;
    }

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
}
