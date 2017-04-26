package com.sunesoft.lemon.ay.partyGroup.application.criteria;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by admin on 2016/9/2.
 */
public class FormWorkAchievementCriteria extends PagedCriteria {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 竞赛类别
     */
    private CompetitionType competitionType;

    /**
     * 竞赛负责人
     */
    private String leader;

    /**
     * 竞赛单位
     */
    private String deptName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
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



