package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmpGroup;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.uAuth.domain.SysPermissionGroup;
import org.hibernate.Criteria;

import javax.persistence.Column;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/15.
 */
public class EmpDto {

    private Long id;



     /**
     * 员工编号
     */

    private String userNo;

    /**
     * 真实姓名
     */

    private String name;

    /**
     * 登录用户帐号
     */

    private String loginName;


    /**
     * 登录密码
     */
    private String password;
    private String roleName;
    /**
     * 加密规则
     */
    private String alt;


    /**
     * 系统用户状态 0代表禁用,1代表未禁用
     */
    private Integer status;

    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 直属上级Id
     */
    private Long leaderId;

    /**
     * 直属上级名称
     */
    private String leaderName;


    private Long groupId;

    /**
     * 直属员工组名称
     */

    private String groupName;


    /**
     * 性别（0：女  1：男）
     */
    private Integer sex;
    /**
     * 族别
     */
    private String nation;

    /**
     * 出生日期
     */
    private String strBirthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证
     */
    private String IdCard;

    /**
     * 工龄
     */
    private Integer seniority;

    /**
     * 入院时间
     */
    private String strEnteryDate;

    /**
     * 职位
     */
    private String position;


    /**
     * 职位描述
     */
    private String pDescription;



    /**
     * 个人身份
     */
    private String personalInfo;

    /**
     * 籍贯
     */
    private String origo;

    /**
     * 已经改为 参加工作时间
     */
    private String joinTime;

    /**
     * 行政级别
     */
    private String executiveLevel;


    /**
     * 工种
     */
    private String workKind;

    /**
     * 技能等级
     */
    private String techLevel;


    /**
     * 政治面貌
     */
    private String politicalFace;


    /**
     * 已经改为 外语类型
     */
    private String foreignLanguageLevel;

    /**
     * 已经改为 外语成绩
     */
    private String foreignLanguageGrage;


    /**
     * 计算机级别
     */
    private String computeLevel;

    /**
     * 手机
     */
    private String mobile;

    /**
     *  邮箱
     */
    private String email;

    /**
     * 座机
     */
    private String phone;

    /**
     * 照片
     */
    private String photo;

    /**
     * 简介
     */
    private String brief;

    /**
     * 创建时间
     */
    private Date createDateTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;

    /**
     * 账号最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 账号登录次数
     */
    private int loginCount;


    /**
     * 当前职务
     */
    private String positionTitle;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 最高学历
     */
    private String highestEdu;

    /**
     * 资质信息
     */
    private String currentQualifications;
    /**
     * 当前职位
     */
    private String currenttechPosition;

    /**
     * 可请的疗养假
     */
    private Float spaWithMoney ;

    /**
     * 是否可请疗养假，true可请
     */
    private Boolean canSpa;
    /**
     * 最新请疗养假——年
     */
    private Integer newSpaYear;

    /**
     * 可请的带薪年假
     */
    private Float yearWithMoney ;
    /**
     * 剩余带薪年假
     */
    private Float hasYear;
    /**
     * 最新请带薪年假的年
     */
    private Integer newRestYear;



    public String getForeignLanguageGrage() {
        return foreignLanguageGrage;
    }

    public void setForeignLanguageGrage(String foreignLanguageGrage) {
        this.foreignLanguageGrage = foreignLanguageGrage;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getComputeLevel() {
        return computeLevel;
    }

    public void setComputeLevel(String computeLevel) {
        this.computeLevel = computeLevel;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getExecutiveLevel() {
        return executiveLevel;
    }

    public void setExecutiveLevel(String executiveLevel) {
        this.executiveLevel = executiveLevel;
    }

    public String getForeignLanguageLevel() {
        return foreignLanguageLevel;
    }

    public void setForeignLanguageLevel(String foreignLanguageLevel) {
        this.foreignLanguageLevel = foreignLanguageLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOrigo() {
        return origo;
    }

    public void setOrigo(String origo) {
        this.origo = origo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
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

    public String getPoliticalFace() {
        return politicalFace;
    }

    public void setPoliticalFace(String politicalFace) {
        this.politicalFace = politicalFace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getWorkKind() {
        return workKind;
    }

    public void setWorkKind(String workKind) {
        this.workKind = workKind;
    }

    public String getStrBirthday() {
        return strBirthday;
    }

    public void setStrBirthday(String strBirthday) {
        this.strBirthday = strBirthday;
    }

    public String getStrEnteryDate() {
        return strEnteryDate;
    }

    public void setStrEnteryDate(String strEnteryDate) {
        this.strEnteryDate = strEnteryDate;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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


    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Float getSpaWithMoney() {
        return spaWithMoney;
    }

    public void setSpaWithMoney(Float spaWithMoney) {
        this.spaWithMoney = spaWithMoney;
    }

    public Float getYearWithMoney() {
        return yearWithMoney;
    }

    public void setYearWithMoney(Float yearWithMoney) {
        this.yearWithMoney = yearWithMoney;
    }

    public Boolean getCanSpa() {
        return canSpa;
    }

    public void setCanSpa(Boolean canSpa) {
        this.canSpa = canSpa;
    }

    public Float getHasYear() {
        return hasYear;
    }

    public void setHasYear(Float hasYear) {
        this.hasYear = hasYear;
    }

    public Integer getNewRestYear() {
        return newRestYear;
    }

    public void setNewRestYear(Integer newRestYear) {
        this.newRestYear = newRestYear;
    }

    public Integer getNewSpaYear() {
        return newSpaYear;
    }

    public void setNewSpaYear(Integer newSpaYear) {
        this.newSpaYear = newSpaYear;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
