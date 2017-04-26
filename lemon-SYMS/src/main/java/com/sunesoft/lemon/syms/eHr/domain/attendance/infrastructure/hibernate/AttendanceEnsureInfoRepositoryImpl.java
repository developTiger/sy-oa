package com.sunesoft.lemon.syms.eHr.domain.attendance.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceEnsureInfo;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceEnsureInfoRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/7/5.
 */
@Service("attendanceEnsureInfoRepository")
public class AttendanceEnsureInfoRepositoryImpl extends GenericHibernateRepository<AttendanceEnsureInfo,Long> implements AttendanceEnsureInfoRepository{
}
