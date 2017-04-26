package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.InnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievement;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
public interface InnovationAchievementService {

    public CommonResult addOrUpdate(InnovationAchievementDto innovationAchievementDto);

    public InnovationAchievementDto getById(Long id);

    public List<InnovationAchievementDto> getAll();

    public PagedResult<InnovationAchievementDto> getPagesInnovationAchiDto(InnovationAchievementCriteria innovationAchievementCriteria);


}
