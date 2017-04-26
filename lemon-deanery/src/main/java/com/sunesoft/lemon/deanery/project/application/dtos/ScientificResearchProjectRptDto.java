package com.sunesoft.lemon.deanery.project.application.dtos;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ScientificResearchProjectRptDto {
    /**
     * 根据项目状态统计项目信息
     * project_status  项目状态
     * project_num 项目数量
     */
    private List<Map<String,Object>> projectRptByStatus;
    /**
     * 根据项目类型统计项目信息
     * project_type 项目类型
     * project_num 项目数量
     */
    private List<Map<String,Object>> projectRptByType;
    /**
     * 根据项目单位统计项目信息
     * department 部门ID
     * dept_name 部门名称
     * project_num 项目数量
     */
    private List<Map<String,Object>> projectRptByDept;
    /**
     * 根据项目年份统计项目信息
     * year 年份
     * project_num 项目数量
     */
    private List<Map<String,Object>> projectRptByYear;

    public List<Map<String, Object>> getProjectRptByStatus() {
        return projectRptByStatus;
    }

    public void setProjectRptByStatus(List<Map<String, Object>> projectRptByStatus) {
        this.projectRptByStatus = projectRptByStatus;
    }

    public List<Map<String, Object>> getProjectRptByType() {
        return projectRptByType;
    }

    public void setProjectRptByType(List<Map<String, Object>> projectRptByType) {
        this.projectRptByType = projectRptByType;
    }

    public List<Map<String, Object>> getProjectRptByDept() {
        return projectRptByDept;
    }

    public void setProjectRptByDept(List<Map<String, Object>> projectRptByDept) {
        this.projectRptByDept = projectRptByDept;
    }

    public List<Map<String, Object>> getProjectRptByYear() {
        return projectRptByYear;
    }

    public void setProjectRptByYear(List<Map<String, Object>> projectRptByYear) {
        this.projectRptByYear = projectRptByYear;
    }
}
