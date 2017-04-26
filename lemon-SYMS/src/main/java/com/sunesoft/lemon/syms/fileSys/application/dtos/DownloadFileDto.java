package com.sunesoft.lemon.syms.fileSys.application.dtos;

import java.io.InputStream;

/**
 * Created by zhouz on 2016/7/13.
 */
public class DownloadFileDto {

    private String fileName;


    private InputStream inputStream;

    private String extension;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
