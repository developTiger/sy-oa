package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpSubAppraiseDetailDto;

import java.util.List;

/**
 * Created by temp on 2016/8/11.
 */
public class EmpSubAppraiseList {

    private List<EmpSubAppraiseDetailDto> empSubAppraiseList;

    public List<EmpSubAppraiseDetailDto> getEmpSubAppraiseList() {
        return empSubAppraiseList;
    }

    public void setEmpSubAppraiseList(List<EmpSubAppraiseDetailDto> empSubAppraiseList) {
        this.empSubAppraiseList = empSubAppraiseList;
    }
}
