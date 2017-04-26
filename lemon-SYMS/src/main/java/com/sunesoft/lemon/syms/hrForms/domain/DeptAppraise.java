package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/26.
 */
@Entity
@Table(name = "syy_oa_dept_appraise")
public class DeptAppraise extends BaseFormEntity {

    @Column(name = "appraise_year")
    private Long appraisYear;

    @Column(name = "apprais_title", columnDefinition = "varchar(100) DEFAULT NULL")
    private String appraisTitle;
    @Lob
    @Column(name = "description2", columnDefinition = "CLOB", nullable = true)
    private String description;

    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "dept_appraise_id")
    private List<DeptAppraiseDetail> details;

    public DeptAppraise() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.details = new ArrayList<>();
        this.setViewUrl("forms");
    }

    public Long getAppraisYear() {
        return appraisYear;
    }

    public void setAppraisYear(Long appraisYear) {
        this.appraisYear = appraisYear;
    }

    public String getAppraisTitle() {
        return appraisTitle;
    }

    public void setAppraisTitle(String appraisTitle) {
        this.appraisTitle = appraisTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeptAppraiseDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DeptAppraiseDetail> details) {
        this.details = details;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
