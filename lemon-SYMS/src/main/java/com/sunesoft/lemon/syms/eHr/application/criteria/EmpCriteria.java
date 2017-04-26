package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by zhouz on 2016/5/19.
 */
public class EmpCriteria extends PagedCriteria {

    /**
     * 真实姓名
     */
    private String name;

    /**
     * login name
     */
    private String loginName;

    /**
     * id of dept
     */

    private Long deptId;

    /**
     * 最高学历信息
     */
    private String highestEdu;

    /**
     * 当前资质信息
     */
    private String currentQualifications;

    /**
     * 已经改为 专业技术职务
     */
    private String currenttechPosition;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getCurrentQualifications() {
        return currentQualifications;
    }

    public void setCurrentQualifications(String currentQualifications) {
        this.currentQualifications = currentQualifications;
    }

    public String getCurrenttechPosition() {
        return currenttechPosition;
    }

    public void setCurrenttechPosition(String currenttechPosition) {
        this.currenttechPosition = currenttechPosition;
    }

    public String getHighestEdu() {
        return highestEdu;
    }

    public void setHighestEdu(String highestEdu) {
        this.highestEdu = highestEdu;
    }


}
