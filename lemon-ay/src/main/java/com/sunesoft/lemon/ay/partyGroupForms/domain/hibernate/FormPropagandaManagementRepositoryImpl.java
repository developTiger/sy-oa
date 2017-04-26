package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagement;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagementRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("formPropagandaManagementRepository")
public class FormPropagandaManagementRepositoryImpl extends GenericHibernateRepository<FormPropagandaManagement,Long> implements FormPropagandaManagementRepository {
    @Override
    public FormPropagandaManagement getById(Long id) {
        Criteria criteria = getSession().createCriteria(FormPropagandaManagement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (FormPropagandaManagement)criteria.uniqueResult();
    }

    @Override
    public List<FormPropagandaManagement> getAll() {
        String hql = "from FormPropagandaManagement";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public FormPropagandaManagement getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormPropagandaManagement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormPropagandaManagement)criteria.uniqueResult();
    }


}
