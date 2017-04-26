package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.syms.uAuth.domain.SysRole;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface FormListRepository {


    Long save(FormList formList);

    void delete(Long roleId);

    FormList get(Long inventorId);


    FormList getByFormKind(String formKind);

}
