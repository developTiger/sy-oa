package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

/**
 * Created by zhouz on 2016/7/29.
 */
public interface DeptAppraiseRepository  extends IRepository<DeptAppraise,Long> {
    DeptAppraise getByFormNo(Long formNo);
}
