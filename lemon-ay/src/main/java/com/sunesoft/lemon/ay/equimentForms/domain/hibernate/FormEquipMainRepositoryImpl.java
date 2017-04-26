package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipMain;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipMainRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/17.
 */
@Service("formEquipMainRepository")
public class FormEquipMainRepositoryImpl extends GenericHibernateRepository<FormEquipMain,Long> implements FormEquipMainRepository {


}