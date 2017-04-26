package com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise;


import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpSubAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseDetail;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

public interface FormAppraiseService extends FormService<EmpAppraise,FormEmpAppraiseDto> {

    /*
     *
     *查询子表单部门员工信息
     */
    List<FormEmpSubAppraiseDto> getAllEmpSubAppraises(Long parentFormNo);


    FormEmpAppraiseDto getEmpAppDetailDtoPages(Long formNo);

    public PagedResult<EmpAppraiseDetailDto> getPagesByAllDetails(Long formNo,EmpCriteria empCriteria);

    public CommonResult updateByList(Long formNo, List<EmpAppraiseDetailDto> dtos);




}
