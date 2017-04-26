package com.sunesoft.lemon.ay.partyGroupForms.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProjectRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
@Service("formWorkProjectRepository")
public class FormWorkProjectRepositoryImpl extends GenericHibernateRepository<FormWorkProject,Long> implements FormWorkProjectRepository {
    @Override
    public FormWorkProject getById(Long id) {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (FormWorkProject)criteria.uniqueResult();
    }

    @Override
    public List<FormWorkProject> getAll() {
        String hql = "from FormWorkProject";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public FormWorkProject getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormWorkProject)criteria.uniqueResult();

    }
}
