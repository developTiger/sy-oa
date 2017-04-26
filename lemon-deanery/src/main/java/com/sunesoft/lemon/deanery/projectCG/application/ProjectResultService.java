package com.sunesoft.lemon.deanery.projectCG.application;

import com.sunesoft.lemon.deanery.projectCG.application.criteria.ProjectResultCriteria;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public interface ProjectResultService  extends FormService<ProjectResult,ProjectResultDto> {

     public ProjectResultDto getById(Long id);
     public ProjectResultDto getProjectResultByFormNo(Long fromNo);
     public Long addOrUpdateProjectResult(ProjectResultDto projectResultDto);

     public boolean deleteProjectResult(Long[] ids);

     public PagedResult<ProjectResultDto> getCommonDrivers(ProjectResultCriteria projectResultCriteria) throws UnsupportedEncodingException;

     public List<Map<String,Object>> projectCGReport();


     public CommonResult addOrUpdateProjectResult2(ProjectResultDto projectResultDto);
     public CommonResult addOrUpdateProjectResult3(ProjectResultDto projectResultDto, ApproveFormDto dto);
}
