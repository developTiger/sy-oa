package com.sunesoft.lemon.deanery.ReceptionFlow.application;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionNB;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface Reception2Service extends FormService<ReceptionNB,ReceptionDto> {


    /**
     * 选择领导审核
     * @param dto 审核dto 单号
     * @param leaderType 领导类型  1：主要领导  2：分管领导
     * @param empIds 新增分管领导审核人员Id列表
     * @return CommonResult
     */
    CommonResult leaderApprove(ApproveFormDto dto,Integer leaderType,List<Long> empIds);


    //院长办公室T2审批
    CommonResult dean(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param);

    //分管领导会签
    CommonResult secondary(ApproveFormDto dto,Map<String,Object> param);

    //主要领导审批choice: 0：通过，1：不通过
    CommonResult mainApply(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param,int choice);

    //院长办公室T5
    CommonResult dean2(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param,int choice);

    //确认接待
    CommonResult confirm(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param);

    //上传
    CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    public CommonResult uploadProjectFile1(Long formNo, String fileId, String fileName);

    //导出数据Dto
    ReceptionDto downByOrderIdReception(String id);

    List<ReceptionDto> receptionAll(String formKind,int clstep,boolean isComplete);
}
