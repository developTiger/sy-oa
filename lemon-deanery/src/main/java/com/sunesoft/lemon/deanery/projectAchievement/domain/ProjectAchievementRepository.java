package com.sunesoft.lemon.deanery.projectAchievement.domain;

/**
 * Created by MJ003 on 2016/10/19.
 */
public interface ProjectAchievementRepository {
    Long save(ProjectAchievement projectAchievement);

    void delete(Long id);

    ProjectAchievement get(Long id);
}

