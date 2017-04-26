package com.sunesoft.lemon.deanery.project.application.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by MJ003 on 2016/8/22.
 */
public class ScientificResearchProjectDto1 {
 /*   private String id;*/
/*    private String applyer;*/


    /**
     * 项目编号
     */
    private String projectNo;


    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 专业类别
     * 单个专业类别保存时，选用编号01,02,03,04,05,06,07
     */
  /*  private String specialtyType;*/
    /**
     * 研究内容，技术经济指标及项目进度安排
     */
    private String projectPlanInfo;

    /**
     * 承担单位
     */
    private String assumeCompany;
    /**
     * 参加单位
     */
    private String joinComopany;

    private String beginTime;

    private String endTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目类型
     * 保存时用：1,2,3代表不同类型
     * 1：项目，2：课题，3：专题
     */
   /* private String projectType;*/

    /**
     * 项目长
     */
    private String leader;
    /**
     * 项目长邮箱
     */
    private String leaderAdress;

    /**
     * 副项目长
     */
    private String deputy;
    /**
     * 副项目长邮箱
     */
    private String deputyAdress;
    /**
     * 组员名称
     */
   /* private String groupMembersName;*/


    /**
     * 所属部门Id
     */
   /* private String deptId;*/

    /**
     * 流程中的状态
     * 插入数据库为数字
     * 1：代表运行
     * 2：代表撤销
     * 3：代表结束
     */
   /* private String projectStatus;*/

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

    public String getProjectPlanInfo() {
        return projectPlanInfo;
    }

    public void setProjectPlanInfo(String projectPlanInfo) {
        this.projectPlanInfo = projectPlanInfo;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public String getJoinComopany() {
        return joinComopany;
    }

    public void setJoinComopany(String joinComopany) {
        this.joinComopany = joinComopany;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDeputy() {
        return deputy;
    }

    public void setDeputy(String deputy) {
        this.deputy = deputy;
    }

   /* public String getGroupMembersName() {
        return groupMembersName;
    }

    public void setGroupMembersName(String groupMembersName) {
        this.groupMembersName = groupMembersName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
*/
 /*   public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }*/

    public String getLeaderAdress() {
        return leaderAdress;
    }

    public void setLeaderAdress(String leaderAdress) {
        this.leaderAdress = leaderAdress;
    }

    public String getDeputyAdress() {
        return deputyAdress;
    }

    public void setDeputyAdress(String deputyAdress) {
        this.deputyAdress = deputyAdress;
    }

/*    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/
}


