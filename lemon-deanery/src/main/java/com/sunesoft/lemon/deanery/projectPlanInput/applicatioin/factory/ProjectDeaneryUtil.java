package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanOutput;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;

/**
 * Created by pxj on 2016/9/12.
 */
public class ProjectDeaneryUtil {
    public static ProjectPlanInput converFromListProjectPlanDto(ProjectPlanInputDate project){
        ProjectPlanInput projectResultDto = new ProjectPlanInput();
        BeanUtils.copyProperties(project,projectResultDto);
        projectResultDto.setProjectPlan_InputYear_Str(DateHelper.formatDate(project.getProjectPlan_InputYear(),"yyyy"));
        return projectResultDto;
    }

    public static ProjectPlanOutput converFromListProjectPlanDto1(ProjectPlanInputDate project){
        ProjectPlanOutput projectResultDto = new ProjectPlanOutput();
        projectResultDto.setStartTime(DateHelper.formatDate(project.getProjectPlan_StartTime(), "yyyy-MM-dd"));
        projectResultDto.setEndTime(DateHelper.formatDate(project.getProjectPlan_EndTime(), "yyyy-MM-dd"));
        projectResultDto.setProjectPlan_Content(project.getProjectPlan_Content());
        projectResultDto.setProjectPlan_BearUnit(project.getProjectPlan_BearUnit());
        projectResultDto.setProjectPlan_Name(project.getProjectPlan_Name());
        projectResultDto.setProjectPlan_ParticipatingUnit(project.getProjectPlan_ParticipatingUnit());
        projectResultDto.setProjectPlan_Manager(project.getProjectPlan_Manager());
        projectResultDto.setProjectPlan_InputYear(DateHelper.formatDate(project.getProjectPlan_InputYear(), "yyyy"));
        projectResultDto.setProjectPlan_Number(project.getProjectPlan_Number());
        return projectResultDto;
    }

    public static ProjectPlanInputDate converFromListProjectPlanDate(ProjectPlanInput projectPlanInput) {
        ProjectPlanInputDate projectResultDate = new ProjectPlanInputDate();
        BeanUtils.copyProperties(projectPlanInput,projectResultDate);
       // projectResultDto.setProjectPlan_InputYear_Str(DateHelper.formatDate(project.getProjectPlan_InputYear(),"yyyy"));
        return projectResultDate;
    }
    public static FormProjectApplyDto converFromListProjectApply(FormProjectApply formProjectApply){
        FormProjectApplyDto formProjectApplyDto=new FormProjectApplyDto();
        BeanUtils.copyProperties(formProjectApply,formProjectApplyDto);
        return  formProjectApplyDto;
    }

}
