package com.sunesoft.lemon.deanery.projectWorkFlow.domain.hibernate;

import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptanceRepository;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecutionRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zy on 2016/8/26 0026.
 */
@Service(value = "formProjectAcceptanceRepository")
public class FormProjectAcceptanceRepositoryImpl extends GenericHibernateRepository<FormProjectAcceptance,Long> implements FormProjectAcceptanceRepository {


}
