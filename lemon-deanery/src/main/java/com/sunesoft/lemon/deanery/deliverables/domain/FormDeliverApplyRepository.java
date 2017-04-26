package com.sunesoft.lemon.deanery.deliverables.domain;

/**
 * Created by swb on 2016/8/30.
 */
public interface FormDeliverApplyRepository {
    Long save(FormDeliverApply formDeliverApply);

    void delete(Long id);

    FormDeliverApply get(Long id);
}
