package com.sunesoft.lemon.ay.partyGroup.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface WorkAchievementsRepository {

    Long save(WorkAchievements workAchievements);

    WorkAchievements getById(Long id);

    List<WorkAchievements> getAll();

}
