package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
public interface FormProjectApplyRepository {
    Long save(FormProjectApply formProjectApply);

    void delete(Long id);

    FormProjectApply get(Long id);


}
