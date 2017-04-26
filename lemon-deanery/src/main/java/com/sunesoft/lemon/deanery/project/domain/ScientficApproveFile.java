package com.sunesoft.lemon.deanery.project.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pxj on 2016/9/30.
 */
@Entity
@Table(name="syy_oa_scientific_approve_file")
public class ScientficApproveFile extends BaseEntity {
    @Column(name="file_name")
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

    @Column(name="file_id")
    private String fileId;
}
