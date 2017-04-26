package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public class FormTrainChooseEmpDto extends BaseFormDto {

    /**
     * 该字段判断主表单流程是否全部结束
     */
    private String formTrainIsComplete;

    /**
     * 发布培训新闻通知的id
     */
    private String publishTrainNewsId;

    /**
     * 发布培训新闻通知的name
     */
    private String publishTrainNewsName;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件id
     */
    private String fileId;

    //培训名称
    private String trainName;
    //培训时间
//    private Date trainDate;
    //培训开始时间
    private Date trainBeginDate;
    //培训结束时间
    private Date trainEndDate;
    //培训地点
    private String trainPlace;
    //培训内容
    private String trainContent;

    private List<Long> empids;

    private List<EmpEasyDto> empLists;

    /**
     *学时
     */
    private String studyTime;

    /**
     * 主办单位
     */
    private String mainCompany;

    /**
     * 承办单位
     */
    private String didCompany;

    /**
     * 培训分类
     */
    private String trainCategory;

    /**
     * 计划内或外
     */
    private String plan;

    public String getFormTrainIsComplete() {
        return formTrainIsComplete;
    }

    public void setFormTrainIsComplete(String formTrainIsComplete) {
        this.formTrainIsComplete = formTrainIsComplete;
    }

    public String getPublishTrainNewsId() {
        return publishTrainNewsId;
    }

    public void setPublishTrainNewsId(String publishTrainNewsId) {
        this.publishTrainNewsId = publishTrainNewsId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }



//    public Date getTrainDate() {
//        return trainDate;
//    }
//
//    public void setTrainDate(Date trainDate) {
//        this.trainDate = trainDate;
//    }


    public String getPublishTrainNewsName() {
        return publishTrainNewsName;
    }

    public void setPublishTrainNewsName(String publishTrainNewsName) {
        this.publishTrainNewsName = publishTrainNewsName;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public List<EmpEasyDto> getEmpLists() {
        return empLists;
    }

    public void setEmpLists(List<EmpEasyDto> empLists) {
        this.empLists = empLists;
    }

    public List<Long> getEmpids() {
        return empids;
    }

    public void setEmpids(List<Long> empids) {
        this.empids = empids;
    }

    public Date getTrainEndDate() {
        return trainEndDate;
    }

    public void setTrainEndDate(Date trainEndDate) {
        this.trainEndDate = trainEndDate;
    }

    public Date getTrainBeginDate() {
        return trainBeginDate;
    }

    public void setTrainBeginDate(Date trainBeginDate) {
        this.trainBeginDate = trainBeginDate;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getMainCompany() {
        return mainCompany;
    }

    public void setMainCompany(String mainCompany) {
        this.mainCompany = mainCompany;
    }

    public String getDidCompany() {
        return didCompany;
    }

    public void setDidCompany(String didCompany) {
        this.didCompany = didCompany;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
