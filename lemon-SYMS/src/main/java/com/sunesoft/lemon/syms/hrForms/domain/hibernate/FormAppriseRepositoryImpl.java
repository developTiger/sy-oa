package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.FormAppriseRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("formAppriseRepository")
public class FormAppriseRepositoryImpl extends GenericHibernateRepository<EmpAppraise,Long> implements FormAppriseRepository {


}
