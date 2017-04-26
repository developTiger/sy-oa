package com.sunesoft.lemon.deanery.project.application;

import com.sunesoft.lemon.deanery.project.application.dtos.ProjectMainApplyDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface ProjectMainApplyService extends FormService<ProjectMainApply,ProjectMainApplyDto> {

    List<ProjectPlanInput> queryProjectPlan() ;
    Long addProjectMain(ProjectPlanInput projectPlanInput);
    CommonResult mainApprove(ApproveFormDto dto, Integer leaderType, List<Long> empIds);
    ScientificResearchProject getByOrderId(String fromNo);
}
