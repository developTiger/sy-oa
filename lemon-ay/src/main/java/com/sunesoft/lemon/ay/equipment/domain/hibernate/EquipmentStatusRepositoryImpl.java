package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenanceRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatusRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@Service("equipmentStatusRepository")
public class EquipmentStatusRepositoryImpl extends GenericHibernateRepository<EquipmentStatus,Long> implements EquipmentStatusRepository{


    @Override
    public EquipmentStatus getByResID(Long resId) {
        Criteria criteria = getSession().createCriteria(EquipmentStatus.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("resId", resId));
        return (EquipmentStatus)criteria.uniqueResult();
    }

}
