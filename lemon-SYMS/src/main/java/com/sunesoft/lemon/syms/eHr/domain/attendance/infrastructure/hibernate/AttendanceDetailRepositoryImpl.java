package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceDetailRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/6/28.
 */
@Service("dateInfoRepository")
public class AttendanceDetailRepositoryImpl extends GenericHibernateRepository<AttendanceDetail,Long> implements AttendanceDetailRepository {
}
