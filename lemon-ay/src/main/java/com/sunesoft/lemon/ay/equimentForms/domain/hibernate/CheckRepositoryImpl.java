package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormCheck;
import com.sunesoft.lemon.ay.equipment.domain.Check;
import com.sunesoft.lemon.ay.equipment.domain.CheckRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Service("checkRepository")
public class CheckRepositoryImpl extends GenericHibernateRepository<Check,Long> implements CheckRepository {


}
