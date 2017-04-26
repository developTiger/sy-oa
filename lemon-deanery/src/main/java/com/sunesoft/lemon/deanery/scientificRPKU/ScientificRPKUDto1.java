package com.sunesoft.lemon.deanery.scientificRPKU;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
public class ScientificRPKUDto1 {


    /**
     * 年度
     */
   // private String year_Str;

    /**
     * 项目信息ID
     */
   /* private Long id;*/
    /**
     * 项目编号
     */
   // private String projectNo;
   //序号
    private int index;
    /**
     * 项目名称
     */
    private String projectName;
   //国家和集团编号
    private String countryNo;

    /**
     * 项目类型
     * 保存时用：1,2,3代表不同类型
     * 1：项目，2：课题，3：专题
     *
     */
    private String projectType;

   //油田公司配套
    private String youtianCountryPT;


    //专业主管部门
    private String zyzgbm;


    /**
     * 专业类别
     * 单个专业类别保存时，选用编号01,02,03,04,05,06,07
     */
    private String specialtyType;

    //新开或转接
    private String xkOrzj;

    //研究目的
    private String yjmd;

    /**
     * 研究内容，技术经济指标及项目进度安排
     */
    private String projectPlanInfo;

    //预算成果
    private String yscg;

    //进度安排
    private String jdap;

    //经济预算
    private String jjys;
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
    private String beginTime;


    /**
     * 截止时间
     */
    private String endTime;


    /**
     * 项目长名称
     */
    private String leaderName;


    /**
     * 备注
     */
    private String remark;




    /**
     * 组员名称
     */
   // private String groupMembersName;

    /**
     *
     * 插入数据库为数字
     * 0：代表立项未开始
     * 1：代表立项中
     * 2立项完成
     */
   /* private String projectStatus;

    *//**
     * 0未验收
     * 1已验收
     *//*

    private String projectYSStatus;
    */

    public String getJoinComopany() {
        return joinComopany;
    }

    public void setJoinComopany(String joinComopany) {
        this.joinComopany = joinComopany;
    }

    /**
     * 0未开题
     * 1已开题
     *//*

    private String projectKTStatus;
   //检测
    private String projectJCStatus;
   //运行
    private String projectYXStatus;*/


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getYoutianCountryPT() {
        return youtianCountryPT;
    }

    public void setYoutianCountryPT(String youtianCountryPT) {
        this.youtianCountryPT = youtianCountryPT;
    }

    public String getZyzgbm() {
        return zyzgbm;
    }

    public void setZyzgbm(String zyzgbm) {
        this.zyzgbm = zyzgbm;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getXkOrzj() {
        return xkOrzj;
    }

    public void setXkOrzj(String xkOrzj) {
        this.xkOrzj = xkOrzj;
    }

    public String getYjmd() {
        return yjmd;
    }

    public void setYjmd(String yjmd) {
        this.yjmd = yjmd;
    }

    public String getProjectPlanInfo() {
        return projectPlanInfo;
    }

    public void setProjectPlanInfo(String projectPlanInfo) {
        this.projectPlanInfo = projectPlanInfo;
    }

    public String getYscg() {
        return yscg;
    }

    public void setYscg(String yscg) {
        this.yscg = yscg;
    }

    public String getJdap() {
        return jdap;
    }

    public void setJdap(String jdap) {
        this.jdap = jdap;
    }

    public String getJjys() {
        return jjys;
    }

    public void setJjys(String jjys) {
        this.jjys = jjys;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
