package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * Created by zhouz on 2016/6/22.
 */
public class EmpSimpleDto {
    private Long id;

    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 真实姓名
     */
    private String name; // 真实姓名

    /**
     * 登录用户帐号
     */
    private String loginName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
