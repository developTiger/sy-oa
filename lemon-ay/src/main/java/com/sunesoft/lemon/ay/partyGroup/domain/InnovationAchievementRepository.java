package com.sunesoft.lemon.ay.partyGroup.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
public interface InnovationAchievementRepository {

    Long save(InnovationAchievement innovationAchievement);

    InnovationAchievement getById(Long id);

    List<InnovationAchievement> getAll();

}
