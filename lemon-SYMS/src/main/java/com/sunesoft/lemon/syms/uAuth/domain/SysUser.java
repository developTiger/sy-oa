package com.sunesoft.lemon.syms.uAuth.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.utils.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zhouz on 2016/5/19.
 */
@Deprecated
@Entity
@Table(name = "syy_oa_sys_user")
public class SysUser extends BaseEntity {

    /**
     * 系统用户状态
     */
    private int status; // 0代表未禁用,1代表禁用
    /**
     * 系统用户等级
     */
    private int levels; // 等级

    @Column(name = "user_name", nullable = false)
    private String loginName; // 登录用户帐号

    @Column(name = "real_name")
    private String name; // 真实姓名

    private String password; // 登录密码

    private String alt; // 加密规则

    private String mobile; // 手机

    private String email; // 邮箱

    private String qq; // qq

    private String phone; // 座机

    private String photo; // 照片

    private String brief; // 简介


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    private Date lastLoginTime; // 账号最后登录时间

    @Column(name = "login_count", columnDefinition = "int default 0")
    private int loginCount; // 账号登录次数


    @ManyToMany
    @JoinTable(name = "syy_oa_sys_user_to_role", inverseJoinColumns = @JoinColumn(name = "role_id"), joinColumns = @JoinColumn(name = "sysuser_id"))
    private List<SysRole> userRoleList; // 角色项

    public SysUser() {
        this.setLoginCount(0);
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public SysUser(String loginName, String password, String name, String email, String mobile) {
        if (!StringUtils.isNullOrWhiteSpace(loginName) && !StringUtils.isNullOrWhiteSpace(password)) {
            this.loginName = loginName;
            this.password = password;
            this.email = email;
            this.name = name;
            this.mobile = mobile;
        }
        this.setLoginCount(0);
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());

    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password))
            return true;
        return false;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        if (!StringUtils.isNullOrWhiteSpace(loginName)) {
            this.loginName = loginName;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!StringUtils.isNullOrWhiteSpace(password)) {
            this.password = password;
        }
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public List<SysRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<SysRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
}
