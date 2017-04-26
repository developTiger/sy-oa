package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormInnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

/**
 * Created by admin on 2016/9/3.
 */
public interface FormInnovationAchievementService extends FormService<FormInnovationAchievement,FormInnovationAchievementDto> {


    /**
     * 查询出 clStep为2 formStatus为签核中的 数据
     * @param achievementCriteria
     * @return
     */
    public PagedResult<FormInnovationAchievementDto> getPagesFormsByFailAll(InnovationAchievementCriteria achievementCriteria) ;



}
