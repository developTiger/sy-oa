package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

/**
 * Created by zy on 2016/7/26 0026.
 */
public interface FormProjectExecutionRepository {
    Long save(FormProjectExecution formProjectExecution);

    void delete(Long id);

    FormProjectExecution get(Long id);

}
