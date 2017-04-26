package com.sunesoft.lemon.syms.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysRoleRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysRoleRepository")
public class SysRoleRepositoryImpl extends GenericHibernateRepository<SysRole, Long> implements SysRoleRepository {

    @Override
    public List<SysRole> gets(String[] inventorIds) {
        List<Long> list=new ArrayList<>();
        if(inventorIds!=null&&inventorIds.length>0){
            for(String s:inventorIds){
                list.add(Long.parseLong(s));
            }
        }
        Criteria criteria = getSession().createCriteria(SysRole.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.in("id", list));
        return criteria.list() == null ? Collections.EMPTY_LIST : criteria.list();
    }

    @Override
    public List<SysRole> getAllRoleName() {
        String hql = " from SysRole ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
