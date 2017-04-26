package com.sunesoft.lemon.deanery.projectWorkFlow.domain.hibernate;

import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApplyRepository;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApplyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
@Service(value = "formProjectApplyRepository")
public class FormProjectApplyRepositoryImpl extends GenericHibernateRepository<FormProjectApply,Long> implements FormProjectApplyRepository {

}
