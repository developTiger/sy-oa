package com.sunesoft.lemon.deanery.project.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/27.
 * 搜索条件类
 */
public class ScientificResearchProjectCriteria extends PagedCriteria {


    private Long approverId;

    /*
     * 项目编号
     */
    private String projectNo;

    /*
     * 项目名称
     */
    private String projectName;

    /*
    * 承担单位
    * */
    private String assumeCompany;

    /*
     * 项目类型
     */
    private String projectType;

    /**
     * 项目状态
     */
    private String projectStatus;

    private String specialtyType;

    /**
     * 年度
     * @return
     */
    private String niandu_Str;

    public String getNiandu_Str() {
        return niandu_Str;
    }

    public void setNiandu_Str(String niandu_Str) {
        this.niandu_Str = niandu_Str;
    }

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    private  Date niandu;


    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }
}
