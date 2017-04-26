package com.sunesoft.lemon.syms.fileSys.domain;

/**
 * Created by MJ006 on 2016/7/8.
 */
public interface FileInfosRepository {
    Long save(FileInfos fileInfos);
    FileInfos get(Long Id);
    void delete(Long Id);
}
