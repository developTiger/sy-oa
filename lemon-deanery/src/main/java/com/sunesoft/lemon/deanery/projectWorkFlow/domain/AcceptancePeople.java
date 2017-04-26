package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;

/**
 * Created by zy on 2016/9/6.
 */
@Entity
@Table(name = "syy_oa_form_acctime")
public class AcceptancePeople extends BaseEntity {
    @Column(name = "accept_name", columnDefinition = "VARCHAR2(50)")
    private String acceptName;
    @Column(name = "accept_workUnit", columnDefinition = "VARCHAR2(50)")
    private String acceptWorkUnit;
    @Column(name = "accept_position", columnDefinition = "VARCHAR2(50)")
    private String groPosition;
    @Column(name = "accept_positName", columnDefinition = "VARCHAR2(50)")
    private String positiName;
    @Column(name = "accept_persView", columnDefinition = "VARCHAR2(50)")
    private String personalView;


    public String getAcceptWorkUnit() {
        return acceptWorkUnit;
    }

    public void setAcceptWorkUnit(String acceptWorkUnit) {
        this.acceptWorkUnit = acceptWorkUnit;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getAcceptanceName() {
        return acceptName;
    }

    public void setAcceptanceName(String acceptanceName) {
        this.acceptName = acceptanceName;
    }

    public String getAcceptanceWorkUnit() {
        return acceptWorkUnit;
    }

    public void setAcceptanceWorkUnit(String acceptanceWorkUnit) {
        this.acceptWorkUnit = acceptanceWorkUnit;
    }


    public String getPersonalView() {
        return personalView;
    }

    public void setPersonalView(String personalView) {
        this.personalView = personalView;
    }

    public String getGroPosition() {
        return groPosition;
    }

    public void setGroPosition(String groPosition) {
        this.groPosition = groPosition;
    }

    public String getPositiName() {
        return positiName;
    }

    public void setPositiName(String positiName) {
        this.positiName = positiName;
    }
}