package com.sunesoft.lemon.ay.partyGroup.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagement;
import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagementRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("propagandaManagementRepository")
public class PropagandaManagementRepositoryImpl extends GenericHibernateRepository<PropagandaManagement,Long> implements PropagandaManagementRepository {
    @Override
    public PropagandaManagement getById(Long id) {
        Criteria criteria = getSession().createCriteria(PropagandaManagement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (PropagandaManagement)criteria.uniqueResult();
    }

    @Override
    public List<PropagandaManagement> getAll() {
        String hql = "from PropagandaManagement";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
