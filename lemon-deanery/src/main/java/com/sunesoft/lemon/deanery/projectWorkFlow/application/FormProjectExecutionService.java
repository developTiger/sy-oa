package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormProjectExecutoryCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecutionFile;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by zy on 2016/8/19.
 */
public interface FormProjectExecutionService extends FormService<FormProjectExecution,FormProjectExecutoryDto> {


    public CommonResult addNewApprove(ApproveFormDto dto, Double price);

    /**
     * 获取项目开题列表
     */
    public List<FormProjectApply> queryProject();


    CommonResult updateProject(ApproveFormDto dto, FormProjectExecutoryDto formProjectExecutoryDto);

    CommonResult nextProject(ApproveFormDto dto);

    CommonResult nextProject1(ApproveFormDto dto, String empId, String clStep);
    CommonResult nextProject2(ApproveFormDto dto, String clStep);
    // CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    FormProjectExecutoryDto getFormProjectExecution(Long id);

    Long updateFormProjectExecutionById(Long id);

    Long updateProjectExecutionById(Long id, String instructions, String clstep);

    void deleteById(Long id);

    public FormProjectExecution getFormByFormNo1(Long formNo);

    List<FormProjectExecutoryDto> getProjectApproves3(FormProjectExecutoryCriteria formProjectExecutoryCriteria, Integer clstep, Long userId);

    CommonResult updateProject(ApproveFormDto dto, String zjid, String zgid, String clStep);

    CommonResult reProject(ApproveFormDto dto);

    //上传
    CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    CommonResult uploadProjectFile2(Long formNo, String fileId1, String fileName1);



}

