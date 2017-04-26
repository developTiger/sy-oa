package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvectionRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/19.
 */
@Service("formEvectionRepository")
public class FormEvectionRepositoryImpl extends GenericHibernateRepository<FormEvection,Long> implements FormEvectionRepository{

    @Override
    public Integer getMaxNo() {
        Integer year=DateHelper.getYear(new Date());
        String hql = "select max(f.numberNo) from FormEvection f where to_char(f.evectionTime,'yyyy') ="+year;
        Query q = getSession().createQuery(hql);
        return (Integer) q.uniqueResult();
    }
}
