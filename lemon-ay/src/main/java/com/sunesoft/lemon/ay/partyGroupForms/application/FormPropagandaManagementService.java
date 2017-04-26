package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormPropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagement;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

/**
 * Created by admin on 2016/9/5.
 */
public interface FormPropagandaManagementService extends FormService<FormPropagandaManagement,FormPropagandaManagementDto> {

    public FormPropagandaManagementDto getById(Long id);

    public CommonResult deleteProManagement(Long id);

    public CommonResult updateProManagement(FormPropagandaManagementDto dto);

}
