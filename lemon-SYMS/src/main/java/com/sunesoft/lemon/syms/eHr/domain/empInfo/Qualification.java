package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.hrForms.domain.TrainFile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 资质信息
 * Created by zhouz on 2016/6/14.
 */
@Entity
@Table(name="syy_oa_hr_qualificat")
public class Qualification extends BaseEntity {

    /**
     * 已经改为 颁发时间
     */
    @Column(name = "time")
    private Date creTime;

    /**
     * 资质名称
     */
    @Column(name = "qua_name")
    private String name;

    /**
     * 资质存放路径
     */
    private String url;

    /**
     * 资质编号
     */
    @Column(name = "is_card")
    private String idCard;

    /**
     * 资质简介
     */
    private String brief;

    /**
     * 员工id
     */
    @Column(name = "emp_id")
    private Long empId;
    /**
     * is or is not current
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    /**
     * 文件 id
     */
    @Column(name = "fileId")
    private String fileId;

    /**
     * 文件名
     */
    @Column(name = "fileName")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Qualification() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
