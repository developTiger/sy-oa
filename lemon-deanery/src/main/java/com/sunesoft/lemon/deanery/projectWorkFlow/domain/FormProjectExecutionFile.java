package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zy
 */
@Entity
@Table(name = "syy_oa_form_Execution_file")
public class FormProjectExecutionFile extends BaseEntity {

    @Column(name="file_name")
    private String fileName1;

    @Column(name="file_id")
    private String fileId1;

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public String getFileId1() {
        return fileId1;
    }

    public void setFileId1(String fileId1) {
        this.fileId1 = fileId1;
    }
}
