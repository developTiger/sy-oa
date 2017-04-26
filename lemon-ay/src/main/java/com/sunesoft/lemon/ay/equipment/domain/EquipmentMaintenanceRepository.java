package com.sunesoft.lemon.ay.equipment.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
public interface EquipmentMaintenanceRepository {

    Long save(EquipmentMaintenance equipmentMaintenance);

    List<EquipmentMaintenance> getAll();

    EquipmentMaintenance get(Long id);

    List<EquipmentMaintenance> getAllByEquipmentId(Long equipmentId);

}
