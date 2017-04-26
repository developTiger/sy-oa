package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/15.
 */
public class QualificationDto {
    /**
     * id of emp
     */
    private Long empId;

    private Long id;
    /**
     * 资质名称
     */
    private String name;
    /**
     * 已经改为 考核项目
     */
    private String idCard;
    /**
     * 存放路径
     */
    private String url;
    /**
     * 已经改为 证书编号
     */
    private String brief;
    /**
     * 是否是当前状态
     */
    private Boolean isCurrent;
    /**
     * 已经改为 颁发时间
     */
    private Date creTime;
    /**
     *  最近修改时间
     */
    private Date lastUpdateTime;

    /**
     * 文件上传 id
     */
    private String  fileId;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public QualificationDto(){
        this.isCurrent=true;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
}
