package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecordRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
@Service("equipmentWorkingRecordRepository")
public class EquipmentWorkingRecordRepositoryImpl extends GenericHibernateRepository<EquipmentWorkingRecord,Long> implements EquipmentWorkingRecordRepository {
    @Override
    public List<EquipmentWorkingRecord> getAll() {
        String hql = "from EquipmentWorkingRecord";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public EquipmentWorkingRecord getById(Long id) {
        String hql = "from EquipmentWorkingRecord t where t.id="+id;
        Query query = getSession().createQuery(hql);
        return (EquipmentWorkingRecord)query.uniqueResult();
    }


}
