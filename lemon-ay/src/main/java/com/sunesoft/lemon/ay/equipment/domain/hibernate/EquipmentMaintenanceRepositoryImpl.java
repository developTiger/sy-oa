package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenanceRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@Service("equipmentMaintenanceRepository")
public class EquipmentMaintenanceRepositoryImpl  extends GenericHibernateRepository<EquipmentMaintenance,Long> implements EquipmentMaintenanceRepository {
    @Override
    public List<EquipmentMaintenance> getAll() {

        String hql = "from EquipmentMaintenance";
        Query query = getSession().createQuery(hql);
        return query.list();

    }

    @Override
    public List<EquipmentMaintenance> getAllByEquipmentId(Long equipmentId) {
        String hql = "from EquipmentMaintenance e where e.equipment.id ="+equipmentId;
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
