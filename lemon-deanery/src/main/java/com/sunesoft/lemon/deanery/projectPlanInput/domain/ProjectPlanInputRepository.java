package com.sunesoft.lemon.deanery.projectPlanInput.domain;

import com.sunesoft.lemon.fr.results.CommonResult;

import java.util.List;

/**
 * Created by pxj on 2016/9/12.
 */
public interface ProjectPlanInputRepository {
    Long save(ProjectPlanInputDate projectPlanInputDate);

    ProjectPlanInputDate get(Long planId);

    List<ProjectPlanInputDate> queryProjectPlan();

    Long getEmployeeId(String email);

    List<ProjectPlanInputDate> queryApproveProjectPlan(String id);

    ProjectPlanInputDate getByProjectNo(String projectNo);

    void updateProjectPlanState(String projectNumber,String number);

    List<ProjectPlanInputDate> getAllProjectPlan();

  /* void updateProjectPlan(ProjectPlanInputDate projectPlanInputDate);*/
}
