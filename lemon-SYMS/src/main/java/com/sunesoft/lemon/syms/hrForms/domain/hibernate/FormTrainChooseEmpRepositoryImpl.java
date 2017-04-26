package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmpRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
@Service("formTrainChooseEmpRepository")
public class FormTrainChooseEmpRepositoryImpl extends GenericHibernateRepository<FormTrainChooseEmp,Long> implements FormTrainChooseEmpRepository {
    @Override
    public List<FormTrainChooseEmpDto> getAll() {
        String hql = "from FormTrainChooseEmp";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public FormTrainChooseEmp getByFormNo(Long formNo) {

        Criteria criteria = getSession().createCriteria(FormTrainChooseEmp.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormTrainChooseEmp)criteria.uniqueResult();
    }
}
