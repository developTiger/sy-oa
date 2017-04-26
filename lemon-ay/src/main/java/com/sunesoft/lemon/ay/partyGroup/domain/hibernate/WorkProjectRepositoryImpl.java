package com.sunesoft.lemon.ay.partyGroup.domain.hibernate;

import com.sunesoft.lemon.ay.partyGroup.domain.WorkProject;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProjectRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
@Service("workProjectRepository")
public class WorkProjectRepositoryImpl extends GenericHibernateRepository<WorkProject,Long> implements WorkProjectRepository {


    @Override
    public WorkProject getById(Long id) {
        Criteria criteria = getSession().createCriteria(WorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (WorkProject)criteria.uniqueResult();
    }

    @Override
    public List<WorkProject> getAll() {
        String hql = "from WorkProject ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
