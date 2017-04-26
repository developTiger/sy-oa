package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

/**
 * Created by zy on 2016/8/26 0026.
 */
public interface FormProjectAcceptanceRepository {
    Long save(FormProjectAcceptance formProjectAcceptance);

    void delete(Long id);

    FormProjectAcceptance get(Long id);

}
