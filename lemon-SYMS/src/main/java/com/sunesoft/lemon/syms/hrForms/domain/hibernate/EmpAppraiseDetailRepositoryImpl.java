package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseDetail;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseDetailRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/7/26.
 */

@Service("empAppraiseDetailRepository")
public class EmpAppraiseDetailRepositoryImpl extends GenericHibernateRepository<EmpAppraiseDetail,Long> implements EmpAppraiseDetailRepository {


}
