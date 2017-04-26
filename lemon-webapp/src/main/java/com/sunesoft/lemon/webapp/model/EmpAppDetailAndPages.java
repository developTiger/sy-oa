package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpAppraiseDto;

/**
 * Created by admin on 2016/10/15.
 */
public class EmpAppDetailAndPages {

    private FormEmpAppraiseDto formEmpAppraiseDto;

    private Boolean isViewOnly;
    private PagedResult<EmpAppraiseDetailDto> pagedResult;

    public FormEmpAppraiseDto getFormEmpAppraiseDto() {
        return formEmpAppraiseDto;
    }

    public void setFormEmpAppraiseDto(FormEmpAppraiseDto formEmpAppraiseDto) {
        this.formEmpAppraiseDto = formEmpAppraiseDto;
    }

    public PagedResult<EmpAppraiseDetailDto> getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PagedResult<EmpAppraiseDetailDto> pagedResult) {
        this.pagedResult = pagedResult;
    }

    public Boolean getIsViewOnly() {
        return isViewOnly;
    }

    public void setIsViewOnly(Boolean isViewOnly) {
        this.isViewOnly = isViewOnly;
    }
}
