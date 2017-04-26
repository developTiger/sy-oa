package com.sunesoft.lemon.ay.partyGroup.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.NewsType;
import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.ValidationEvent;
import java.util.Date;

/**
 * 宣传管理
 * Created by admin on 2016/9/5.
 */
@Entity
@Table(name = "syy_oa_dq_propa_manage")
public class PropagandaManagement extends BaseEntity {

    public PropagandaManagement(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }


    /**
     * 审核状态:
     * 待签核
     * 签核中
     * 已完成
     */
    @Column(name = "pro_manage_status")
    private String formstatus;

    /**
     * 发表时间
     */
    @Column(name = "time")
    private Date time;

    /**
     * 文章标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 单位
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 新闻类别
     */
    @Column(name = "newsType")
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
