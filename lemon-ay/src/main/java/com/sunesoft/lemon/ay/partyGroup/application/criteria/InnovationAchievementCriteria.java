package com.sunesoft.lemon.ay.partyGroup.application.criteria;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.EducationDegree;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Education;

import java.util.Date;

/**
 * Created by admin on 2016/9/3.
 */
public class InnovationAchievementCriteria extends PagedCriteria {

    /**
     * 审核流程 步骤
     */
    private String step;

    /**
     * 获奖等级
     */
    private PrizeLeval prizeLeval;

    /**
     * 创造者姓名
     */
    private String creatorName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 文化程度
     */
    private EducationDegree educationDegree;

    /**
     * 竞赛单位
     */
    private String deptName;

    /**
     * 申报开始时间
     */
    private Date beginTime;

    /**
     * 申报结束时间
     */
    private Date endTime;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public PrizeLeval getPrizeLeval() {
        return prizeLeval;
    }

    public void setPrizeLeval(PrizeLeval prizeLeval) {
        this.prizeLeval = prizeLeval;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public EducationDegree getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(EducationDegree educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

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
}
