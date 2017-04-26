package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface FormWorkProjectService extends FormService<FormWorkProject,FormWorkProjectDto> {


    public List<FormWorkProjectDto> getAllForm();

    /**
     * 查询出 clStep为2 formStatus为签核中的 数据
     * @param workProjectCriteria
     * @return
     */
    public PagedResult<FormWorkProjectDto> getPagesFormWorkProjectDto(FormWorkProjectCriteria workProjectCriteria) ;

    /**
     * 获取form表里的所有数据 通过和不通过的
     * @param workProjectCriteria
     * @return
     */
    public PagedResult<FormWorkProjectDto> getAllFormsPages(FormWorkProjectCriteria workProjectCriteria) ;

    /**
     * 获取所有审批通过的formworkPorjectDto
     * @return
     */
    public List<FormWorkProjectDto> getAllPassFormWorkProjectDto();


    public FormWorkProjectDto getById(Long id);

}
