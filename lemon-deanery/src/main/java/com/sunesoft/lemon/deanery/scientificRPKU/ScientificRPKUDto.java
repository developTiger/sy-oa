package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificApproveFileDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectFileDto;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zy on 2016/6/27 0027.
 */
public class ScientificRPKUDto {

    private Long id;
    //立项流程id
    private Long projectID;
    private Date year;

    public String getYear_Str() {
        return year_Str;
    }

    public void setYear_Str(String year_Str) {
        this.year_Str = year_Str;
    }

    /**
     * 年度
     */
    private String year_Str;

    /**
     * 项目信息ID
     */
   /* private Long id;*/
    /**
     * 项目编号
     */
    private String projectNo;


    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 专业类别
     * 单个专业类别保存时，选用编号01,02,03,04,05,06,07
     */
    private String specialtyType;

    /**
     * 专业类别
     * 01油气勘探
     * 02油气开发
     * 03炼油化工
     * 04油气集输
     * 05计 算 机
     * 06 软科 学
     * 07安全环保
     * 页面展示
     */
    private Map<String,String> specialtyTypes;

    /**
     * 页面展示字段
     */
    private String forSpecialtyType;

    /**
     * 研究内容，技术经济指标及项目进度安排
     */
    private String projectPlanInfo;



    /**
     * 承担单位
     */
    private String assumeCompany;

    /**
     * 参加单位
     */
    private String joinComopany;

    /**
     * 开始时间
     */
    private String beginTime1;


    /**
     * 截止时间
     */
    private String endTime1;


    /**
     * 备注
     */
    private String remark;


    /**
     * 项目类型
     * 保存时用：1,2,3代表不同类型
     * 1：项目，2：课题，3：专题
     *
     */
    private String projectType;

    /**
     * 项目类型
     * 页面展示下拉列表选择
     */
    private Map<String,String> projectTypes;

    /**
     * 项目类型
     * 页面数据批量展示
     */
    private String forProjectType;


    /**
     * 项目长
     */
    private Long leader;

    /**
     * 项目长名称
     */
    private String leaderName;


    /**
     * 副项目长
     */
    private Long deputy;

    /**
     * 副项目长名称
     */
    private String deputyName;

    /**
     * 组员（多个人员，以英文字符的逗号进行分割，对应人员表中的ID）
     */
    //private String[] groupMembers;

    private String groupMembers;

    /**
     * 组员名称
     */
    private String groupMembersName;


    /**
     * 所属部门
     */
    private Long dept;


    /**
     *
     * 插入数据库为数字
     * 0：代表立项未开始
     * 1：代表立项中
     * 2立项完成
     */

    private String projectStatus;

    /**
     * 0未验收
     * 1已验收
     */

    private String projectYSStatus;
    /**
     * 0未开题
     * 1已开题
     */

    private String projectKTStatus;
    /**
     * 0未申报
     * 1已申报
     */
    private String deptName;
    private String projectSBStatus;

    private String projectJCStatus;
    private String projectYXStatus;


    private String proficientOpinion;
    private String instructions;

    public String getProficientOpinion() {
        return proficientOpinion;
    }

    public void setProficientOpinion(String proficientOpinion) {
        this.proficientOpinion = proficientOpinion;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectYXStatus() {
        return projectYXStatus;
    }

    public void setProjectYXStatus(String projectYXStatus) {
        this.projectYXStatus = projectYXStatus;
    }

    public String getProjectJCStatus() {
        return projectJCStatus;
    }

    public void setProjectJCStatus(String projectJCStatus) {
        this.projectJCStatus = projectJCStatus;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getProjectPlanInfo() {
        return projectPlanInfo;
    }

    public void setProjectPlanInfo(String projectPlanInfo) {
        this.projectPlanInfo = projectPlanInfo;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public String getJoinComopany() {
        return joinComopany;
    }

    public void setJoinComopany(String joinComopany) {
        this.joinComopany = joinComopany;
    }

    public String getBeginTime1() {
        return beginTime1;
    }

    public void setBeginTime1(String beginTime1) {
        this.beginTime1 = beginTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setSpecialtyTypes(Map<String, String> specialtyTypes) {
        this.specialtyTypes = specialtyTypes;
    }

    public void setProjectTypes(Map<String, String> projectTypes) {
        this.projectTypes = projectTypes;
    }

    public void setForProjectType(String forProjectType) {
        this.forProjectType = forProjectType;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public Map<String, String> getSpecialtyTypes() {
        return static_common.SPECIALTYTYPE;
    }

    public void setSpecialtyTypes() {
        this.specialtyTypes = static_common.SPECIALTYTYPE;
    }

    public Map<String, String> getProjectTypes() {
        return static_common.PROJECTTYPE;
    }

    public void setProjectTypes() {
        this.projectTypes = static_common.PROJECTTYPE;
    }

    public String getForSpecialtyType() {
        return forSpecialtyType;
    }



    /**
     * 专业类别
     * 01油气勘探
     * 02油气开发
     * 03炼油化工
     * 04油气集输
     * 05计 算 机
     * 06 软科 学
     * 07安全环保
     * 页面展示
     */
    public void setForSpecialtyType(String forSpecialtyType) {
        String type = null;
        if (forSpecialtyType.equals("01")) {
            type = "油气勘探";

        } else if (forSpecialtyType.equals("02")) {
            type = "油气开发";

        } else if (forSpecialtyType.equals("03")) {
            type = "炼油化工";

        } else if (forSpecialtyType.equals("04")) {
            type = "油气集输";

        } else if (forSpecialtyType.equals("05")) {
            type = "计 算 机";

        } else if (forSpecialtyType.equals("06")) {
            type = "软 科 学";

        } else if (forSpecialtyType.equals("07")) {
            type = "安全环保";

        } else {
            type = "油气勘探";
        }
        this.forSpecialtyType = type;
    }

    public String getForProjectType() {
        return forProjectType;
    }

    /*
        1：项目，2：课题，3：专题
    */
    public void setForProjectType(int forProjectType) {
        String type = null;
        switch (forProjectType){
            case 1:
                type = "项目";
                break;
            case 2:
                type = "课题";
                break;
            case 3:
                type = "专题";
                break;
            default:
                type = "项目";
        }
        this.forProjectType = type;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Long getDeputy() {
        return deputy;
    }

    public void setDeputy(Long deputy) {
        this.deputy = deputy;
    }


    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDeputyName() {
        return deputyName;
    }

    public void setDeputyName(String deputyName) {
        this.deputyName = deputyName;
    }

    public String getGroupMembersName() {
        return groupMembersName;
    }

    public void setGroupMembersName(String groupMembersName) {
        this.groupMembersName = groupMembersName;
    }


    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getProjectYSStatus() {
        return projectYSStatus;
    }

    public void setProjectYSStatus(String projectYSStatus) {
        this.projectYSStatus = projectYSStatus;
    }

    public String getProjectKTStatus() {
        return projectKTStatus;
    }

    public void setProjectKTStatus(String projectKTStatus) {
        this.projectKTStatus = projectKTStatus;
    }

    public String getProjectSBStatus() {
        return projectSBStatus;
    }

    public void setProjectSBStatus(String projectSBStatus) {
        this.projectSBStatus = projectSBStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
