package com.sunesoft.lemon.ay.partyGroup.application.criteria;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by admin on 2016/9/2.
 */
public class WorkProjectCriteria extends PagedCriteria {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 竞赛类别
     */
    private String competitionType;

    /**
     * 竞赛负责人
     */
    private String leader;

    /**
     * 竞赛单位
     */
    private String deptName;

    /**
     * 申报 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


}
