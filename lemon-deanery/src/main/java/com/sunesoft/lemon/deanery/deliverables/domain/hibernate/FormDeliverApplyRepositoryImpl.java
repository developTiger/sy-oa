package com.sunesoft.lemon.deanery.deliverables.domain.hibernate;

import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApplyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by swb on 2016/8/30.
 */
@Service(value = "FormDeliverApplyRepository")
public class FormDeliverApplyRepositoryImpl extends GenericHibernateRepository<FormDeliverApply,Long> implements FormDeliverApplyRepository{

}
