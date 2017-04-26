package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceRuleSet;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceRuleSetRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/6/30.
 */
@Service("attendanceRuleSetRepository")
public class AttendanceRuleSetRepositoryImpl extends GenericHibernateRepository<AttendanceRuleSet,Long> implements AttendanceRuleSetRepository {
}
