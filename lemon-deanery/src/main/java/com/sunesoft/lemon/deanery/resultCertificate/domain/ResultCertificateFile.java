package com.sunesoft.lemon.deanery.resultCertificate.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pxj on 2016/9/28.
 * 成果证书实体
 */
@Entity
@Table(name = "syy_oa_resultcertificate_file")
public class ResultCertificateFile extends BaseEntity {
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
