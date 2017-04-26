package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceSummery;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceSummeryRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/6/28.
 */
@Service("workSummeryRepository")
public class AttendanceSummeryRepositoryImpl extends GenericHibernateRepository<AttendanceSummery,Long> implements AttendanceSummeryRepository {


}
