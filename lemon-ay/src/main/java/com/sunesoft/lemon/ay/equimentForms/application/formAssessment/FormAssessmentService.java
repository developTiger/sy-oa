package com.sunesoft.lemon.ay.equimentForms.application.formAssessment;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessment;
import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */
public interface FormAssessmentService extends FormService<FormAssessment,FormAssessmentDto>{

    public CommonResult addOrUpdateContent(FormAssessContentDto dto );


    public CommonResult delContent(Long formNo,Long id);


    public List<FormAssessContentDto> getAllContent(Long asId);


    public FormAssessContentDto getContentById(Long contentId);

    /**
     * 每台设备对应的多条设备评估 已审核通过
     * @param id
     * @return
     */
    public List<FormAssessmentDto> getByEquipmentId(Long id);

    public PagedResult<AssessmentDto> getPages(AssessContentCritera contentCritera,Long equipmentId);




}
