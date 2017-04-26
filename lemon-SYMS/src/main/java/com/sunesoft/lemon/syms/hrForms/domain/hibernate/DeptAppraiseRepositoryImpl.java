package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.DeptAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.DeptAppraiseRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("deptAppraiseRepository")
public class DeptAppraiseRepositoryImpl extends GenericHibernateRepository<DeptAppraise,Long> implements DeptAppraiseRepository {

    @Override
    public DeptAppraise getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(DeptAppraise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (DeptAppraise)criterion.uniqueResult();
    }
}
