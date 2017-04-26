package com.sunesoft.lemon.deanery.delayflow.domain.hibernate;

import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApplyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by swb on 2016/8/25.
 */
@Service(value = "formDelayApplyRepository")
public class FormDelayApplyRepositoryImpl extends GenericHibernateRepository<FormDelayApply,Long> implements FormDelayApplyRepository{
}
