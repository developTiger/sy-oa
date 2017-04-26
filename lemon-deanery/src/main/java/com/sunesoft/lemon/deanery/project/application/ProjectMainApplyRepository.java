package com.sunesoft.lemon.deanery.project.application;

import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;
import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.syms.hrForms.domain.DeptSubAppraise;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public interface ProjectMainApplyRepository {

    Long save(ProjectMainApply projectMainApply);

    void delete(Long id);

    ProjectMainApply get(Long id);

    List<ScientificResearchProject> getSubAppraiseByParentFormNo(Long parentFormNo);
}
