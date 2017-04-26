package com.sunesoft.lemon.deanery.deliverables.application.dto;

import java.util.Date;

/**
 * Created by MJ003 on 2016/10/14.
 */
public class FormDeliverAplyExportDto {

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

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    /**
     *
     *项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /*
    *主要完成单位
    * */
    private String assumeCompany;
    /*
    * 项目长
    * */
    private Long leader;
    /*
    * 组员
    * */
    private String groupMembers;


}
