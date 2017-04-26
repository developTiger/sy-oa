package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProject;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface WorkProjectService {

    /**
     * 只做新增
     * @param workProjectDto
     * @return
     */
    public CommonResult addOrUpdateWorkPro(WorkProjectDto workProjectDto);

    public WorkProjectDto getById(Long id);


    /**
     * 获取所有已通过审核的劳动项目信息
     * @return
     */
    public List<WorkProjectDto> getAll();

    /**
     * 获取所有已通过的审核的劳动项目数据 劳动成果一旦入库 就不能重复申报
     * @return
     */
    public List<WorkProjectDto> getAllByWorkAchievement();

    public PagedResult<WorkProjectDto> getPagesWorkProjectDto(WorkProjectCriteria workProjectCriteria);

    /**
     * 后期进行编辑 编辑主体参与人，目标及措施，预期效益3个字段
     * @param dto
     * @return
     */
    public CommonResult updateWorkProject(WorkProjectDto dto);



}
