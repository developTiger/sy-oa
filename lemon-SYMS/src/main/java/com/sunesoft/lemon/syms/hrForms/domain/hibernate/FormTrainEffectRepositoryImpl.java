package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainEffect;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainEffectRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@Service("formTrainEffectRepository")
public class FormTrainEffectRepositoryImpl extends GenericHibernateRepository<FormTrainEffect,Long> implements FormTrainEffectRepository {
    @Override
    public FormTrainEffect getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormTrainEffect.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormTrainEffect)criteria.uniqueResult();
    }
}
