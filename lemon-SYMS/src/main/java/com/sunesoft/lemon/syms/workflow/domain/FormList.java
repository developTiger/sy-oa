package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhouz on 2016/5/25.
 */
@Entity
@Table(name="syy_oa_fm_form_list")
public class FormList   extends BaseEntity {

    @Column(name="form_kind",columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKind;// 表单种类编号

    @Column(name="form_name",columnDefinition = "varchar(100) DEFAULT NULL")
    private String formName; //表单名称

    @Column(name="description",columnDefinition = "varchar(200) DEFAULT NULL")
    private String description; //表单描述

    @Column(name="form_type")
    private Integer formType;//表单类型   1 静态签核人表单 0 动态签核人表单

    @Column(name="form_version")
    private Integer version;//版本号

    @Column(name="limit_time")
    private Integer limitTime;// 有效期

    @Column(name="is_batch_approved")
    private Boolean isBatchApproved;//批量签核

    @Column(name="form_status")
    private Integer formStatus;//表单状态
    @Column(name="close_form")
    private Boolean closeForm;// 是否可结案

    @Column(name="has_apply_view")
    public Boolean hasApplyView;
    public FormList(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Boolean getIsBatchApproved() {
        return isBatchApproved;
    }

    public void setIsBatchApproved(Boolean isBatchApproved) {
        this.isBatchApproved = isBatchApproved;
    }

    public Integer getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(Integer formStatus) {
        this.formStatus = formStatus;
    }

    public Boolean getCloseForm() {
        return closeForm;
    }

    public void setCloseForm(Boolean closeForm) {
        this.closeForm = closeForm;
    }


    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }
}
