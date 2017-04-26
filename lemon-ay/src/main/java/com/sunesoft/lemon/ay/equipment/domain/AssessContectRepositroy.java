package com.sunesoft.lemon.ay.equipment.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/19.
 */
public interface AssessContectRepositroy {

    Long save(AssessContent assessContent);

    void delete(Long id);

    List<AssessContent> getAllContents();

    AssessContent get(Long id);

    List<AssessContent> getAllByEquipmentId(Long equipmentId);
}
