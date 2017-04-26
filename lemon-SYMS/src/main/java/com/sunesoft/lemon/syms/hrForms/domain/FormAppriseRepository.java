package com.sunesoft.lemon.syms.hrForms.domain;


public interface FormAppriseRepository {

     Long save(EmpAppraise empAppraise);

     void delete(Long empAppraiseId);

    EmpAppraise get(Long empAppraiseId);
}
