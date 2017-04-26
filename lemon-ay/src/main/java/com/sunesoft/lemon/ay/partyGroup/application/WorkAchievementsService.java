package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkAchievementsCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface WorkAchievementsService {

    public CommonResult addOrUpdateWorkAchievement(WorkAchievementsDto workAchievementsDto);

    public WorkAchievementsDto getById(Long id);

    public List<WorkAchievementsDto> getAll();

    public PagedResult<WorkAchievementsDto> getPagesWorkAchievementsDto(WorkAchievementsCriteria workAchievementsCriteria);

    /**
     * 后期进行编辑 编辑主体参与人，目标及措施，预期效益3个字段
     * @param dto
     * @return
     */
    public CommonResult updateWorkAchievement(WorkAchievementsDto dto);

}
