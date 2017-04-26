package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;


import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 员工信息
 * Created by zhouz on 2016/5/12.
 */
@Entity
@Table(name = "syy_oa_hr_employees")
public class Employee extends BaseEntity {
    /**
     * 员工编号
     */
    @Column(name = "user_no")
    private String userNo;
    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String name;

    /**
     * 登录用户帐号
     */
    @Column(name = "user_name", nullable = false)
    private String loginName;


    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 登录密码
     */
    @Column(name = "emp_password")
    private String password;

    /**
     * 加密规则
     */
    private String alt;


    /**
     * 系统用户状态 0代表禁用,1代表未禁用
     */
    @Column(name = "emp_status")
    private Integer status;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_group_id")
    private EmpGroup empGroup;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_dept_id")
    private Deptment dept;

    /**
     * 直属上级
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "leader_id")
    private Employee leader;


//    @Column(name = "id_of_leader")
//    private Long leaderId;
//
//    /**
//     * 直属上级名称
//     */
//    @Column(name = "leader_name")
//    private String leaderName;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String IdCard;

    /**
     * 工龄
     */
    private Integer seniority;
    /**
     * 参加工作时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "join_date")
    private Date joinDate;

    /**
     * 入院时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_date")
    private Date enteryDate;

    /**
     * 职位
     */
    private String position;

    /**
     * 职位描述
     */
    @Column(name = "p_description")
    private String pDescription;

    /**
     * 个人身份
     */
    @Column(name = "person_info")
    private String personalInfo;

    /**
     * 籍贯
     */
    private String origo;

    /**
     * 当前职务
     */
    @Column(name = "position_title")
    private String positionTitle;


    /**
     * 行政级别
     */
    @Column(name = "executive_level")
    private String executiveLevel;


    /**
     * 工种
     */
    @Column(name = "work_kind")
    private String workKind;

    /**
     * 技能等级
     */
    @Column(name = "text_level")
    private String techLevel;


    /**
     * 政治面貌
     */
    @Column(name = "political_face")
    private String politicalFace;


    /**
     * 已经改为 外语类型
     */
    @Column(name = "foreign_language_level")
    private String foreignLanguageLevel;

    /**
     * 已经改为 外语成绩
     */
    @Column(name = "foreign_language_grade")
    private String foreignLanguageGrage;


    /**
     * 计算机级别
     */
    @Column(name = "compute_level")
    private String computeLevel;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
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
     *
     */
    @Column(name = "agent_to")
    private String agentTo;
    /**
     * 账号最后登录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 账号登录次数
     */
    @Column(name = "login_count", columnDefinition = "int default 0")
    private int loginCount;

    /**
     * 最高学历信息
     */
    @Column(name = "highest_edu")
    private String highestEdu;

    /**
     * 当前资质信息
     */
    @Column(name = "current_qualifications")
    private String currentQualifications;

    /**
     * 当前职称
     */
    @Column(name = "current_tech_position")
    private String currenttechPosition;
    /**
     * 教育背景信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_id")
    private List<Education> educations;

    /**
     * 家庭成员信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "emp_id")
    private List<Family> families;

    /**
     * 资质信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_id")
    private List<Qualification> qualifications;

    /**
     * 资质信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_id")
    private List<TechPosition> techPositions;

    /**
     * 工作经历
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "emp_id")
    private List<WorkExperience> workExperiences;

    /**
     * 可请的疗养假
     */
    private Float spaWithMoney;

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
    private Float yearWithMoney;
    /**
     * 剩余带薪年假
     */
    private Float hasYear;
    /**
     * 最新请带薪年假的年
     */
    private Integer newRestYear;

    public Employee() {
        this.setLoginCount(0);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        setIsActive(true);
        setStatus(1);
        this.password = "123456";
        this.educations = Collections.emptyList();
        this.families = Collections.emptyList();
        this.qualifications = Collections.emptyList();
        this.techPositions = Collections.emptyList();
        this.workExperiences = Collections.emptyList();
    }

    public Employee(String userNo, String loginName, String password, String name, String email, String mobile) {
        if (!StringUtils.isNullOrWhiteSpace(userNo) && !StringUtils.isNullOrWhiteSpace(loginName) && !StringUtils.isNullOrWhiteSpace(password)) {
            this.userNo = userNo;
            this.loginName = loginName;
            this.password = password;
            this.email = email;
            this.name = name;
            this.mobile = mobile;
        }
        setStatus(1);
        this.setLoginCount(0);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        setIsActive(true);
        this.educations = Collections.emptyList();
        this.families = Collections.emptyList();
        this.qualifications = Collections.emptyList();
        this.techPositions = Collections.emptyList();
        this.workExperiences = Collections.emptyList();
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password))
            return true;
        return false;
    }


    public String getForeignLanguageGrage() {
        return foreignLanguageGrage;
    }

    public void setForeignLanguageGrage(String foreignLanguageGrage) {
        this.foreignLanguageGrage = foreignLanguageGrage;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        if (!StringUtils.isNullOrWhiteSpace(userNo))
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
        if (!StringUtils.isNullOrWhiteSpace(loginName))
            this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public Date getEnteryDate() {
        return enteryDate;
    }

    public void setEnteryDate(Date enteryDate) {
        this.enteryDate = enteryDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getOrigo() {
        return origo;
    }

    public void setOrigo(String origo) {
        this.origo = origo;
    }

    public String getExecutiveLevel() {
        return executiveLevel;
    }

    public void setExecutiveLevel(String executiveLevel) {
        this.executiveLevel = executiveLevel;
    }

    public String getWorkKind() {
        return workKind;
    }

    public void setWorkKind(String workKind) {
        this.workKind = workKind;
    }

    public String getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public String getPoliticalFace() {
        return politicalFace;
    }

    public void setPoliticalFace(String politicalFace) {
        this.politicalFace = politicalFace;
    }

    public String getForeignLanguageLevel() {
        return foreignLanguageLevel;
    }

    public void setForeignLanguageLevel(String foreignLanguageLevel) {
        this.foreignLanguageLevel = foreignLanguageLevel;
    }

    public String getComputeLevel() {
        return computeLevel;
    }

    public void setComputeLevel(String computeLevel) {
        this.computeLevel = computeLevel;
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

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public List<TechPosition> getTechPositions() {
        return techPositions;
    }

    public void setTechPositions(List<TechPosition> techPositions) {
        this.techPositions = techPositions;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public String getAgentTo() {
        return agentTo;
    }

    public void setAgentTo(String agentTo) {
        this.agentTo = agentTo;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Deptment getDept() {
        return dept;
    }

    public void setDept(Deptment dept) {
        this.dept = dept;
    }

    public EmpGroup getEmpGroup() {
        return empGroup;
    }

    public void setEmpGroup(EmpGroup empGroup) {
        this.empGroup = empGroup;
    }

    public Employee getLeader() {
        return leader;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public Float getYearWithMoney() {
        return yearWithMoney;
    }

    public void setYearWithMoney(Float yearWithMoney) {
//        if (yearWithMoney != null)
            this.yearWithMoney = yearWithMoney;
//        else
//            this.yearWithMoney = getT(this.seniority);//可请年假
    }

    public Float getSpaWithMoney() {
        return spaWithMoney;
    }

    public void setSpaWithMoney(Float spaWithMoney) {

            this.spaWithMoney=spaWithMoney;
    }

    public Float getHasYear() {
        return hasYear;
    }

    public void setHasYear(Float hasYear) {
        this.hasYear = hasYear;
    }

    public Boolean getCanSpa() {
        return canSpa;
    }

    public void setCanSpa(Boolean canSpa) {
        if (canSpa != null) {
            this.canSpa = canSpa;
        } else {
            if (this.newRestYear.equals(DateHelper.getYear(new Date())) || this.getSpaWithMoney() == 0f)
                this.canSpa = false;
        }

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
