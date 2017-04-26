package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraiseDetail;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraiseDetailsRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("empSubAppraiseDetailsRepository")
public class EmpSubAppraiseDetailsRepositoryImpl extends GenericHibernateRepository<EmpSubAppraiseDetail,Long> implements EmpSubAppraiseDetailsRepository {

}
