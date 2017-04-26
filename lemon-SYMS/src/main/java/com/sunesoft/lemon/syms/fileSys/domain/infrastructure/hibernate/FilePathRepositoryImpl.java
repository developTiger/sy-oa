package com.sunesoft.lemon.syms.fileSys.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.fileSys.domain.FilePath;
import com.sunesoft.lemon.syms.fileSys.domain.FilePathRepository;
import org.springframework.stereotype.Service;

/**
 * Created by MJ006 on 2016/7/8.
 */
@Service("FilePathRepository")
public class FilePathRepositoryImpl extends GenericHibernateRepository<FilePath,Long> implements FilePathRepository {
}
