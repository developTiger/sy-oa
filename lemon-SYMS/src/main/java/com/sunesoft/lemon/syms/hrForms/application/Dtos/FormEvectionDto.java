package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public class FormEvectionDto  extends BaseFormDto {

    //出差理由
    private String reason;
    //出差目的
    private String target;
    //截止时间
    private Date toTime;
    //出差日期
    private Date evectionTime;
    //历时多久
    private float countTime;
    //出差类别
    private String category;
    //出差属性
    private String evecAttr;
    //任务来源
    private String taskSource;
    //任务内容
    private String taskContent;

    private Date applyDate;

    private String currentPointName;

    private String currentRemark;

    private String evectionNumber;

    private Integer year;

    private String strFormNo;

    private Boolean printFlag;

    private Integer numberNo;

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Date getEvectionTime() {
        return evectionTime;
    }

    public void setEvectionTime(Date evectionTime) {
        this.evectionTime = evectionTime;
    }

    public float getCountTime() {
        return countTime;
    }

    public void setCountTime(float countTime) {
        this.countTime = countTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEvecAttr() {
        return evecAttr;
    }

    public void setEvecAttr(String evecAttr) {
        this.evecAttr = evecAttr;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getEvectionNumber() {
        return evectionNumber;
    }

    public void setEvectionNumber(String evectionNumber) {
        this.evectionNumber = evectionNumber;
    }

    public String getCurrentPointName() {
        return currentPointName;
    }

    public void setCurrentPointName(String currentPointName) {
        this.currentPointName = currentPointName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCurrentRemark() {
        return currentRemark;
    }

    public void setCurrentRemark(String currentRemark) {
        this.currentRemark = currentRemark;
    }

    public String getStrFormNo() {
        return strFormNo;
    }

    public void setStrFormNo(String strFormNo) {
        this.strFormNo = strFormNo;
    }

    public Boolean getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Boolean printFlag) {
        this.printFlag = printFlag;
    }

    public Integer getNumberNo() {
        return numberNo;
    }

    public void setNumberNo(Integer numberNo) {
        this.numberNo = numberNo;
    }
}
