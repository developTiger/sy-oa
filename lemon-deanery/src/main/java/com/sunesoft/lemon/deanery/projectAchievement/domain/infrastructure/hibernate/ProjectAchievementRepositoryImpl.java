package com.sunesoft.lemon.deanery.projectAchievement.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievement;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievementRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by MJ003 on 2016/10/19.
 */
@Service("ProjectAchievementRepository")
public class ProjectAchievementRepositoryImpl extends GenericHibernateRepository<ProjectAchievement,Long>
        implements ProjectAchievementRepository {
}
