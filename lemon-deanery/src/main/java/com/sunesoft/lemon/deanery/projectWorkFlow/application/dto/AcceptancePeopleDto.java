package com.sunesoft.lemon.deanery.projectWorkFlow.application.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zy on 2016/9/6.
 */
public class AcceptancePeopleDto {

    private String  acceptanceName;
    private String  acceptanceWorkUnit;
    private String  groupPosition;
    private String  positionName;
    private String  personalView;


    public String getAcceptanceName() {
        return acceptanceName;
    }

    public void setAcceptanceName(String acceptanceName) {
        this.acceptanceName = acceptanceName;
    }

    public String getAcceptanceWorkUnit() {
        return acceptanceWorkUnit;
    }

    public void setAcceptanceWorkUnit(String acceptanceWorkUnit) {
        this.acceptanceWorkUnit = acceptanceWorkUnit;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPersonalView() {
        return personalView;
    }

    public void setPersonalView(String personalView) {
        this.personalView = personalView;
    }

    public String getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(String groupPosition) {
        this.groupPosition = groupPosition;
    }
}
