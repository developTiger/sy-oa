package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProjectRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposal;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposalRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("formWorkersProposalRepository")
public class FormWorkersProposalRepositoryImpl extends GenericHibernateRepository<FormWorkersProposal,Long> implements FormWorkersProposalRepository {
    @Override
    public FormWorkersProposal getById(Long id) {
        Criteria criteria = getSession().createCriteria(FormWorkersProposal.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (FormWorkersProposal)criteria.uniqueResult();
    }

    @Override
    public List<FormWorkersProposal> getAll() {
        String hql = "from FormWorkersProposal";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
