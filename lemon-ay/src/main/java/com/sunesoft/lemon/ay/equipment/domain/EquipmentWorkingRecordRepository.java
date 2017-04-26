package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
public interface EquipmentWorkingRecordRepository extends IRepository<EquipmentWorkingRecord,Long> {

    Long save(EquipmentWorkingRecord record);


    List<EquipmentWorkingRecord> getAll();

    void delete(Long id);

    EquipmentWorkingRecord getById(Long id);


}
