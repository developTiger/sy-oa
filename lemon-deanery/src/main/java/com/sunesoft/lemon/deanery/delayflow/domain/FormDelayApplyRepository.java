package com.sunesoft.lemon.deanery.delayflow.domain;

/**
 * Created by swb on 2016/8/25.
 */
public interface FormDelayApplyRepository {
    Long save(FormDelayApply formDelayApply);

    void delete(Long id);

    FormDelayApply get(Long id);
}
