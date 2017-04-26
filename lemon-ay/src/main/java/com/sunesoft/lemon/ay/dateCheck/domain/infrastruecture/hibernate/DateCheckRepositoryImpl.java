package com.sunesoft.lemon.ay.dateCheck.domain.infrastruecture.hibernate;

import com.sunesoft.lemon.ay.dateCheck.application.Criteria.DateCheckCriteria;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheck;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheckRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiazl on 2016/10/21.
 */
@Service("dateCheckRepository")
public class DateCheckRepositoryImpl extends GenericHibernateRepository<DateCheck, Long> implements DateCheckRepository {
    @Override
    public List<DateCheck> getByName(String equipName) {
        Criteria criteria = getSession().createCriteria(DateCheck.class);
        criteria.add(Restrictions.eq("equipName", equipName));
        criteria.add(Restrictions.eq("isActive", true));
        return criteria.list();
    }

    @Override
    public PagedResult<DateCheck> page(DateCheckCriteria checkCriteria) {
        Criteria criteria = getSession().createCriteria(DateCheck.class);
        criteria.add(Restrictions.eq("isActive", true));

//        if (!StringUtils.isNullOrWhiteSpace(checkCriteria.getEquipName()))
//            criteria.add(Restrictions.like("equipName", "%" + checkCriteria.getEquipName() + "%"));
        if(checkCriteria.getId()!=null&&checkCriteria.getId()>0)
            criteria.add(Restrictions.eq("equipId",checkCriteria.getId()));
        if (checkCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("checkTime", checkCriteria.getBeginTime()));
        if (checkCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("checkTime", checkCriteria.getEndTime()));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
//
        criteria.setProjection(null);
        criteria.addOrder(checkCriteria.isAscOrDesc() ?
                        Order.asc(checkCriteria.getOrderByProperty() == null ? "id" : checkCriteria.getOrderByProperty()) :
                        Order.desc(checkCriteria.getOrderByProperty() == null ? "id" : checkCriteria.getOrderByProperty())
        );
        criteria.setFirstResult((checkCriteria.getPageNumber()-1)*checkCriteria.getPageSize()).setMaxResults(checkCriteria.getPageSize());

        return new PagedResult<DateCheck>(criteria.list(),checkCriteria.getPageNumber(),checkCriteria.getPageSize(),totalCount);
    }
}
