package com.sunesoft.lemon.deanery.projectWorkFlow.application.dto;


/**
 * Created by zy on 2016/8/23.
 */

public class FormAcceptanceProjectFileDto {

    private Long id;

    private String fileId;

    private String fileName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
