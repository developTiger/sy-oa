package com.sunesoft.lemon.ay.partyGroupForms.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
public interface FormWorkAchievementsRepository {

    Long save(FormWorkAchievements formWorkAchievements);

    FormWorkAchievements getById(Long id);

    List<FormWorkAchievements> getAll();

}
