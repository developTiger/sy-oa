package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievementRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
@Service("formInnovationAchievementRepository")
public class FormInnovationAchievementRepositoryImpl extends GenericHibernateRepository<FormInnovationAchievement,Long> implements FormInnovationAchievementRepository {
    @Override
    public FormInnovationAchievement getById(Long id) {
        Criteria criteria = getSession().createCriteria(FormInnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (FormInnovationAchievement)criteria.uniqueResult();
    }

    @Override
    public List<FormInnovationAchievement> getAll() {
        String hql = "from FormInnovationAchievement";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public FormInnovationAchievement getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormInnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormInnovationAchievement)criteria.uniqueResult();
    }
}
