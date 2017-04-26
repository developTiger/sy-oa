package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.Assessment;
import com.sunesoft.lemon.ay.equipment.domain.AssessmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Service("assessmentRepository")
public class AssessmentRepositoryImpl extends GenericHibernateRepository<Assessment,Long> implements AssessmentRepository{


}
