package com.sunesoft.lemon.syms.fileSys.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.fileSys.application.criteria.FileCriteria;
import com.sunesoft.lemon.syms.fileSys.application.dtos.DownloadFileDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FilePathDto;
import com.sunesoft.lemon.syms.fileSys.domain.FileInfos;
import com.sunesoft.lemon.syms.fileSys.domain.FileInfosRepository;
import com.sunesoft.lemon.syms.fileSys.domain.FilePath;
import com.sunesoft.lemon.syms.fileSys.domain.FilePathRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.io.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MJ006 on 2016/7/8.
 */
@Service("fileService")
public class FileServiceImpl extends GenericHibernateFinder implements FileService {
    @Autowired
    FilePathRepository filePathRepository;

    @Autowired
    LuceneIndexManger luceneIndexManager;

    @Override
    public String upload(FileInfoDto fileInfoDto) {
        String id = "";
        //创建索引
        id = luceneIndexManager.createIndex(fileInfoDto).toString();
        return id;
    }

    @Override
    public CommonResult delete(String fileInfoId) {
        try {
            //删除索引
            luceneIndexManager.delete(fileInfoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<FileInfoDto> GetFileInfo(FileCriteria fileCriteria) {
        List<FileInfoDto> list = null;
        //全文检索
        StringBuffer text = new StringBuffer();
        if (fileCriteria.getPublic() != null) {
            text.append("isPublic:" + fileCriteria.getPublic().toString());
        }
        if (fileCriteria.getDeptId() != null && fileCriteria.getDeptId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND deptId:" + fileCriteria.getDeptId().toString());
            } else {
                text.append("deptId:" + fileCriteria.getDeptId().toString());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getId())) {
            if (text.length() > 0) {
                text.append(" AND id:" + fileCriteria.getId().toString());
            } else {
                text.append("id:" + fileCriteria.getId().toString());
            }
        }
        if (fileCriteria.getResourceId() != null && fileCriteria.getResourceId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND resourceId:" + fileCriteria.getResourceId().toString());
            } else {
                text.append("resourceId:" + fileCriteria.getResourceId().toString());
            }
        }
        if (fileCriteria.getUserDefineType() != null) {
            if (text.length() > 0) {
                text.append(" AND userDefineType:" + fileCriteria.getUserDefineType());
            } else {
                text.append("userDefineType:" + fileCriteria.getUserDefineType());
            }
        }
        if (fileCriteria.getUserId() != null && fileCriteria.getUserId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND userId:" + fileCriteria.getUserId());
            } else {
                text.append("userId:" + fileCriteria.getUserId());
            }
        }
        if (fileCriteria.getFilePathId() != null && fileCriteria.getFilePathId() > 0L) {
            if (text.length() > 0) {
                text.append(" AND path:" + fileCriteria.getFilePathId().toString());
            } else {
                text.append("path:" + fileCriteria.getFilePathId().toString());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getKeywords())) {
            if (text.length() > 0) {
                text.append(" AND content:" + fileCriteria.getKeywords().toString().trim());
            } else {
                text.append("content:" + fileCriteria.getKeywords().toString().trim());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getFileName())) {
            if (text.length() > 0) {
                text.append(" AND filename:" + fileCriteria.getFileName().toString().trim());
            } else {
                text.append("filename:" + fileCriteria.getFileName().toString().trim());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getExtensions())) {
            if (text.length() > 0) {
                text.append(" AND extensions:" + fileCriteria.getExtensions().toString().trim());
            } else {
                text.append("extensions:" + fileCriteria.getExtensions().toString().trim());
            }
        }

        list = luceneIndexManager.searchIndex(text.toString());
        return list;
    }

    @Override
    public PagedResult<FileInfoDto> GetFileInfoPaged(FileCriteria fileCriteria) {
        List<FileInfoDto> list = null;
        //全文检索
        StringBuffer text = new StringBuffer();
        if (fileCriteria.getPublic() != null) {
            text.append("isPublic:" + fileCriteria.getPublic().toString());
        }
        if (fileCriteria.getDeptId() != null && fileCriteria.getDeptId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND deptId:" + fileCriteria.getDeptId().toString());
            } else {
                text.append("deptId:" + fileCriteria.getDeptId().toString());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getId())) {
            if (text.length() > 0) {
                text.append(" AND id:" + fileCriteria.getId().toString());
            } else {
                text.append("id:" + fileCriteria.getId().toString());
            }
        }
        if (fileCriteria.getResourceId() != null && fileCriteria.getResourceId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND resourceId:" + fileCriteria.getResourceId().toString());
            } else {
                text.append("resourceId:" + fileCriteria.getResourceId().toString());
            }
        }
        if (fileCriteria.getUserDefineType() != null) {
            if (text.length() > 0) {
                text.append(" AND userDefineType:" + fileCriteria.getUserDefineType());
            } else {
                text.append("userDefineType:" + fileCriteria.getUserDefineType());
            }
        }
        if (fileCriteria.getUserId() != null && fileCriteria.getUserId() != 0L) {
            if (text.length() > 0) {
                text.append(" AND userId:" + fileCriteria.getUserId());
            } else {
                text.append("userId:" + fileCriteria.getUserId());
            }
        }
        if (fileCriteria.getFilePathId() != null && fileCriteria.getFilePathId() > 0L) {
            if (text.length() > 0) {
                text.append(" AND path:" + fileCriteria.getFilePathId().toString());
            } else {
                text.append("path:" + fileCriteria.getFilePathId().toString());
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getKeywords())) {
            if (text.length() > 0) {
                text.append(" AND content:" + fileCriteria.getKeywords().toString().trim());
            } else {
                text.append("content:" + fileCriteria.getKeywords().toString().trim());
            }
        }

        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getExtensions())) {
            if (text.length() > 0) {

                if (fileCriteria.getExtensions().equals("doc")) {
                    text.append(" AND (extensions:doc OR extensions:docx)");
                } else if (fileCriteria.getExtensions().equals("xls")) {
                    text.append(" AND (extensions:xls OR extensions:xlsx)");
                } else
                    text.append(" AND extensions:" + fileCriteria.getExtensions().toString().trim());
            } else {
                if (fileCriteria.getExtensions().equals("doc")) {
                    text.append("(extensions:doc OR extensions:docx)");
                } else if (fileCriteria.getExtensions().equals("xls")) {
                    text.append("(extensions:xls OR extensions:xlsx)");
                } else
                    text.append("extensions:" + fileCriteria.getExtensions().toString().trim());
            }
        } else {
            if (text.length() > 0) {
                text.append(" AND (extensions:doc OR extensions:docx OR extensions:txt OR extensions:xls OR extensions:xlsx OR extensions:pdf )");
            } else {
                text.append("(extensions:doc OR extensions:docx OR extensions:txt OR extensions:xls OR extensions:xlsx OR extensions:pdf )");
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(fileCriteria.getFileName())) {
            if (text.length() > 0) {
                text.append(" AND filename:" + fileCriteria.getFileName().toString().trim());
            } else {
                text.append("filename:" + fileCriteria.getFileName().toString().trim());
            }
        }


        //todo 增加时间排序 倒叙

        return luceneIndexManager.searchIndex(text.toString(), fileCriteria.getPageSize(), fileCriteria.getPageNumber());
    }


    @Override
    public List<FilePathDto> GetFilePath(Long id) {
        List<FilePath> beans;
        List<FilePathDto> list = new ArrayList<>();
        if (id != null && id > 0) {
            beans = filePathRepository.get(id).getChildPaths();
        } else {
            Criteria criterion = getSession().createCriteria(FilePath.class);

            criterion.add(Restrictions.eq("isActive", true));

            criterion.add(Restrictions.eq("hasParent", false));

            beans = criterion.list();
        }
        if (beans != null && beans.size() > 0)
            for (FilePath filePath : beans) {
                FilePathDto filePathDto = new FilePathDto();
                BeanUtils.copyProperties(filePath, filePathDto);
                if (filePath.getParent() != null)
                    filePathDto.setParentId(filePath.getParent().getId());
                list.add(filePathDto);
            }
        return list;

    }

    @Override
    public List<FilePathDto> GetFilePath(Long id, Long deptId) {
        List<FilePath> beans;
        List<FilePathDto> list = new ArrayList<>();
        if (id != null && id > 0) {
            beans = filePathRepository.get(id).getChildPaths();
        } else {
            Criteria criterion = getSession().createCriteria(FilePath.class);

            criterion.add(Restrictions.eq("isActive", true));

            criterion.add(Restrictions.eq("hasParent", false));

            if (deptId != null && deptId > 0) {
                criterion.add(Restrictions.or(Restrictions.eq("belongDept", deptId), Restrictions.eq("isPublis", true)));
            }
            beans = criterion.list();
        }
        if (beans != null && beans.size() > 0)
            for (FilePath filePath : beans) {
                FilePathDto filePathDto = new FilePathDto();
                BeanUtils.copyProperties(filePath, filePathDto);
                if (filePath.getParent() != null)
                    filePathDto.setParentId(filePath.getParent().getId());
                list.add(filePathDto);
            }
        return list;

    }

    @Override
    public CommonResult Update(FileInfoDto fileInfoDto) {
        try {
            //先删除索引
            luceneIndexManager.delete(fileInfoDto.getFilePathId().toString());
            //重新创新索引
            upload(fileInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult AddFilePath(FilePathDto filePathDto) {
        if (filePathDto.getParentId() != null && filePathDto.getParentId() > 0L) {
            FilePath filePath = filePathRepository.get(filePathDto.getParentId());

            FilePath path = new FilePath();
            BeanUtils.copyProperties(filePathDto, path);
            path.setBelongDept(filePath.getBelongDept());
            path.setIsPublis(filePath.getIsPublis());
            path.setPathDesc(filePathDto.getPathName());
            path.setHasParent(true);
            List<FilePath> list = filePath.getChildPaths();
            list.add(path);
            filePath.setChildPaths(list);
            filePathRepository.save(filePath);

        } else {
            FilePath filePath = new FilePath();
            BeanUtils.copyProperties(filePathDto, filePath);
            filePath.setPathDesc(filePathDto.getPathName());
            filePath.setHasParent(false);
            filePathRepository.save(filePath);
        }
        return ResultFactory.commonSuccess();
    }


    public FilePathDto getFilePathById(Long id) {


        FilePath path = filePathRepository.get(id);
        FilePathDto filePathDto = new FilePathDto();
        BeanUtils.copyProperties(path, filePathDto);
        if (path.getParent() != null)
            filePathDto.setParentId(path.getParent().getId());
        return filePathDto;

    }

    @Override
    public CommonResult UpdateFilePath(FilePathDto filePathDto) {

//        if (filePathDto.getParentId() != null && filePathDto.getParentId() > 0L) {
//            FilePath filePath = filePathRepository.get(filePathDto.getParentId());
//            FilePath path = new FilePath();
//            BeanUtils.copyProperties(filePathDto, path);
//            path.setPathDesc( filePathDto.getPathName());
//            path.setHasParent(true);
//            List<FilePath> list = filePath.getChildPaths();
//            list.add(path);
//            filePath.setChildPaths(list);
//            filePathRepository.save(filePath);
//
//        } else {
        FilePath filePath = filePathRepository.get(filePathDto.getId());
        if (filePathDto.getBelongDept() != null) {
            filePath.setBelongDept(filePathDto.getBelongDept());
        }
        if(filePath.getIsPublis()!=null){
            filePath.setIsPublis(filePathDto.getIsPublis());
        }
        filePath.setPathName(filePathDto.getPathName());
        filePath.setPathDesc(filePathDto.getPathName());
//        filePath.setHasParent(false);
        filePathRepository.save(filePath);
//        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult deleteFilePath(Long id) {
        FilePath filePath = filePathRepository.get(id);
        filePath.markAsRemoved();
        filePathRepository.save(filePath);
        return ResultFactory.commonSuccess();
    }

    @Override
    public DownloadFileDto getFileById(String id) {
        FileCriteria criteria = new FileCriteria();
        criteria.setId(id);
        DownloadFileDto downdto = new DownloadFileDto();
        FileInfoDto dto = luceneIndexManager.searchIndexById(id);
        try {
            FileInputStream in = new FileInputStream("D:\\luceneData\\" + dto.getUuidName());
            downdto.setFileName(dto.getFileName());
            downdto.setExtension(dto.getExtensions());
            downdto.setInputStream(in);
            return downdto;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
