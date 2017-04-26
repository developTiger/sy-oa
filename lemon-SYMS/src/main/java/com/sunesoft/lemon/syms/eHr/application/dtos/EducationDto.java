package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by xiazl on 2016/6/15.
 */
public class EducationDto {

    private Long empId;

    private Long id;
    /**
     * 是否是当前学历：false代表不是，true代表是
     */
    private Boolean isCurrent;

    /**
     * 毕业学校
     */
    private String school;

    /**
     * 学习形式
     */
    private String studyForm;

    /**
     * 毕业时间
     */
    private String strGraduation;

    /**
     * 学习专业
     */
    private String major;

    /**
     * 文化程度
     */
    private String level;

    /**
     * 学位
     */
    private String degree;

    /**
     * 已经改为 入学时间
     */
    private String strObtain;


    /**
     * 获取的途径
     */
    private String way;

    /**
     * 简介
     */
    private String brief;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStrGraduation() {
        return strGraduation;
    }

    public void setStrGraduation(String strGraduation) {
        this.strGraduation = strGraduation;
    }

    public String getStrObtain() {
        return strObtain;
    }

    public void setStrObtain(String strObtain) {
        this.strObtain = strObtain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }


}


