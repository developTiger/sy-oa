package com.sunesoft.lemon.deanery.carWorkFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApplyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
@Service(value = "carApplyRepository")
public class CarApplyRepositoryImpl extends GenericHibernateRepository<CarApply,Long> implements CarApplyRepository{
    @Override
    public CarApply get(Long formNo, Boolean isActive) {
        Criteria criteria = getSession().createCriteria(CarApply.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",isActive));
        List<CarApply> list = criteria.list();
        if (null == list && list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    @Override
    public void update(CarApply carApply) {
        getSession().saveOrUpdate(carApply);
    }

    /**
     * 根据taskid和isactive属性查询
     * @param orderid
     * @param isActive
     * @return
     */
    @Override
    public CarApply get(String orderid, Boolean isActive) {
        CarApply carApply = null;
        Criteria criteria = getSession().createCriteria(CarApply.class);
        criteria.add(Restrictions.eq("orderId",orderid));
        criteria.add(Restrictions.eq("isActive",isActive));
        List<CarApply> list = criteria.list();
        if ( null == list || list.size() == 0 ){
            return null;
        }
        carApply = list.get(0);
        return carApply;
    }
}
