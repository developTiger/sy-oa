package com.sunesoft.lemon.syms.hrForms.application.formDeptAppraise;


import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.DeptAppraise;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

public interface FormDeptAppraiseService extends FormService<DeptAppraise,DeptAppraiseDto> {


    public CommonResult downLoadDate(Long formNo);

    CommonResult updateByList(Long formNo, List<DeptAppraiseDetailDto> dtos);

}
