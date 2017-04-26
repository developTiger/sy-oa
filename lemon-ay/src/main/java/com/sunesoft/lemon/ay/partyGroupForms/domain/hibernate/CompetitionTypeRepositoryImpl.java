package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionTypeRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/2/14.
 */
@Service("CompetitionTypeRepository")
public class CompetitionTypeRepositoryImpl extends GenericHibernateRepository<CompetitionType,Long> implements CompetitionTypeRepository {
    @Override
    public CompetitionType getById(Long id) {
        Criteria criteria = getSession().createCriteria(CompetitionType.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (CompetitionType)criteria.uniqueResult();
    }
}
