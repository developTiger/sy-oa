package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.Attendance;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceDetailRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceRepository;
import org.springframework.stereotype.Service;

/**
 * Created by MJ006 on 2016/6/29.
 */
@Service("AttendanceRepository")
public class AttendanceRepositoryImpl extends GenericHibernateRepository<Attendance,Long> implements AttendanceRepository {

}
