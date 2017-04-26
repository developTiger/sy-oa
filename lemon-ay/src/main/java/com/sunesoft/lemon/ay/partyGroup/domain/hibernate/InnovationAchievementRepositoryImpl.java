package com.sunesoft.lemon.ay.partyGroup.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievement;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievementRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
@Service("innovationAchievementRepository")
public class InnovationAchievementRepositoryImpl extends GenericHibernateRepository<InnovationAchievement,Long> implements InnovationAchievementRepository {
    @Override
    public InnovationAchievement getById(Long id) {
        Criteria criteria = getSession().createCriteria(InnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (InnovationAchievement)criteria.uniqueResult();
    }

    @Override
    public List<InnovationAchievement> getAll() {
        String hql = "from InnovationAchievement";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
