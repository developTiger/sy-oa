package com.sunesoft.lemon.syms.hrForms.application.formTrainChoose;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public interface FormTrainChooseEmpService extends FormService<FormTrainChooseEmp,FormTrainChooseEmpDto> {

     public CommonResult addEmpById(Long formNo,List<Long> empIds);

     public CommonResult deleteEmpById(Long formNo,Long id);

}
