package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by xiazl on 2016/6/30.
 */
@Service("holidayInfoRepository")
public class HolidayInfoRepositoryImpl extends GenericHibernateRepository<HolidayInfo,Long> implements HolidayInfoRepository{

}
