package com.sunesoft.lemon.webapp.model.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.AchiPatent;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.AchiTransformation;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;

import java.util.Date;

/**
 * Created by admin on 2017/3/9.
 */
public class InnovationAchiCountDtoModel {

    private String applyUnit;

    private String projectName;

    private String creatorName;
    private String prizeLeval;

    private String achiCreateTime;

    private String achiTransformation;

    private String achiPatent;

    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getPrizeLeval() {
        return prizeLeval;
    }

    public void setPrizeLeval(String prizeLeval) {
        this.prizeLeval = prizeLeval;
    }

    public String getAchiCreateTime() {
        return achiCreateTime;
    }

    public void setAchiCreateTime(String achiCreateTime) {
        this.achiCreateTime = achiCreateTime;
    }

    public String getAchiTransformation() {
        return achiTransformation;
    }

    public void setAchiTransformation(String achiTransformation) {
        this.achiTransformation = achiTransformation;
    }

    public String getAchiPatent() {
        return achiPatent;
    }

    public void setAchiPatent(String achiPatent) {
        this.achiPatent = achiPatent;
    }
}
