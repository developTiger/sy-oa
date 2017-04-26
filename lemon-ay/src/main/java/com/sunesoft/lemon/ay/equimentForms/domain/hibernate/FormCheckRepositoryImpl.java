package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormCheck;
import com.sunesoft.lemon.ay.equimentForms.domain.FormCheckRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/17.
 */
@Service("formCheckRepository")
public class FormCheckRepositoryImpl extends GenericHibernateRepository<FormCheck,Long> implements FormCheckRepository {
    @Override
    public List<FormCheck> getAll() {
        String hql ="form FormCheck";
        Query  q = getSession().createQuery(hql);
        return q.list();
    }
}
