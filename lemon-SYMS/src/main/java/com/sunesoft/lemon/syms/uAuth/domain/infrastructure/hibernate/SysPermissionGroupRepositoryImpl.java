package com.sunesoft.lemon.syms.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysPermissionGroup;
import com.sunesoft.lemon.syms.uAuth.domain.SysPermissionGroupRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysPermissionGroupRepository")
public class SysPermissionGroupRepositoryImpl extends GenericHibernateRepository<SysPermissionGroup,Long> implements SysPermissionGroupRepository {

    @Override
    public List<SysPermissionGroup> getAllName() {
        String hql=" from SysPermissionGroup";
        Query query=getSession().createQuery(hql);
        return query.list();
    }
}
