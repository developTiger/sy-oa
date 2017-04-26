package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievements;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;

/**
 * Created by admin on 2016/9/3.
 */
public interface FormWorkAchievementsService extends FormService<FormWorkProject,FormWorkProjectDto> {


    public PagedResult<FormWorkAchievementsDto> getPagesFormWorkAchievementDto(FormWorkAchievementCriteria achievementCriteria) ;

}
