package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.infrastructure.IRepository;

import java.util.List;

/**
 * Created by zhouz on 2016/7/29.
 */
public interface DeptSubAppraiseRepository extends IRepository<DeptSubAppraise,Long> {

    List<DeptSubAppraise> getSubAppraiseByParentFormNo(Long parentFormNo);
}
