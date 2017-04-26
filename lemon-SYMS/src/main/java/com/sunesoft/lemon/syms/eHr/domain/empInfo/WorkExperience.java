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
@Table(name="syy_oa_hr_work_exp")
public class WorkExperience extends BaseEntity {

    /**
     * 是否是当前工作：false不是，true代表是
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    /**
     * 就职时间
     */
    @Column(name = "start_time")
    private Date strartTime;

    /**
     * 辞职时间
     */
    @Column(name = "over_time")
    private Date overTime;

    /**
     * 就职单位
     */
    private String company;

    /**
     * 工作名称
     */
    @Column(name = "work_name")
    private String workName;

    /**
     * 工作地点
     */
    @Column(name = "work_place")
    private String workPlace;

    /**
     * 工作简介
     */
    private String breif;

    /**
     * 员工id
     */
    @Column(name = "emp_id")
    private Long empId;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public WorkExperience() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getStrartTime() {
        return strartTime;
    }

    public void setStrartTime(Date strartTime) {
        this.strartTime = strartTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getBreif() {
        return breif;
    }

    public void setBreif(String breif) {
        this.breif = breif;
    }
}
