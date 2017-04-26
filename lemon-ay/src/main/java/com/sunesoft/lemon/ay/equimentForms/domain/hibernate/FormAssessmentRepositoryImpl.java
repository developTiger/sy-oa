package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessment;
import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.utils.excel.ReadExcel;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Service("formAssessmentRepository")
public class FormAssessmentRepositoryImpl extends GenericHibernateRepository<FormAssessment,Long> implements FormAssessmentRepository {
    @Override
    public List<FormAssessment> getByEquipmentId(Long equipmentId) {
        Criteria criteria = getSession().createCriteria(FormAssessment.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("equipmentId",equipmentId));
        criteria.add(Restrictions.eq("formStatus", FormStatus.AP));
        return criteria.list();
    }
}
