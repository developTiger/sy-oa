package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/16.
 */
public class EduCriteria extends PagedCriteria {
    /**
     * 毕业学校
     */
    private String school;//毕业学校
    /**
     * 学习专业
     */
    private String major;//学习专业
    /**
     * 学位
     */
    private String degree;//学位

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
