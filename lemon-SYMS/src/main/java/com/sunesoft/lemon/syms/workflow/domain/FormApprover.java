package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.domain.enums.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhouz on 2016/5/21.
 */
@Entity
@Table(name = "syy_oa_fm_approver")
public class FormApprover extends BaseEntity {


    @Column(name = "form_no")
    private Long formNo;//表单编号

    @Column(name = "form_kind", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKind;//表单类型

    @Column(name = "app_serial")
    private Integer appSerial;//顺序

    @Column(name = "app_assigner")
    private Long appAssigner;//加签人或者是系统


    @Column(name = "app_user_id")
    private Long appUserId;//签核人

    @Column(name = "app_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String appName; //签核人姓名

    @Column(name = "role_type")
    private RoleType roleType;//角色类型


    @Column(name = "roleName", columnDefinition = "varchar(100) DEFAULT NULL")
    private String roleName;//角色名称


    @Column(name = "cl_step")
    private Integer clStep; //当前步骤

    @Column(name = "is_complete")
    private Boolean isComplete;

    @Column(name = "role_id")
    private Long roleId;
    //private FormApproveRole appRole;

    @Column(name = "next_approver_id")
    private Long nextApproverId;//下一个签核人


    @Column(name = "app_actor")
    private Long appActor;//实际完成人


    @Column(name = "app_actor_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String appActorName; //签核人姓名


    @Column(name = "approve_status")
    private ApproveStatus approveStatus;//工作状态

    @Column(name = "app_type")
    private AppType appType;//工作类型


    @Column(name = "assign_type")
    private AssignType assignType;//分配类型


    @Column(name = "begin_date")
    private Date beginDate;


    @Column(name = "end_date")
    private Date endDate;


    @Column(name = "app_value")
    private AppValue appValue;//签核值

    @Column(name = "app_content", columnDefinition = "varchar(500) DEFAULT NULL")
    private String appContent;//签核意见

    @Column(name = "assign_reason", columnDefinition = "varchar(500) DEFAULT NULL")
    private String assignReason;//加签 转签意见

    private Long appPointId;

    private Long appNextPointId;


    public FormApprover() {
    }

    public FormApprover(Long formNo, String formKind) {
        this.formKind = formKind;
        this.formNo = formNo;
        appAssigner = 0L;
        this.approveStatus = ApproveStatus.W;
//        this.appType=AppType.A;
////        this.roleType=RoleType.OR;
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.clStep = 0;
    }


    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 审核通过
     *
     * @param approveId
     * @param userName
     * @param content
     * @return
     */


    public CommonResult approve(Long approveId, String userName, String content) {
        if (this.approveStatus == ApproveStatus.U) {
//            if(!approveId.equals(this.appUserId)){
//                userName+="(代)";
//            }
            this.setApproveStatus(ApproveStatus.A);
            this.appContent = content;
            this.appActor = approveId;
            this.appActorName = userName;
            this.appValue = AppValue.Y;
            this.endDate = new Date();
            return new CommonResult(true);
        }
        return new CommonResult(false, "当前表单不允许您做签核！");
    }


    /**
     * 略过
     *
     * @param approveId
     * @param userName
     * @param content
     * @return
     */
    public CommonResult autoPass(Long approveId, String userName, String content) {
        if (this.approveStatus == ApproveStatus.U) {
//            if(!approveId.equals(this.appUserId)){
//                userName+="(代)";
//            }
            this.setApproveStatus(ApproveStatus.P);
            this.appActor = approveId;
            this.appActorName = userName;
            this.appContent = content;
            this.endDate = new Date();
            this.appValue = AppValue.Y;
            return ResultFactory.commonSuccess();
        }
        return new CommonResult(false, "操作失败！");
    }

    /**
     * 否决
     *
     * @param approveId
     * @param userName
     * @param content
     * @return
     */
    public CommonResult reject(Long approveId, String userName, String content) {
        if (this.approveStatus == ApproveStatus.U) {
//            if(!approveId.equals(this.appUserId)){
//                userName+="(代)";
//            }
            this.setApproveStatus(ApproveStatus.R);
            this.appContent = content;
            this.appActor = approveId;
            this.appActorName = userName;
            this.appValue = AppValue.N;
            this.endDate = new Date();
            return new CommonResult(true);
        }
        return new CommonResult(false, "当前表单不允许您做签核！");
    }

    /**
     * 否决略过
     *
     * @param approveId
     * @param userName
     * @param content
     * @return
     */
    public CommonResult rejectPass(Long approveId, String userName, String content) {
        if (this.approveStatus == ApproveStatus.U) {
//            if(!approveId.equals(this.appUserId)){
//                userName+="(代)";
//            }
            this.setApproveStatus(ApproveStatus.P);
            this.appContent = content;
            this.appActor = approveId;
            this.appActorName = userName;
            this.appValue = AppValue.N;
            this.endDate = new Date();
            return new CommonResult(true);
        }
        return new CommonResult(false, "当前表单不允许您做签核！");
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 激活签核人
     *
     * @return
     */
    public CommonResult activeApprover() {
        if (approveStatus == ApproveStatus.W) {
            this.beginDate = new Date();
            this.approveStatus = ApproveStatus.U;
            return ResultFactory.commonSuccess();
        }
        return ResultFactory.commonError("用户无法激活下一级");
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public Integer getAppSerial() {
        return appSerial;
    }

    public void setAppSerial(Integer appSerial) {
        this.appSerial = appSerial;
    }

    public Integer getClStep() {
        return clStep;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Long getAppAssigner() {
        return appAssigner;
    }

    public void setAppAssigner(Long appAssigner) {
        this.appAssigner = appAssigner;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getAppActor() {
        return appActor;
    }

    public void setAppActor(Long appActor) {
        this.appActor = appActor;
    }

    public String getAppActorName() {
        return appActorName;
    }

    public void setAppActorName(String appActorName) {
        this.appActorName = appActorName;
    }

    public ApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public AssignType getAssignType() {
        return assignType;
    }

    public void setAssignType(AssignType assignType) {
        this.assignType = assignType;
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

    public AppValue getAppValue() {
        return appValue;
    }

    public void setAppValue(AppValue appValue) {
        this.appValue = appValue;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }

    public String getAssignReason() {
        return assignReason;
    }

    public void setAssignReason(String assignReason) {
        this.assignReason = assignReason;
    }

    public Long getNextApproverId() {
        return nextApproverId;
    }

    public void setNextApproverId(Long nextApproverId) {
        this.nextApproverId = nextApproverId;
    }

    public Long getAppPointId() {
        return appPointId;
    }

    public void setAppPointId(Long appPointId) {
        this.appPointId = appPointId;
    }

    public Long getAppNextPointId() {
        return appNextPointId;
    }

    public void setAppNextPointId(Long appNextPointId) {
        this.appNextPointId = appNextPointId;
    }
}
