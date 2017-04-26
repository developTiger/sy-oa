package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhouz on 2016/6/14.
 */
@Entity
@Table(name = "syy_oa_hr_education")
public class Education extends BaseEntity {



    /**
     * 是否是当前学历：false代表不是，true代表是
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    /**
     * 毕业学校
     */
    private String school;

    /**
     * 学习形式
     */
    @Column(name = "study_form")
    private String studyForm;

    /**
     * 毕业时间
     */
    private Date graduation;

    /**
     * 学习专业
     */
    @Column(name = "edu_major")
    private String major;

    /**
     * 文化程度
     */
    @Column(name = "edu_level")
    private String level;

    /**
     * 学位
     */
    @Column(name = "edu_degree")

    private String degree;

    /**
     * 获取时间
     */
    private Date obtain;

    /**
     * 获取的途径
     */
    @Column(name = "edu_getway")
    private String way;

    /**
     * 简介
     */
    private String brief;

    /**
     * 员工id
     */
    @Column(name = "emp_id")
    private Long empId;

    public Education() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
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

    public Date getGraduation() {
        return graduation;
    }

    public void setGraduation(Date graduation) {
        this.graduation = graduation;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Date getObtain() {
        return obtain;
    }

    public void setObtain(Date obtain) {
        this.obtain = obtain;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
