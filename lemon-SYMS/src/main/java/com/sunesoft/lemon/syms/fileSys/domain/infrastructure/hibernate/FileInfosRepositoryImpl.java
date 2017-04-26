package com.sunesoft.lemon.syms.fileSys.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.fileSys.domain.FileInfosRepository;
import com.sunesoft.lemon.syms.fileSys.domain.FileInfos;
import org.springframework.stereotype.Service;

/**
 * Created by MJ006 on 2016/7/8.
 */
@Service("FileInfosRepository")
public class FileInfosRepositoryImpl extends GenericHibernateRepository<FileInfos,Long> implements FileInfosRepository {

}
