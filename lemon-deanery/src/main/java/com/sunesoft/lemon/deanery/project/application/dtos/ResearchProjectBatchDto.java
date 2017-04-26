package com.sunesoft.lemon.deanery.project.application.dtos;

import com.sunesoft.lemon.fr.results.PagedResult;

/**
 * Created by user on 2016/8/5.
 */
public class ResearchProjectBatchDto{
    private String applyer;
    private String applyer_name;
    private String approve_status;
    private String app_name;
    private String app_user_id;
    private String begin_date;
    private String blong_dept;
    private String blong_dept_name;
    private String cl_step;
    private String create_datetime;
    private String current_app_type;
    private String dept_id;
    private String dept_name;
    private String form_kind;
    private String form_kind_name;
    private String form_status;
    private String has_apply_view;
    private String id;
    private String is_active;
    private String is_step_complete;
    private String last_update_time;
    private String life_cycle;
    private String provious_approver;
    private String view_url;

    public ResearchProjectBatchDto() {

    }

    public ResearchProjectBatchDto(String applyer, String applyer_name, String approve_status, String app_name, String app_user_id, String begin_date, String blong_dept, String blong_dept_name, String cl_step, String create_datetime, String current_app_type, String dept_id, String dept_name, String form_kind, String form_kind_name, String form_status, String has_apply_view, String id, String is_active, String is_step_complete, String last_update_time, String life_cycle, String provious_approver, String view_url) {
        this.applyer = applyer;
        this.applyer_name = applyer_name;
        this.approve_status = approve_status;
        this.app_name = app_name;
        this.app_user_id = app_user_id;
        this.begin_date = begin_date;
        this.blong_dept = blong_dept;
        this.blong_dept_name = blong_dept_name;
        this.cl_step = cl_step;
        this.create_datetime = create_datetime;
        this.current_app_type = current_app_type;
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.form_kind = form_kind;
        this.form_kind_name = form_kind_name;
        this.form_status = form_status;
        this.has_apply_view = has_apply_view;
        this.id = id;
        this.is_active = is_active;
        this.is_step_complete = is_step_complete;
        this.last_update_time = last_update_time;
        this.life_cycle = life_cycle;
        this.provious_approver = provious_approver;
        this.view_url = view_url;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public String getApplyer_name() {
        return applyer_name;
    }

    public void setApplyer_name(String applyer_name) {
        this.applyer_name = applyer_name;
    }

    public String getApprove_status() {
        return approve_status;
    }

    public void setApprove_status(String approve_status) {
        this.approve_status = approve_status;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(String app_user_id) {
        this.app_user_id = app_user_id;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getBlong_dept() {
        return blong_dept;
    }

    public void setBlong_dept(String blong_dept) {
        this.blong_dept = blong_dept;
    }

    public String getBlong_dept_name() {
        return blong_dept_name;
    }

    public void setBlong_dept_name(String blong_dept_name) {
        this.blong_dept_name = blong_dept_name;
    }

    public String getCl_step() {
        return cl_step;
    }

    public void setCl_step(String cl_step) {
        this.cl_step = cl_step;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getCurrent_app_type() {
        return current_app_type;
    }

    public void setCurrent_app_type(String current_app_type) {
        this.current_app_type = current_app_type;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getForm_kind() {
        return form_kind;
    }

    public void setForm_kind(String form_kind) {
        this.form_kind = form_kind;
    }

    public String getForm_kind_name() {
        return form_kind_name;
    }

    public void setForm_kind_name(String form_kind_name) {
        this.form_kind_name = form_kind_name;
    }

    public String getForm_status() {
        return form_status;
    }

    public void setForm_status(String form_status) {
        this.form_status = form_status;
    }

    public String getHas_apply_view() {
        return has_apply_view;
    }

    public void setHas_apply_view(String has_apply_view) {
        this.has_apply_view = has_apply_view;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_step_complete() {
        return is_step_complete;
    }

    public void setIs_step_complete(String is_step_complete) {
        this.is_step_complete = is_step_complete;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getLife_cycle() {
        return life_cycle;
    }

    public void setLife_cycle(String life_cycle) {
        this.life_cycle = life_cycle;
    }

    public String getProvious_approver() {
        return provious_approver;
    }

    public void setProvious_approver(String provious_approver) {
        this.provious_approver = provious_approver;
    }

    public String getView_url() {
        return view_url;
    }

    public void setView_url(String view_url) {
        this.view_url = view_url;
    }
}
