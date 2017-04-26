package com.sunesoft.lemon.deanery.project.domain;


import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/22.
 */
@Entity
@Table(name = "syy_oa_form_projectmain")
public class ProjectMainApply extends BaseFormEntity {
  //  private Long id;
    /**
     * 申报标题
     */
    private String  title;


    //申报描述内容
    private  String mainContent;


    //申报开始时间
    private String mainBeginDate;


//构造函数
    public ProjectMainApply() {
        this.setIsActive(true);
    //    this.setQubie(0);
        this.setViewUrl("projectInfo");
    }


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
