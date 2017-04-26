package com.sunesoft.lemon.syms.eHr.domain.attend.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendTypeCriteria;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendTypeRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.Attendance;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
@Service("attendTypeRepository")

public class AttendTypeRepositoryImpl extends GenericHibernateRepository<AttendType, Long> implements AttendTypeRepository {


    @Override
    public AttendType getByCord(String cord) {
        Criteria criteria = getSession().createCriteria(AttendType.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("cord", cord));
        return (AttendType)criteria.uniqueResult();//唯一，否则报错
//        List<AttendType> list = criteria.list();
//        if (list == null || list.size() == 0)
//            return null;
//        return list.get(0);
    }

    @Override
    public List<AttendType> getList(String name) {
        Criteria criteria = getSession().createCriteria(AttendType.class);
        criteria.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.add(Restrictions.eq("name", name));
        }
        criteria.addOrder(Order.asc("id"));
        return criteria.list();
    }

    @Override
    public PagedResult<AttendType> page(AttendTypeCriteria criteria) {
        Criteria criter = getSession().createCriteria(AttendType.class);
        criter.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            criter.add(Restrictions.like("name", "%" + criteria.getName() + "%"));
        }
        int totalCount = ((Long) criter.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criter.setProjection(null);
        criter.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        criter.addOrder(Order.desc("createDateTime"));
        List<AttendType> list = criter.list();
        return new PagedResult<AttendType>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

}
