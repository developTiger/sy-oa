package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface FormHeaderRepository {
    Long save(FormHeader formHeader);

    void delete(Long formHeadId);

    FormHeader get(Long formHeadId);

    FormHeader getByKindAndNo(Long formNo,String formKind);

    public List<FormHeader> getHeaderByParentFormNo(Long parentFormNo);

    /**
     * 判断关建字段是否有重复
     *
     * @param title
     * @param upId  操作类型 0 新增 1 修改
     * @return
     */
    public Boolean isExistSameTitle(String formKind, String title, Long upId) ;

}
