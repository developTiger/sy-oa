package com.sunesoft.lemon.syms.workflow.domain;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface FormApproveListRepository {


    Long save(FormApproveList formApprovelist);

    void delete(Long id);

    FormApproveList get(Long id);


}
