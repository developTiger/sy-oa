package com.sunesoft.lemon.syms.eHr.domain.notice.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.domain.notice.AdviceType;
import com.sunesoft.lemon.syms.eHr.domain.notice.AdviceTypeRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2017/1/18.
 */
@Service("adviceTypeRepository")
public class AdviceTypeRepositoryImpl extends GenericHibernateRepository<AdviceType,Long> implements AdviceTypeRepository {
    @Override
    public void delete(List<Long> ids) {
        Criteria criteria=getSession().createCriteria(AdviceType.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.in("id",ids));
        List<AdviceType> list=criteria.list();
        if(list!=null&&list.size()>0){
            for(AdviceType type:list){
                type.setIsActive(false);
                type.setLastUpdateTime(new Date());
                save(type);
            }
        }

    }

    @Override
    public List<AdviceType> getByName(String name) {
        Criteria criteria=getSession().createCriteria(AdviceType.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("name", name));
       return criteria.list()==null? Collections.EMPTY_LIST: criteria.list();
    }

    @Override
    public PagedResult<AdviceType> page(AdviceTypeCriteria criter) {

        Criteria criteria=getSession().createCriteria(AdviceType.class);
        criteria.add(Restrictions.eq("isActive",true));
        if(!StringUtils.isNullOrWhiteSpace(criter.getName())){
            criteria.add(Restrictions.like("name",'%'+criter.getName()+'%'));
        }
        if(criter.getForbide()!=null){
            criteria.add(Restrictions.eq("forbide", criter.getForbide()));
        }
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((criter.getPageNumber() - 1) * criter.getPageSize()).setMaxResults(criter.getPageSize());
        criteria.addOrder(Order.desc("createDateTime"));
        return new PagedResult<AdviceType>(criteria.list()==null?Collections.EMPTY_LIST:criteria.list(),criter.getPageNumber(),criter.getPageSize(),totalCount);
    }
}
