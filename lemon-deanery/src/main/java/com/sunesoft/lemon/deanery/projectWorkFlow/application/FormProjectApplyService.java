package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhouz on 2016/8/19.
 */
public interface FormProjectApplyService  extends FormService<FormProjectApply,FormProjectApplyDto> {


    public CommonResult addPriceAndApprove(ApproveFormDto dto, Double price);

    /**
     *  获取项目列表
     * */
    public List<ScientificResearchProject> queryProject();


    CommonResult updateProject(ApproveFormDto dto, String content, String projName, String projectNo, FormProjectApplyDto formProjectApplyDto);

    CommonResult nextProject(ApproveFormDto dto );

    CommonResult uploadProjectFile(Long formNo ,String fileId,String fileName);

    ScientificResearchProject getFormSRPByFormNo(Long formNo);

    FormProjectApplyDto getFormProjectApply(Long id);

    CommonResult updateProjectApplyEmployee(ApproveFormDto dto,String employeeid);

    CommonResult updateProjectApplyByFormNo(Long formNo, List<Long> list,String type);

    CommonResult saveAllApproveFile(FormProjectApplyDto formProjectApplyDto);

    PagedResult<FormProjectApplyDto> selectLeaderApprove(FormProjectApplyDto clStep) throws UnsupportedEncodingException;

    String findOpenFlowEmployeeids(long l, String type);
}
