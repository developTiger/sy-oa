package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 职称信息
 * Created by zhouz on 2016/6/14.
 */
@Entity
@Table(name = "syy_oa_hr_tech_pos")
public class TechPosition extends BaseEntity {

    /**
     * 是否是当前职称
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    /**
     * 聘任时间
     */
    @Column(name = "in_time")
    private Date inTime;

    /**
     * 卸任时间
     */
    @Column(name = "out_time")
    private Date outTime;

    /**
     * 资格专业
     */
    private String major;

    /**
     * 职称名
     */
    @Column(name = "pos_name")
    private String name;

    /**
     * 资格级别:高，中，初
     */
    @Column(name = "pos_level")
    private String level;

    /**
     * 获取时间
     */
    @Column(name = "in_obtian")
    private Date inObtian;

    /**
     * 获取途径
     */
    @Column(name = "get_way")
    private String way;

    /**
     * 专业技术职务信息介绍
     */
    private String brief;

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


    public TechPosition() {
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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getInObtian() {
        return inObtian;
    }

    public void setInObtian(Date inObtian) {
        this.inObtian = inObtian;
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
