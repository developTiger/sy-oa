package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public class FormBusinessDownloadDto {

    //出差日期
    private String evectionTime;
    //截止时间
    private String toTime;
    //申请人
    private String applyerName;
    //出差目的
    private String target;

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

    public String getEvectionTime() {
        return evectionTime;
    }

    public void setEvectionTime(String evectionTime) {
        this.evectionTime = evectionTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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
}
