package com.sunesoft.lemon.deanery.resultCertificate.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pxj on 2016/9/28.
 * 成果证书获奖人
 */
@Entity
@Table(name = "syy_oa_certificate_people")
public class ResultCertificatePeople extends BaseEntity{
    /**
     * 获奖人名
     */

    @Column(name="people_name")
    private String people_Name;

    /**
     * 获奖人id
     */

    @Column(name="people_id")
    private String people_Id;

    /**
     * 内部外部人员
     * @return
     */
    @Column(name="people_inorout")
    private String people_InOrOut;

    public String getPeople_InOrOut() {
        return people_InOrOut;
    }

    public void setPeople_InOrOut(String people_InOrOut) {
        this.people_InOrOut = people_InOrOut;
    }

    public String getPeople_Name() {
        return people_Name;
    }

    public void setPeople_Name(String people_Name) {
        this.people_Name = people_Name;
    }

    public String getPeople_Id() {
        return people_Id;
    }

    public void setPeople_Id(String people_Id) {
        this.people_Id = people_Id;
    }



}
