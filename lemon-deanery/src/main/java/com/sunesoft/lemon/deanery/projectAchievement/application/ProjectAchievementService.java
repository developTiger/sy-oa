package com.sunesoft.lemon.deanery.projectAchievement.application;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.projectAchievement.application.criteria.ProjectAchievementCriteria;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto2;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievement;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by MJ003 on 2016/10/19.
 */
public interface ProjectAchievementService extends FormService<ProjectAchievement,ProjectAchievementDto> {

    PagedResult<ProjectAchievementDto> getCommonDrivers(ProjectAchievementCriteria projectAchievementCriteria);

    ProjectAchievementDto getById(String id);

    CommonResult saveByDto(ProjectAchievementDto dto);

    List<SpecialtyType> getAllSpecialtyType();

    List<ProjectAchievementDto2> getAcievementList();
}
