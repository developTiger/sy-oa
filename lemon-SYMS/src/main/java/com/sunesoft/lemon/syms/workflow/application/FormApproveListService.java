package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveList;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;

import java.util.List;

/**
 * Created by zhouz on 2016/6/16.
 */
public interface FormApproveListService {

    public List<FormApproveList> getApproveListByFormKind(String formKind);

    public CommonResult AddFormApproveList(FormAppListDto dto);

    public CommonResult UpdateFormApproveList(FormAppListDto dto);

    public CommonResult DeleteFormApproveList(Long id);

    public List<FormAppListDto> getApproveListByCriteria(FormListCriteria criteria);
    
    public FormAppListDto getById(Long  id);

    public CommonResult updateTreatiseApproveByCriteria(FormListCriteria criteria,FormApproveRole approver_role_id);

    public CommonResult updateResultCetificateApproveByCriteria(FormListCriteria criteria,FormApproveRole approver_role_id);

    public CommonResult changeRoleId(FormListCriteria criteria,FormApproveRole approver_role_id,String step);
}
