package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.RuleDate;
import com.sunesoft.lemon.syms.eHr.domain.attendance.RuleDateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/6/30.
 */
@Service("ruleDateRepository")
public class RuleDateRepositoryImpl extends GenericHibernateRepository<RuleDate,Long> implements RuleDateRepository {
}
