package com.sunesoft.lemon.deanery.project.domain;

import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public interface ScientificResearchPeojectRepository {
    Long save(ScientificResearchProject scientificResearchProject);

    void delete(Long id);

    ScientificResearchProject get(Long id);

    ScientificResearchProject getByFormNo(Long formNo);

    /**
     * 修改项目状态
     * @param id
     * @param projectStatus 项目状态
     * @return
     */
    Long modifyProjectStatus(Long id,String projectStatus);

    /**
     * 修改项目截止时间
     * @param id
     * @param endTime 截止时间
     * @return
     */
    Long modifyEndTime(Long id,Date endTime);

    /**
     * 根据项目状态统计项目信息
     * @return
     */
    List<Map<String,Object>> projectReportByStatus();

    /**
     * 根据项目类型统计项目信息
     * @return
     */
    List<Map<String,Object>> projectReportByType();

    /**
     * 根据项目单位统计项目信息
     * @return
     */
    List<Map<String,Object>> projectReportByDept();

    /**
     * 根据项目年份统计项目信息
     * @return
     */
    List<Map<String,Object>> projectReportByYear();

    /**
     * 根据相关条件获取待审核信息
     * @param formKind 表单类型
     * @param accessCriteria
     * @return
     */
    List<Map<String,Object>> queryAccessLists(String formKind,AccessCriteria accessCriteria);
}
