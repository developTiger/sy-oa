package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraiseRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("empSubAppraiseRepository")
public class EmpSubAppraiseRepositoryImpl extends GenericHibernateRepository<EmpSubAppraise,Long> implements EmpSubAppraiseRepository {

}
