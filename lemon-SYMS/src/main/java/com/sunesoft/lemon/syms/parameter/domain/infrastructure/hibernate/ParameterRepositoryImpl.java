package com.sunesoft.lemon.syms.parameter.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.parameter.domain.Parameter;
import com.sunesoft.lemon.syms.parameter.domain.ParameterRepository;
import org.springframework.stereotype.Service;

/**
 * Created by user on 2016/6/2.
 */

@Service("parameterTypeRepository")
public class ParameterRepositoryImpl  extends GenericHibernateRepository<Parameter,Long> implements ParameterRepository {
}
