package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetailRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/11/7.
 */
@Service("dateDetailRepository")
public class DateDetailRepositoryImpl extends GenericHibernateRepository<DateDetail,Long> implements DateDetailRepository {
    @Override
    public List<DateDetail> getAll() {
        String hql = "from DateDetail";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
