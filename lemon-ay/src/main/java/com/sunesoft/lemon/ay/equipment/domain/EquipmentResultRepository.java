package com.sunesoft.lemon.ay.equipment.domain;

/**
 * Created by jiangkefan on 2016/8/8.
 */
public interface EquipmentResultRepository {

    Long save(EquipmentResult  equipmentResult);

    EquipmentResult getById(Long id);

}
