package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

/**
 * Created by zhouz on 2016/7/26.
 */


public interface EmpAppraiseRepository extends IRepository<EmpAppraise,Long> {

    EmpAppraise getByFormNo(Long formNo);
}
