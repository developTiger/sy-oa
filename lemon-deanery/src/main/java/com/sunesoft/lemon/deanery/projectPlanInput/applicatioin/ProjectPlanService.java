package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanOutput;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by pxj on 2016/9/9.
 */
public interface ProjectPlanService {
    /**
     * 导入保存
     * @param list
     * @return
     */
    CommonResult save(List<ProjectPlanInput> list);

    /**
     * 立项导入到项目计划库
     */
     CommonResult savaProjectApprove(List<ProjectPlanInput> list);
    /**
     * 项目计划列表
     * @param projectPlanInput
     * @return
     * @throws UnsupportedEncodingException
     */
    public PagedResult<ProjectPlanInput> getProjectPlan(ProjectPlanInput projectPlanInput) throws UnsupportedEncodingException;
    PagedResult<ProjectPlanInput> getProjectPlan1(ProjectPlanInput projectPlanInput);
    /**
     * 查询项目计划
     * @return
     */
    public List<ProjectPlanInput> queryProjectPlan();

    /**
     * 立项查询项目计划
     * @return
     */
    public List<ProjectPlanInput> queryApproveProjectPlan(String id);

    /**
     * 查询项目
     * @param planId
     * @return
     */
    public ProjectPlanInput getByIdProjectPlanInput(Long planId);

    Long addOrUpdateProjectPlan(ProjectPlanInput projectPlanInput);
   //导出
    List<ProjectPlanOutput> getAllPlan();
    ProjectPlanInput getProjectPlanByProjectNo(String projectNo);

    CommonResult updateProjectPlanState(String projectNumber,String number);
}
