package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import java.util.Date;

/**
 * Created by admin on 2016/12/29.
 */
public class PropaManagementModel {

    private String title;
    private String author;
    private String deptName;
    private String newsType;
    private Date time;
    private String formStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }
}
