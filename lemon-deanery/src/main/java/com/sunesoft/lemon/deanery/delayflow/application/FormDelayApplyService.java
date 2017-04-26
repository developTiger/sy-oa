package com.sunesoft.lemon.deanery.delayflow.application;

import com.sunesoft.lemon.deanery.delayflow.application.dto.FormDelayApplyDto;
import com.sunesoft.lemon.deanery.delayflow.criteria.FormDelayCriteria;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by swb on 2016/8/25.
 */
public interface FormDelayApplyService extends FormService<FormDelayApply,FormDelayApplyDto> {

    public CommonResult addDelayAndApprove(ApproveFormDto dto);

    public List<FormDelayApply> queryDelay();

    //CommonResult updateDelayForm(ApproveFormDto dto,String delayName,String delayReason,Integer delayTime);

    CommonResult updateDelayForm1(ApproveFormDto dto,String opinion);

    CommonResult updateDelayForm2(ApproveFormDto dto,String leaderWord);

    CommonResult updateDelayForm3(ApproveFormDto dto,String opinion2);

    CommonResult updateDelayForm05(ApproveFormDto dto,String opinion2);

    CommonResult updateDelayForm06(ApproveFormDto dto,String opinion2);

    CommonResult nextDelayForm(ApproveFormDto dto);

    CommonResult updateDelayForm4(ApproveFormDto dto, String opinion2);

    //上传
    CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    public PagedResult<FormDelayApplyDto> getFormDelayPaged(FormDelayCriteria criteria);

    //第一次汇总审批
    public PagedResult<FormHeaderDto> findFormPaged(FormHeaderCriteria criteria);

    //第二次汇总审批
    public PagedResult<FormHeaderDto> findFormPaged1(FormHeaderCriteria criteria);

    CommonResult updateDelayFormAll1(ApproveFormDto dto);

    CommonResult updateDelayFormAll2(ApproveFormDto dto);

    List<FormDelayApplyDto> getDelayApproves3(FormDelayCriteria formDelayCriteria,String majorType);

    List<FormDelayApplyDto> getDelayApproves4(FormDelayCriteria formDelayCriteria,String majorType);

    //中心科管人员汇总审批
    CommonResult updateProject0(ApproveFormDto dto);

    //科管科汇总审批
    CommonResult updateDelayProject(ApproveFormDto dto,String empid);

    //科管科长审查001
    CommonResult updateDelay001(ApproveFormDto dto);
    //批量数据
    List<Long> getFormApproverByUserId(Long id,int clstep);
    //批量数据
    List<FormDelayApplyDto> getFormDeliverApplyByClstep(String specialtyType, List<Long> l,Integer clstep);
//普通审批
    CommonResult approve(ApproveFormDto dto);
  //处理批量第五步流程
    CommonResult updateBatch(ApproveFormDto dto);
}
