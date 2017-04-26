package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by xiazl on 2016/6/15.
 */
public class FamilyDto {
    /**
     * id of emp
     */
    private Long empId;

    private Long id;
    /**
     * 称谓
     */
    private String label;//称谓
    /**
     * 姓名
     */
    private String name;//姓名
    /**
     * 0代表男的，1代表女的
     */
    private int sex;//0代表男的，1代表女的
    /**
     * 出生日期
     */
    private String strBirthday;//出生日期
    /**
     * 住址
     */
    private String address;//住址
    /**
     * 工作单位
     */
    private String company;//工作单位

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStrBirthday() {
        return strBirthday;
    }

    public void setStrBirthday(String strBirthday) {
        this.strBirthday = strBirthday;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
