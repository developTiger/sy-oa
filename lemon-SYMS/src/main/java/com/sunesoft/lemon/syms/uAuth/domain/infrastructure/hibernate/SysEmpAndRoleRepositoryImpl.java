package com.sunesoft.lemon.syms.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysEmpAndRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysEmpAndRoleRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysRoleRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysEmpAndRoleRepository")
public class SysEmpAndRoleRepositoryImpl extends GenericHibernateRepository<SysEmpAndRole,Long> implements SysEmpAndRoleRepository {


    @Override
    public void delete(List<Long> ids) {
       if(ids!=null&&ids.size()>0){
           for(Long id:ids){
               delete(id);//考虑到之前使用的也是物理删除
           }
       }
    }
}
