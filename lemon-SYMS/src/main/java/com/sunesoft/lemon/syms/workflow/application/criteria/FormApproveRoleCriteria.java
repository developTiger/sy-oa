package com.sunesoft.lemon.syms.workflow.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by MJ006 on 2016/6/20.
 */
public class FormApproveRoleCriteria extends PagedCriteria {
    private String roleName;

    private String roleType;

    public String getRoleName() {
        return roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
