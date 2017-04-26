package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpSubAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpSubAppraiseDto;

import java.util.List;

/**
 * Created by temp on 2016/8/2.
 */
public class FromSubEmpAppraiseDetail {

    private FormEmpSubAppraiseDto formEmpSubAppraiseDto;
    private EmpSubAppraiseDetailDto empSubAppraiseDetailDto;
    private List<EmpDto> listEmp;
    private List<EmpSubAppraiseDetailDto> listEmpSubAppraise;

    public List<EmpSubAppraiseDetailDto> getListEmpSubAppraise() {
        return listEmpSubAppraise;
    }

    public void setListEmpSubAppraise(List<EmpSubAppraiseDetailDto> listEmpSubAppraise) {
        this.listEmpSubAppraise = listEmpSubAppraise;
    }

    public List<EmpDto> getListEmp() {
        return listEmp;
    }

    public void setListEmp(List<EmpDto> listEmp) {
        this.listEmp = listEmp;
    }

    public FormEmpSubAppraiseDto getFormEmpSubAppraiseDto() {
        return formEmpSubAppraiseDto;
    }

    public void setFormEmpSubAppraiseDto(FormEmpSubAppraiseDto formEmpSubAppraiseDto) {
        this.formEmpSubAppraiseDto = formEmpSubAppraiseDto;
    }

    public EmpSubAppraiseDetailDto getEmpSubAppraiseDetailDto() {
        return empSubAppraiseDetailDto;
    }

    public void setEmpSubAppraiseDetailDto(EmpSubAppraiseDetailDto empSubAppraiseDetailDto) {
        this.empSubAppraiseDetailDto = empSubAppraiseDetailDto;
    }
}
