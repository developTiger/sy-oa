package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public class FormEmpAppraiseDto extends BaseFormDto {

    /*
     *年度考核年份
     */
    private Long appraisYear;

    /*
     * 考核标题
     */
    private String appraisTitle;

    /*
     *考核描述
     */
    private String description;

    /*
     *考核开始时间
     */
    private Date beginDate;

    /*
     *结束时间
     */
    private Date endDate;

    List<EmpAppraiseDetailDto> detailsDtos;

    private PagedResult<EmpAppraiseDetailDto> pageDetailDtos;

    public PagedResult<EmpAppraiseDetailDto> getPageDetailDtos() {
        return pageDetailDtos;
    }

    public void setPageDetailDtos(PagedResult<EmpAppraiseDetailDto> pageDetailDtos) {
        this.pageDetailDtos = pageDetailDtos;
    }

    public List<EmpAppraiseDetailDto> getDetailsDtos() {
        return detailsDtos;
    }

    public void setDetailsDtos(List<EmpAppraiseDetailDto> detailsDtos) {
        this.detailsDtos = detailsDtos;
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
