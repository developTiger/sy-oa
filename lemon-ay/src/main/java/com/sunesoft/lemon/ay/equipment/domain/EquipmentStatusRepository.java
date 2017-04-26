package com.sunesoft.lemon.ay.equipment.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
public interface EquipmentStatusRepository {

     Long save(EquipmentStatus equipmentStatus);


     EquipmentStatus getByResID(Long resId);



}
