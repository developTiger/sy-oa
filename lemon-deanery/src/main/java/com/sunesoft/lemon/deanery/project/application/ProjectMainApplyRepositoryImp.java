package com.sunesoft.lemon.deanery.project.application;

import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchPeojectRepository;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.hrForms.domain.DeptSubAppraise;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
@Service(value = "projectMainApplyRepository")
public class ProjectMainApplyRepositoryImp extends GenericHibernateRepository<ProjectMainApply,Long> implements ProjectMainApplyRepository {

    @Override
    public List<ScientificResearchProject> getSubAppraiseByParentFormNo(Long parentFormNo) {
        Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("parentFormNo", parentFormNo));
        return criterion.list();
    }
}
