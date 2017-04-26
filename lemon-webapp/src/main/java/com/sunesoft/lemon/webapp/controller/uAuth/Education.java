package com.sunesoft.lemon.webapp.controller.uAuth;

import java.util.Date;

/**
 * Created by swb on 2016/6/14.
 */
public class Education {
    private Long id;
    private String school;
    private String study;
    private Date graduate;
    private String subject;
    private Integer level;
    private String degree;
    private Date gettime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public Date getGraduate() {
        return graduate;
    }

    public void setGraduate(Date graduate) {
        this.graduate = graduate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Date getGettime() {
        return gettime;
    }

    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }
}
