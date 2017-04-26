package com.sunesoft.lemon.deanery.treatiseCG.application.dtos;

/**
 * Created by pxj on 2016/9/26.
 */
public class TreatiseFileDto {
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

    private String fileName;

    private String fileId;
}
