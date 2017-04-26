package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhouz on 2016/6/14.
 */
@Entity
@Table(name="syy_oa_hr_family")
public class Family  extends BaseEntity{


    /**
     * 称谓
     */
    private String label;

    /**
     * 姓名
     */
    @Column(name = "fam_name")
    private String name;

    /**
     * 0代表男的，1代表女的
     */
    private int sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 住址
     */
    private String address;

    /**
     * 工作单位
     */
    private String company;

    /**
     * 员工id
     */
    @Column(name = "emp_id")
    private Long empId;


    public Family() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}
