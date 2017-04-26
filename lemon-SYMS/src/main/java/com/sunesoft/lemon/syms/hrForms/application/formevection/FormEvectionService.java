package com.sunesoft.lemon.syms.hrForms.application.formevection;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.AttendanceByFlowDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEvectionDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.EvectionCriteria;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.Date;
import java.util.List;


public interface FormEvectionService extends FormService<FormEvection,FormEvectionDto> {

    public PagedResult<FormEvectionDto> getEvectionPage(EvectionCriteria criteria);

    public List<AttendanceByFlowDto> getEmpStatusByBusiness(Date time);
    public List<Long> getEmpStatusByBusiness(Long deptId,Date time);


    PagedResult getDownlodEvection(EvectionCriteria criteria);

    CommonResult updatePrint(String formKind);
}
