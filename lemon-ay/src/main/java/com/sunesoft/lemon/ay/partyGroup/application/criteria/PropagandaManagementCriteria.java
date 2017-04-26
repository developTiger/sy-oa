package com.sunesoft.lemon.ay.partyGroup.application.criteria;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.NewsType;
import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/5.
 */
public class PropagandaManagementCriteria extends PagedCriteria {

    /**
     * 作者
     */
    private String author;

    /**
     * 文章标题
     */
    private String title;


    /**
     * 单位
     */
    private String unit;

    /**
     * 新闻类别
     */
    private String newsType;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 审核状态:
     * 通过 AP
     * 不通过  UA
     */
    private FormStatus formStatus;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
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

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }
}
