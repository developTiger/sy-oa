package com.sunesoft.lemon.deanery.project.application.dtos;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ScientificResearchProjectDto extends BaseFormDto {
//    private String processId;
//    private String orderId;
//    private String taskId;
    //年度
    private Date year;
    private  Date beginDate;

    private  Date endDate;
    private  Date beginTime;

    private  Date endTime;

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    private  Date niandu;
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
    private String baoTitle;
    //申报时间

    private String baoBeginDate;


    private String baoEndDate;
    //申报类容
    private String baoContent;
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
     * 公关目标
     */

    private String relationAim;

    /**
     * 项目预期效果
     */

    private String desiredEffect;

    /**
     * 经济指标
     */
    private String economicIndicator;



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
     * 项目状态
     */
    private String projectStatus;

    /**
     * 所属部门
     */
    private Long dept;


   //专家意见
    private String proficientOpinion;
   //批示
    private String instructions;
    /*
      文件
         */
    private List<ScientificResearchProjectFileDto> fileList;

    /*
    文件2
     */
    private List<ScientificApproveFileDto> fileApproveList;

    /**
     * 附件退回id
     */
    private List<String> already_upFileId;

    /**
     * 附件退回name
     */
    private List<String> already_upFileName;

    public List<String> getAlready_upFileId() {
        return already_upFileId;
    }

    public void setAlready_upFileId(List<String> already_upFileId) {
        this.already_upFileId = already_upFileId;
    }

    public List<String> getAlready_upFileName() {
        return already_upFileName;
    }

    public void setAlready_upFileName(List<String> already_upFileName) {
        this.already_upFileName = already_upFileName;
    }

    public List<ScientificApproveFileDto> getFileApproveList() {
        return fileApproveList;
    }

    public void setFileApproveList(List<ScientificApproveFileDto> fileApproveList) {
        this.fileApproveList = fileApproveList;
    }

    public String getRelationAim() {
        return relationAim;
    }

    public void setRelationAim(String relationAim) {
        this.relationAim = relationAim;
    }

    public String getDesiredEffect() {
        return desiredEffect;
    }

    public void setDesiredEffect(String desiredEffect) {
        this.desiredEffect = desiredEffect;
    }

    public String getEconomicIndicator() {
        return economicIndicator;
    }

    public void setEconomicIndicator(String economicIndicator) {
        this.economicIndicator = economicIndicator;
    }

    private List<ScientificResearchProjectDto> pdetail;

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

    public String getBaoTitle() {
        return baoTitle;
    }

    public void setBaoTitle(String baoTitle) {
        this.baoTitle = baoTitle;
    }

    public String getBaoBeginDate() {
        return baoBeginDate;
    }

    public void setBaoBeginDate(String baoBeginDate) {
        this.baoBeginDate = baoBeginDate;
    }

    public String getBaoEndDate() {
        return baoEndDate;
    }

    public void setBaoEndDate(String baoEndDate) {
        this.baoEndDate = baoEndDate;
    }

    public String getBaoContent() {
        return baoContent;
    }

    public void setBaoContent(String baoContent) {
        this.baoContent = baoContent;
    }

    public List<ScientificResearchProjectFileDto> getFileList() {
        return fileList;
    }

    public void setFileList(List<ScientificResearchProjectFileDto> fileList) {
        this.fileList = fileList;
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

        this.forSpecialtyType = forSpecialtyType;
    }

    public String getForProjectType() {
        return forProjectType;
    }

    /*
        1：项目，2：课题，3：专题
    */

    public String getProficientOpinion() {
        return proficientOpinion;
    }

    public void setProficientOpinion(String proficientOpinion) {
        this.proficientOpinion = proficientOpinion;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

//    public String getProcessId() {
//        return processId;
//    }
//
//    public void setProcessId(String processId) {
//        this.processId = processId;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getTaskId() {
//        return taskId;
//    }
//
//    public void setTaskId(String taskId) {
//        this.taskId = taskId;
//    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<ScientificResearchProjectDto> getPdetail() {
        return pdetail;
    }

    public void setPdetail(List<ScientificResearchProjectDto> pdetail) {
        this.pdetail = pdetail;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
