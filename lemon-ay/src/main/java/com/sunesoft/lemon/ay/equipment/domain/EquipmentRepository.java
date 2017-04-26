package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

/**
 * Created by jiangkefan on 2016/8/6.
 */
public interface EquipmentRepository extends IRepository<Equipment,Long>{

      Long save(Equipment equipment);

      Equipment getByNum(String proNum);

}
