package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievements;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievementsRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
@Service("formWorkAchievementsRepository")
public class FormWorkAchievementsRepositoryImpl extends GenericHibernateRepository<FormWorkAchievements,Long> implements FormWorkAchievementsRepository {
    @Override
    public FormWorkAchievements getById(Long id) {
        Criteria criteria = getSession().createCriteria(FormWorkAchievements.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (FormWorkAchievements)criteria.uniqueResult();
    }

    @Override
    public List<FormWorkAchievements> getAll() {
        String hql = "from FormWorkAchievements";
        Query query = getSession().createQuery(hql);
        return query.list();
    }


}
