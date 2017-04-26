package com.sunesoft.lemon.syms.eHr.domain.attend.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.domain.attend.Attend;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
@Service("attendRepository")
public class AttendRepositoryImpl extends GenericHibernateRepository<Attend, Long> implements AttendRepository {

    @Override
    public Attend get(Long empId, Date time) {
        Criteria criter = getSession().createCriteria(Attend.class);
        criter.add(Restrictions.eq("isActive", true));
        criter.add(Restrictions.eq("dateTime", time));
        criter.add(Restrictions.eq("empId", empId));
        return (Attend)criter.uniqueResult();
    }

    @Override
    public PagedResult<Attend> page(AttendCriteria criteria) {
        Criteria criter = getSession().createCriteria(Attend.class);
        criter.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getEmpName())) {
            criter.add(Restrictions.like("empName", "%" + criteria.getEmpName() + "%"));
        }
        if (criteria.getDepId() != null) {
            criter.add(Restrictions.eq("depId", criteria.getDepId()));
        }
        if (criteria.getAttDate() != null) {

            criter.add(Restrictions.eq("dateTime", criteria.getAttDate()));
        }
        int totalCount = ((Long) criter.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criter.setProjection(null);

        criter.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        criter.addOrder(Order.desc("createDateTime"));
        List<Attend> list = criter.list();
        return new PagedResult<Attend>(list == null ? Collections.EMPTY_LIST : list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }
}
