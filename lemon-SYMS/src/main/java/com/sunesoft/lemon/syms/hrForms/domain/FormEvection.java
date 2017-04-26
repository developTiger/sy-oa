package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jiangkefan on 2016/7/19.
 */
@Entity
@Table(name = "syy_oa_form_evection")
public class FormEvection extends BaseFormEntity {
    /**
     * 出差类型
     */
    @Enumerated(EnumType.STRING)
    private AttendanceKind evectionType;
    /**
     * 出差日期
     */
    @Column(name="evection_time")
    private Date evectionTime;

    /**
     * 出差事由
     */
    private String reason;

    /**
     * 出差目的
     */
    private String target;

    /**
     * 截止时间
     */
    @Column(name="to_time")
    private Date toTime;

    /**
     * 历时多久
     */
    @Column(name = "count_time")
    private Float countTime;//小时，如1.5小时

    /**
     * 出差类别
     */
    private String category;

    /**
     * 出差属性
     */
    private String evecAttr;


    //任务来源
    private String taskSource;

    //任务内容
    private String taskContent;

    private Boolean printFlag;

    private Integer numberNo;

    public FormEvection(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.printFlag=false;
    }

    public Integer getNumberNo() {
        return numberNo;
    }

    public void setNumberNo(Integer numberNo) {
        this.numberNo = numberNo;
    }

    public Boolean getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Boolean printFlag) {
        this.printFlag = printFlag;
    }

    public AttendanceKind getEvectionType() {
        return evectionType;
    }

    public void setEvectionType(AttendanceKind evectionType) {
        this.evectionType = evectionType;
    }

    public Date getEvectionTime() {
        return evectionTime;
    }

    public void setEvectionTime(Date evectionTime) {
        this.evectionTime = evectionTime;
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

    public Float getCountTime() {
        return countTime;
    }

    public void setCountTime(Float countTime) {
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
