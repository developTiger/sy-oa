package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
public interface EquipmentHealthRecordRepository extends IRepository<EquipmentHealthRecord,Long> {

    Long save(EquipmentHealthRecord record);


    List<EquipmentHealthRecord> getAll();

    void delete(Long id);

    EquipmentHealthRecord getById(Long id);

}
