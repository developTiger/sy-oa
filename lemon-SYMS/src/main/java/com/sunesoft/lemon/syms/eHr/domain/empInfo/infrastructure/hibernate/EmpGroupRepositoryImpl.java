package com.sunesoft.lemon.syms.eHr.domain.empInfo.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmpGroup;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmpGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiazl on 2016/5/12.
 */
@Service("empGroupRepository")
public class EmpGroupRepositoryImpl extends GenericHibernateRepository<EmpGroup,Long> implements EmpGroupRepository {


    @Override
	public List<EmpGroup> getByIds(List<Long> ids) {
		// TODO Auto-generated method stub

		return null;
	}
 
}
