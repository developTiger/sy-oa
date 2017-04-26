package com.sunesoft.lemon.ay.equimentForms.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/15.
 */
public interface FormEquipmentRepository {
    Long save(FormEquipment formEquipment);

    FormEquipment get(Long id);

    List<FormEquipment> getAll();

}
