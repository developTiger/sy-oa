package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionTitleTypeRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/2/13.
 */
@Service("competitionTitleTypeRepository")
public class CompetitionTitleTypeRepositoryImpl extends GenericHibernateRepository<WorkProject_competitionTitleType,Long> implements CompetitionTitleTypeRepository {
    @Override
    public WorkProject_competitionTitleType getById(Long id) {
        Criteria criteria = getSession().createCriteria(WorkProject_competitionTitleType.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (WorkProject_competitionTitleType)criteria.uniqueResult();
    }
}
