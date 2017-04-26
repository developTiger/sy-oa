package com.sunesoft.lemon.ay.equimentForms.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */
public interface FormAssessmentRepository {

    Long save(FormAssessment formAssessment);

    FormAssessment get(Long id);

    /**
     * 通过设备id 获取设备评估记录 多条 审核通过
     * @param equipmentId
     * @return
     */
    List<FormAssessment> getByEquipmentId(Long equipmentId);
}
