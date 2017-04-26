package com.sunesoft.lemon.ay.equimentForms.domain;

/**
 * Created by jiangkefan on 2016/8/17.
 */
public interface FormEquipMainRepository {

        Long save(FormEquipMain formEquipMain);

    FormEquipMain get(Long id);

}
