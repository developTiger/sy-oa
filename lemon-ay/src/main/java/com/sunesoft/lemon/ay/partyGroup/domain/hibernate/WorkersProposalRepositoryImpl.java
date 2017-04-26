package com.sunesoft.lemon.ay.partyGroup.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroup.domain.WorkProjectRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposal;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposalRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("workersProposalRepository")
public class WorkersProposalRepositoryImpl extends GenericHibernateRepository<WorkersProposal,Long> implements WorkersProposalRepository {
    @Override
    public WorkersProposal getById(Long id) {
        Criteria criteria = getSession().createCriteria(WorkersProposal.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (WorkersProposal)criteria.uniqueResult();
    }

    @Override
    public List<WorkersProposal> getAll() {
        String hql = "from WorkersProposal";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
