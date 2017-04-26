package com.sunesoft.lemon.syms.eHr.domain.notice.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.notice.Notice;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeRepository")
public class NoticeRepositoryImpl extends GenericHibernateRepository<Notice,Long> implements NoticeRepository {


    @Override
    public List<Notice> getByIds(List<Long> ids) {

        return null;
    }
}
