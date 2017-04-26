package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.DeptSubAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.DeptSubAppraiseRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("deptSubAppraiseRepository")
public class DeptSubAppraiseRepositoryImpl extends GenericHibernateRepository<DeptSubAppraise,Long> implements DeptSubAppraiseRepository {

    @Override
    public List<DeptSubAppraise> getSubAppraiseByParentFormNo(Long parentFormNo) {
            Criteria criterion = getSession().createCriteria(DeptSubAppraise.class);
            criterion.add(Restrictions.eq("isActive", true));
            criterion.add(Restrictions.eq("parentFormNo", parentFormNo));
            return criterion.list();
    }
}
