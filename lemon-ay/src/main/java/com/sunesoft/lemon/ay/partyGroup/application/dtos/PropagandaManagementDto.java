package com.sunesoft.lemon.ay.partyGroup.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.NewsType;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/5.
 */
public class PropagandaManagementDto {


    /**
     * 审核状态
     */
    private String formstatus;

    /**
     * 发表时间
     */
    private Date time;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 单位
     */
    private String unit;

    /**
     * 新闻类别
     */
    private String newsType;

    public String getFormstatus() {
        return formstatus;
    }

    public void setFormstatus(String formstatus) {
        this.formstatus = formstatus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

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
}
