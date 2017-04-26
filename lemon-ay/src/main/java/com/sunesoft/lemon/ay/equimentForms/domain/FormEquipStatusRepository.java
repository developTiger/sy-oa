package com.sunesoft.lemon.ay.equimentForms.domain;

/**
 * Created by jiangkefan on 2016/8/16.
 */
public interface FormEquipStatusRepository {

    Long save(FormEquiStatus formEquiStatus);

    FormEquiStatus get(Long id);
}
