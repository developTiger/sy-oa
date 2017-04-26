package com.sunesoft.lemon.ay.partyGroup.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievementsRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
@Service("workAchievementsRepository")
public class WorkAchievementsRepositoryImpl extends GenericHibernateRepository<WorkAchievements,Long> implements WorkAchievementsRepository {
    @Override
    public WorkAchievements getById(Long id) {
        Criteria criteria = getSession().createCriteria(WorkAchievements.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (WorkAchievements)criteria.uniqueResult();
    }

    @Override
    public List<WorkAchievements> getAll() {
        String hql = "from WorkAchievements";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
