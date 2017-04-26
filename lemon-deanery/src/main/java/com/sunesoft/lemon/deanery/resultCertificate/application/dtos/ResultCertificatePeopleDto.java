package com.sunesoft.lemon.deanery.resultCertificate.application.dtos;

/**
 * Created by pxj on 2016/9/28.
 * 获奖人dto
 */
public class ResultCertificatePeopleDto {
    /**
     * 人员id
     */
    private String people_Id;

    /**
     * 人员名称
     */
    private String people_Name;



    /**\
     * 内部、外部
     */
    private String people_InOrOut;

    public String getPeople_InOrOut() {
        return people_InOrOut;
    }

    public void setPeople_InOrOut(String people_InOrOut) {
        this.people_InOrOut = people_InOrOut;
    }

    public String getPeople_Id() {
        return people_Id;
    }

    public void setPeople_Id(String people_Id) {
        this.people_Id = people_Id;
    }

    public String getPeople_Name() {
        return people_Name;
    }

    public void setPeople_Name(String people_Name) {
        this.people_Name = people_Name;
    }



}
