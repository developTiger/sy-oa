package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecordRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
@Service("equipmentHealthRecordRepository")
public class EquipmentHealthRecordRepositoryImpl extends GenericHibernateRepository<EquipmentHealthRecord,Long> implements EquipmentHealthRecordRepository {

    @Override
    public List<EquipmentHealthRecord> getAll() {
        String hql = "from EquipmentHealthRecord";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public EquipmentHealthRecord getById(Long id) {
        String hql = "from EquipmentHealthRecord t where t.id="+id;
        Query query = getSession().createQuery(hql);
        return (EquipmentHealthRecord)query.uniqueResult();
    }
}
