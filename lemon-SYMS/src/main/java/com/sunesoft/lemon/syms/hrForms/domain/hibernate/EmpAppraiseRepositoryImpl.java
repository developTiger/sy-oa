package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/26.
 */

@Service("empAppraiseRepository")
public class EmpAppraiseRepositoryImpl extends GenericHibernateRepository<EmpAppraise,Long> implements EmpAppraiseRepository {


    @Override
    public EmpAppraise getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(EmpAppraise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (EmpAppraise)criterion.uniqueResult();
    }
}
