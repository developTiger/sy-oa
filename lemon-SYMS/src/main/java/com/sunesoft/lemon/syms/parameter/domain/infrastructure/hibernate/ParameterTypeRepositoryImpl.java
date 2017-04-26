package com.sunesoft.lemon.syms.parameter.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.parameter.domain.ParameterType;
import com.sunesoft.lemon.syms.parameter.domain.ParameterTypeRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zy on 2016/6/2.
 */
@Service("parameterTypeRepositoryImpl")
public class ParameterTypeRepositoryImpl extends GenericHibernateRepository<ParameterType,Long> implements ParameterTypeRepository {


}
