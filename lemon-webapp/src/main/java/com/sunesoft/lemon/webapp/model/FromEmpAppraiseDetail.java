package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpSubAppraiseDto;

import java.util.List;

/**
 * Created by temp on 2016/8/2.
 */
public class FromEmpAppraiseDetail {
    private FormEmpAppraiseDto formEmpAppraiseDto;
    List<FormEmpSubAppraiseDto> empSubAppraiseDtoList;

    public FormEmpAppraiseDto getFormEmpAppraiseDto() {
        return formEmpAppraiseDto;
    }

    public void setFormEmpAppraiseDto(FormEmpAppraiseDto formEmpAppraiseDto) {
        this.formEmpAppraiseDto = formEmpAppraiseDto;
    }

    public List<FormEmpSubAppraiseDto> getEmpSubAppraiseDtoList() {
        return empSubAppraiseDtoList;
    }

    public void setEmpSubAppraiseDtoList(List<FormEmpSubAppraiseDto> empSubAppraiseDtoList) {
        this.empSubAppraiseDtoList = empSubAppraiseDtoList;
    }
}
