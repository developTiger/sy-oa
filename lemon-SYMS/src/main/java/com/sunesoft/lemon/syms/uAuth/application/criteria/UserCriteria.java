package com.sunesoft.lemon.syms.uAuth.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by zhouz on 2016/5/19.
 */
@Deprecated
public class UserCriteria extends PagedCriteria {

    private String userName;

    private String loginName;

    private String phone;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
