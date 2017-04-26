package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/29.
 */
public class DeptAppraiseDto extends BaseFormDto {

    private Long appraisYear;

    private String appraisTitle;

    private String description;

    private Date startDate;

    private Date endDate;

    private List<DeptAppraiseDetailDto> detailDtos;


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

    public List<DeptAppraiseDetailDto> getDetailDtos() {
        return detailDtos;
    }

    public void setDetailDtos(List<DeptAppraiseDetailDto> detailDtos) {
        this.detailDtos = detailDtos;
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
