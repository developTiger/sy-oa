package com.sunesoft.lemon.ay.partyGroup.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import javax.persistence.Column;

/**
 * Created by admin on 2016/9/5.
 */
public class WorkersProposalCriteria extends PagedCriteria {

    private Integer step;

    /**
     * 提案标题
     */
    private String title;

    /**
     * 申请人
     */
    private String person;

    /**
     * 申请单位
     */
    private String deptName;

    /**
     * 类别
     */
    private String type;

    /**
     * 建议承建部门
     */
    private String undertakeDept;

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUndertakeDept() {
        return undertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        this.undertakeDept = undertakeDept;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
