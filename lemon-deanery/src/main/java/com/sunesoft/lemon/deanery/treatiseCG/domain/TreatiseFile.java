package com.sunesoft.lemon.deanery.treatiseCG.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pxj on 2016/9/26.
 */
@Entity
@Table(name = "syy_oa_treatise_file")
public class TreatiseFile extends BaseEntity {
    @Column(name="file_name")
    private String fileName;

    @Column(name="file_id")
    private String fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getfileId() {
        return fileId;
    }

    public void setfileId(String fileId) {
        this.fileId = fileId;
    }

    public Long getTreatiseFileid() {
        return treatiseFileid;
    }

    public void setTreatiseFileid(Long treatiseFileid) {
        this.treatiseFileid = treatiseFileid;
    }

    @Column(name = "treatise_fileid")
    private Long treatiseFileid;
}
