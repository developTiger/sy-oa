package com.sunesoft.lemon.syms.fileSys.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.fileSys.application.criteria.FileCriteria;
import com.sunesoft.lemon.syms.fileSys.application.dtos.DownloadFileDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FilePathDto;
import com.sunesoft.lemon.syms.fileSys.domain.FilePath;

import java.util.List;

/**
 * Created by zhouz on 2016/7/6.
 */
public interface FileService {
    //上传并添加索引
    public String upload(FileInfoDto fileInfoDto);
    //删除索引
    public CommonResult delete(String fileInfoId);
    //全文检索
    public List<FileInfoDto> GetFileInfo(FileCriteria fileCriteria);

    public PagedResult<FileInfoDto> GetFileInfoPaged(FileCriteria fileCriteria);
    //查询所有目录
    public List<FilePathDto> GetFilePath(Long id);


    //查找部门权限目录
    List<FilePathDto> GetFilePath(Long id,Long deptId);
    //修改文件路径
    public CommonResult Update(FileInfoDto fileInfoDto);

    //增FilePath
    public CommonResult AddFilePath(FilePathDto filePathDto);

    public FilePathDto getFilePathById(Long id );
    //修改FilePath
    public CommonResult UpdateFilePath(FilePathDto filePathDto);
    //删除FilePath
    public CommonResult deleteFilePath(Long Id);


    public DownloadFileDto getFileById(String id);

}
