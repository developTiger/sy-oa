package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/27.
 * 搜索条件类
 */
public class ScientificRPKUCriteria extends PagedCriteria {


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
     * 申请部门
     */
    private String projectStatus;

    /**
     * 年度
     * @return
     */
    private String niandu_Str;
    private String projectYXStatus;

    public String getProjectYXStatus() {
        return projectYXStatus;
    }

    public void setProjectYXStatus(String projectYXStatus) {
        this.projectYXStatus = projectYXStatus;
    }

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
    /*
    * 申请部门
    * */

    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

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


    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
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
}
