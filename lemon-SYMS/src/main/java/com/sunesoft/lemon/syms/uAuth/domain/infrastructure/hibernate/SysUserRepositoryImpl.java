package com.sunesoft.lemon.syms.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysUser;
import com.sunesoft.lemon.syms.uAuth.domain.SysUserRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysUserRepository")
public class SysUserRepositoryImpl extends GenericHibernateRepository<SysUser, Long> implements SysUserRepository {
}
