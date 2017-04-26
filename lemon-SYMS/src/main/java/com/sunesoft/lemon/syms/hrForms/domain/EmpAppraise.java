package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/26.
 */
@Entity
@Table(name = "syy_oa_emp_appraise")
public class EmpAppraise extends BaseFormEntity {

    private Long appraisYear;

    private String appraisTitle;

    /**
     * 考核内容字段长度限定200，
     */
    @Column(name = "description",columnDefinition = "CLOB DEFAULT NULL")
    private String description;

    @Column(name = "start_date")
    private Date beginDate;
    @Column(name = "end_date")
    private Date endDate;
    @OneToMany( fetch = FetchType.LAZY, cascade = {
            CascadeType.ALL},mappedBy = "empAppraise")
    private List<EmpAppraiseDetail> details;
    public EmpAppraise(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
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

    public List<EmpAppraiseDetail> getDetails() {
        return details;
    }

    public void setDetails(List<EmpAppraiseDetail> details) {
        this.details = details;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
