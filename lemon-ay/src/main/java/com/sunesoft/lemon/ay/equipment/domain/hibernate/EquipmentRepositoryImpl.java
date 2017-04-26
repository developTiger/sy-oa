package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@Service("equipmentRepository")
public class EquipmentRepositoryImpl extends GenericHibernateRepository<Equipment,Long> implements EquipmentRepository{


    @Override
    public Equipment getByNum(String proNum) {
        Criteria criteria = getSession().createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("propertyNum", proNum));
        Object eq = criteria.uniqueResult();
        return null == eq ? null:(Equipment)eq;
    }
}
