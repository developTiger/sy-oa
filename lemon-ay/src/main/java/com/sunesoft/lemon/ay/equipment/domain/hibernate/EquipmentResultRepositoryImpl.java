package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.*;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@Service("equipmentResultRepository")
public class EquipmentResultRepositoryImpl extends GenericHibernateRepository<EquipmentResult,Long> implements EquipmentResultRepository{

    @Override
    public EquipmentResult getById(Long id) {
        Criteria criteria = getSession().createCriteria(EquipmentResult.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("id",id));
        return (EquipmentResult)criteria.uniqueResult();
    }

}
