package com.sunesoft.lemon.syms.workflow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveList;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zhouz on 2016/5/30.
 */
@Service("formHeaderRepository")
public class FormHeaderRepositoryImpl extends GenericHibernateRepository<FormHeader, Long> implements FormHeaderRepository {

    @Override
    public FormHeader getByKindAndNo(Long formNo, String formKind) {
        Criteria criterion = getSession().createCriteria(FormHeader.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("id", formNo));
        criterion.add(Restrictions.eq("formKind", formKind));
        return (FormHeader) criterion.list().get(0);
    }


    @Override
    public List<FormHeader> getHeaderByParentFormNo(Long parentFormNo) {

        Criteria criterion = DetachedCriteria.forClass(
                FormHeader.class, "fh").getExecutableCriteria(
                getSession());
        criterion.add(Restrictions.eq("isActive", true));


        criterion.add(Restrictions.eq("parentFormNo", parentFormNo));
        return criterion.list();

    }

    @Override
    /**
     * 判断关建字段是否有重复
     *
     * @param title
     * @param upId  操作类型 0 新增 1 修改
     * @return
     */
    public Boolean isExistSameTitle(String formKind, String title, Long upId) {
        if (StringUtils.isNullOrWhiteSpace(title)) {
            return false;
        }
        FormStatus[] formStatus = new FormStatus[]{FormStatus.AP,FormStatus.UA};

        Criteria criterion = getSession().createCriteria(FormHeader.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formKind", formKind));
        criterion.add(Restrictions.eq("summery", title));
        criterion.add(Restrictions.in("formStatus",formStatus));
        List<FormHeader> headers = criterion.list();
        if (upId == null || upId == 0) {
            if (headers == null || headers.size() == 0)
                return false;
            else
                return true;
        } else {
            if (headers == null || headers.size() == 0)
                return false;
            if (headers.size() > 1)
                return true;
            else //==1

                return !headers.get(0).getId().equals(upId);
        }

    }
}
