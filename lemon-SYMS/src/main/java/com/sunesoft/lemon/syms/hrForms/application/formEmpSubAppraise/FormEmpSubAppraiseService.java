package com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise;


import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpSubAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraise;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

public interface FormEmpSubAppraiseService extends FormService<EmpSubAppraise,FormEmpSubAppraiseDto> {

    public List<EmpAppraiseDetailDto> getEmpAppraiseDetailDtos(Long parentFormNo, Long dept);

}
