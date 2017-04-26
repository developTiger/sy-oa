package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormEquiStatus;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipStatusRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/16.
 */
@Service("formEquipStatusRepository")
public class FormEquipStatusRepositoryImpl extends GenericHibernateRepository<FormEquiStatus,Long> implements FormEquipStatusRepository {
}
