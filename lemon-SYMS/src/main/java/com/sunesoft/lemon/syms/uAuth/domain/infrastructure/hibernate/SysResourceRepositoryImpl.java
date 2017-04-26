package com.sunesoft.lemon.syms.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysResource;
import com.sunesoft.lemon.syms.uAuth.domain.SysResourceRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysResourceRepository")
public class SysResourceRepositoryImpl extends GenericHibernateRepository<SysResource,Long> implements SysResourceRepository {

}
