package com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;

import java.util.List;

/**
 * Created by temp on 2016/8/2.
 */
public interface FormAppraiseDetailsService {
    CommonResult addOrUpdateFormAppraiseDetails(EmpAppraiseDetailDto empAppraiseDetailDto);
    EmpAppraiseDetailDto getByid(Long id);

    List<EmpAppraiseDetailDto> getByEmpAndStatus(Long empId,Integer status);


}
