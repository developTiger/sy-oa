package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@Entity
@Table(name="syy_oa_form_train_file")
public class TrainFile extends BaseEntity {
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
