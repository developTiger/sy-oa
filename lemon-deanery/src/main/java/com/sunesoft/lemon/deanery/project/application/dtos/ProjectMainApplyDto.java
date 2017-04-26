package com.sunesoft.lemon.deanery.project.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ProjectMainApplyDto extends BaseFormEntity {
    /**
     * 申报标题
     */
    private String  title;


    //申报描述内容
    private  String mainContent;


    //申报开始时间
    private String  mainBeginDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getMainBeginDate() {
        return mainBeginDate;
    }

    public void setMainBeginDate(String mainBeginDate) {
        this.mainBeginDate = mainBeginDate;
    }
}
