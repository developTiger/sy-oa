package com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpSubAppraiseDetailDto;

import java.util.List;

/**
 * Created by temp on 2016/8/2.
 */
public interface FormEmpSubAppraiseDetailsService {

    CommonResult addOrUpdateFormAppraiseDetails(EmpSubAppraiseDetailDto empSubAppraiseDetailDto);

/*    EmpSubAppraiseDetailDto getEmpSubAppraiseDetails(Long formNo);

    EmpSubAppraiseDetailDto ByEmpIDgetEmpSubAppraiseDetails(Long id);*/

    List<EmpSubAppraiseDetailDto> getEmpSubAppraiseDetailsByParentFormNo(Long formNo,Long blongDeptId);


}
