package com.sunesoft.lemon.syms.fileSys.domain;

/**
 * Created by MJ006 on 2016/7/8.
 */
public interface FilePathRepository {
    Long save(FilePath filePath);
    FilePath get(Long id);
    void delete(Long id);
}
