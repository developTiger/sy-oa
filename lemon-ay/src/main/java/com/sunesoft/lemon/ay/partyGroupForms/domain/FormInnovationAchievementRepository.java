package com.sunesoft.lemon.ay.partyGroupForms.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
public interface FormInnovationAchievementRepository {

    Long save(FormInnovationAchievement achievement);

    FormInnovationAchievement getById(Long id);

    List<FormInnovationAchievement> getAll();

    FormInnovationAchievement getByFormNo(Long formNo);

}
