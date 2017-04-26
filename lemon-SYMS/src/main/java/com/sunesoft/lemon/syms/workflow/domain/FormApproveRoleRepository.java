package com.sunesoft.lemon.syms.workflow.domain;

import java.util.HashMap;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface FormApproveRoleRepository {


    Long save(FormApproveRole formApproveRole);

    void delete(Long id);

    FormApproveRole get(Long id);

    HashMap<Long,String> getApprovers(Long roleId);

}
