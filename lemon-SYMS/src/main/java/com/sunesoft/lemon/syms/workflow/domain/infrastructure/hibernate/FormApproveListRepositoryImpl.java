package com.sunesoft.lemon.syms.workflow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveList;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveListRepository;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import org.springframework.stereotype.Service;


/**
 * Created by zhouz on 2016/5/30.
 */
@Service("formApproveListRepository")
public class FormApproveListRepositoryImpl extends GenericHibernateRepository<FormApproveList,Long> implements FormApproveListRepository {

}
