package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.hrForms.application.criteria.LeaveFormCriteria;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeaveRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("formLeaveRepository")
public class FormLeaveRepositoryImpl extends GenericHibernateRepository<FormLeave, Long> implements FormLeaveRepository {

    @Override
    public PagedResult<FormLeave> page(LeaveFormCriteria criteria) {
        Criteria criter = getSession().createCriteria(FormLeave.class);
        criter.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getPersonName())) {
            criter.add(Restrictions.like("applyerName", "%" + criteria.getPersonName() + "%"));
        }
        if (criteria.getDeptId() != null && criteria.getDeptId() > 0) {
            criter.add(Restrictions.eq("deptId", criteria.getDeptId()));
        }
        if (criteria.getBegin() != null) {
            criter.add(Restrictions.ge("fromTime", criteria.getBegin()));
        }
        if (criteria.getEnd() != null) {
            criter.add(Restrictions.le("toTime", criteria.getEnd()));
        }
        int total=((Long)criter.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criter.addOrder(Order.desc("createDateTime"));
        criter.setProjection(null);
        criter.setFirstResult((criteria.getPageNumber()-1)*criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormLeave> leaves=criter.list();
        return new PagedResult<FormLeave>(leaves==null? Collections.EMPTY_LIST:leaves,criteria.getPageNumber(),criteria.getPageSize(),total);
    }
}
