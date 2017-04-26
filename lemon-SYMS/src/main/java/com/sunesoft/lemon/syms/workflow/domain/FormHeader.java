package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.ArrayUtils;
import com.sunesoft.lemon.syms.workflow.domain.enums.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * Created by zhouz on 2016/5/21.
 */

@Entity
@Table(name = "syy_oa_fm_header")
public class FormHeader extends BaseEntity {
//    /**
//     * 归口部门id
//     */
//    @Column(name = "with_dept_id")
//    private Long withDeptId;

    /**
     * 申请人
     */
    @Column(name = "applyer")
    private Long applyer; //申请人

    /**
     * 申请人姓名
     */
    @Column(name = "applyer_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String applyerName; //申请人姓名

    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "dept_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String deptName;

    /**
     * 表单种类
     */
    @Column(name = "form_kind", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKind;//表单种类

    /**
     * 表单名称
     */
    @Column(name = "form_kind_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String formKindName;//表单名称


    @Column(name = "parent_form_no")
    private Long parentFormNo;
    /**
     * 表单状态
     */
    @Column(name = "form_status")
    private FormStatus formStatus;//表单状态
    /**
     * 归属部门
     */
    @Column(name = "blong_dept")
    private Long blongDept;//归属部门
    /**
     * 归属部门名称
     */
    @Column(name = "blong_dept_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String blongDeptName;//归属部门名称

    /**
     * 开始时间
     */
    @Column(name = "begin_date")
    private Date beginDate;//开始持剑(时间)

    /**
     * 处理时间
     */
    @Column(name = "due_date")
    private Date dueDate;//处理时间

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private Date endDate;//结束时间

    /**
     * R开始(运行)run    F 结束 finish    P 暂停(保留)
     */
    @Column(name = "life_cycle", columnDefinition = "varchar(20) DEFAULT NULL")
    private String lifeCycle;//R开始(运行)run    F 结束 finish    P 暂停(保留)

    /**
     * 是否已通知到签核人
     */
    @Column(name = "notice_flag")
    private Boolean noticeFlag;//是否已通知到签核人

    /**
     * 前一位签核人
     */
    @Column(name = "provious_approver", columnDefinition = "varchar(100) DEFAULT NULL")
    private String proviousApprover;//前一位签核人

    /**
     * 内容
     */
    @Column(name = "content", columnDefinition = "varchar(500) DEFAULT NULL")
    private String content;//内荣

    /**
     * 备注
     */
    @Column(name = "remark", columnDefinition = "varchar(500) DEFAULT NULL")
    private String remark;//备注

    /**
     * 概要/标题 。 作为重复判断的依据
     */
    @Column(name = "summery", columnDefinition = "varchar(500) DEFAULT NULL")
    private String summery;//概要

    /**
     * 当前步骤
     */
    @Column(name = "cl_step")
    private Integer clStep; //当前步骤

    /**
     * 是否已完成
     */
    @Column(name = "is_step_complete")
    private Boolean isStepComplete;

    @Column(name = "view_url", columnDefinition = "varchar(100) DEFAULT NULL")
    private String viewUrl;

    @Column(name = "current_app_type")
    private AppType currentAppType;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "fm_header_id")
    private List<FormApprover> formApprovers;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "fm_header_id")
    private List<InnerFormApprovePoint> innerFormApprovePoints;


    @Column(name = "has_apply_view")
    public Boolean hasApplyView;


    @Column(name = "form_type")
    private Integer formType;//表单类型   1 静态签核人表单 0 动态签核人表单


    /**
     * 最大顺序标号
     */
    @Column(name = "max_serial")
    private Integer maxSerial;
    /**
     * 当前谦和 serial
     */
    @Column(name = "current_serials")
    private Integer currentSerial;

    @Column(name = "current_point_name")
    private String currentPointName;
    /**
     * 当前标号
     */
    @Column(name = "current_serial")
    private Integer nextAppListSerial;
    /**
     * 该节点页面Action
     */
    @Column(name = "view_action")
    private String currentViewAction;


    /**
     * 该节点审核Action
     */
    @Column(name = "approve_action")
    private String currentApproveAction;


    /**
     * 当前审核节点Id
     */
    @Column(name = "current_app_point_id")
    private Long currentAppPointId;

    @Column(name = "is_current_piont_complete")
    private Boolean isCurrentPiontComplete;

    /**
     * 下一个审核节点Id
     */
    @Column(name = "next_app_point_id")
    private Long nextAppPointId;

    public FormHeader() {
        this.formStatus = FormStatus.SA;
        this.formApprovers = new ArrayList<>();
        innerFormApprovePoints = new ArrayList<>();
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        formType = 1;

        this.clStep = 0;
        isStepComplete = true;
        isCurrentPiontComplete = false;
    }

    public FormHeader(String formKind, Long applyer) {
        this.formStatus = FormStatus.SA;
        this.formApprovers = new ArrayList<>();
        innerFormApprovePoints = new ArrayList<>();
        this.formKind = formKind;
        formType = 1;
        this.applyer = applyer;
        this.clStep = 0;
        isStepComplete = true;
        this.nextAppListSerial = 0;

        this.currentSerial = 0;
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public Boolean hasNextSerial() {
        return currentSerial < maxSerial;
    }

    public Boolean hasNextRoleApprover(Integer appListCount) {

        return appListCount > nextAppListSerial;
    }

    /**
     * 提交表单
     *
     * @param formNo           表单编号
     * @param formApproverList 需要签核的人员列表
     * @return
     */
    public CommonResult submitForm(Long formNo, List<FormApprover> formApproverList) {
        if (this.formStatus == FormStatus.IM || this.formStatus == FormStatus.NC || this.formStatus == FormStatus.RC || this.formStatus == FormStatus.SA || this.formStatus == FormStatus.WD || this.formStatus == FormStatus.RJ) {
            this.beginDate = new Date();
            formApprovers.addAll(formApproverList);
            formStatus = FormStatus.WA;
            lifeCycle = "R";
        }

        return ResultFactory.commonSuccess();
    }


    /**
     * 激活表单
     *
     * @return
     */
    public CommonResult activeForm() {

        if (formStatus == FormStatus.WA) {
            formStatus = FormStatus.UA;
            for (FormApprover approver : formApprovers) {
                if (approver.getAppSerial() == 1 && approver.getApproveStatus() == ApproveStatus.W) {
                    approver.setApproveStatus(ApproveStatus.U);
//                    this.currentAppType = approver.getAppType();
                    if (approver.getClStep() > 0) {
                        this.clStep = approver.getClStep();
                        this.isStepComplete = false;

                    }
                }
            }
            return ResultFactory.commonSuccess();
        } else {
            return ResultFactory.commonError("激活失败，表单状态错误！");
        }
    }


    /**
     * 审核通过
     *
     * @param appUserId
     * @param appUserName
     * @param agentId     代理人列表
     * @param content
     */
    public CommonResult approve2(Long appUserId, String appUserName, String content, List<Long> agentId) {
        List<Long> canApproverUserId = new ArrayList<>();
         if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;

         }
        canApproverUserId.add(appUserId);
        //this.beginDate=new Date();
        formStatus = FormStatus.UA;
        Integer step = 0;
        Boolean doFlag = false;
        RoleType roleType = RoleType.OR;
        Long currentPoint = 0L;
        for (FormApprover approver : formApprovers) {
            if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getApproveStatus() == ApproveStatus.U) {
                if (approver.approve(appUserId, appUserName, content).getIsSuccess()) {
                    proviousApprover = appUserName;
//                    clStep = approver.getClStep();//添加特殊操作更改
                    doFlag = true;
                    roleType = approver.getRoleType();
                    currentPoint = approver.getAppPointId();
                    if (roleType == RoleType.OR) {
                        for (FormApprover app : formApprovers) {
                            if (roleType == RoleType.OR && app.getAppPointId().equals(currentPoint)) {
                                app.autoPass(appUserId, appUserName, content);
                            }
                        }
                        if (!isStepComplete)
                            isStepComplete = true;
                        this.isCurrentPiontComplete = true;
                        break;
                    }
                }
            }
        }

        if (roleType == RoleType.AND && doFlag) {
            Boolean alldoneFlag = true;
            for (int i = 0; i < formApprovers.size(); i++) {
                if (formApprovers.get(i).getAppPointId().equals(currentPoint) && formApprovers.get(i).getApproveStatus() == ApproveStatus.U) {
                    alldoneFlag = false;
                }
                if (i == formApprovers.size() - 1 && alldoneFlag) {
                    if (!isStepComplete)
                        isStepComplete = true;
                    this.isCurrentPiontComplete = true;
                }
            }
        }
        if (doFlag) {
            if (this.formStatus != FormStatus.AP)
                lifeCycle = "R";
            return ResultFactory.commonSuccess(Long.parseLong(step.toString()));
        }

        return ResultFactory.commonError("表单状态异常，签核失败！");


    }
    public CommonResult approveNoCkeck(Long appUserId, String appUserName, String content) {

        //this.beginDate=new Date();
        formStatus = FormStatus.UA;
        Integer step = 0;
        Boolean doFlag = false;
        RoleType roleType = RoleType.OR;
        Long currentPoint = 0L;
        for (FormApprover approver : formApprovers) {
            if(approver.getApproveStatus()==ApproveStatus.U)
                if (approver.approve(appUserId, appUserName, content).getIsSuccess()) {
                    proviousApprover = appUserName;
//                    clStep = approver.getClStep();//添加特殊操作更改
                    doFlag = true;
                    roleType = approver.getRoleType();
                    currentPoint = approver.getAppPointId();

                        for (FormApprover app : formApprovers) {
                            if (roleType == RoleType.OR && app.getAppPointId().equals(currentPoint)) {
                                app.autoPass(appUserId, appUserName, content);
                            }
                        }
                        if (!isStepComplete)
                            isStepComplete = true;
                        this.isCurrentPiontComplete = true;
                        break;

            }
        }


            return ResultFactory.commonSuccess(Long.parseLong(step.toString()));



    }

    /**
     * 审核通过
     *
     * @param appUserId
     * @param appUserName
     * @param agentId     代理人列表
     * @param content
     */
    public CommonResult approve(Long appUserId, String appUserName, String content, List<Long> agentId) {
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;

        }
        canApproverUserId.add(appUserId);
        //this.beginDate=new Date();
        formStatus = FormStatus.UA;
        Integer step = 0;
        Boolean doFlag = false;
        RoleType roleType = RoleType.OR;
        Integer serial = 0;
        for (FormApprover approver : formApprovers) {
            if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getApproveStatus() == ApproveStatus.U && approver.getAppType() == AppType.A) {
                if (approver.approve(appUserId, appUserName, content).getIsSuccess()) {
                    proviousApprover = appUserName;
//                    clStep = approver.getClStep();//添加特殊操作更改
                    doFlag = true;
                    roleType = approver.getRoleType();
                    serial = approver.getAppSerial();
                    if (roleType == RoleType.OR) {
                        for (FormApprover app : formApprovers) {
                            if (roleType == RoleType.OR && app.getAppSerial().equals(serial)) {
                                app.autoPass(appUserId, appUserName, content);
                            }
                        }
                        if (!isStepComplete)
                            isStepComplete = true;
                        if ((formType==null || formType.equals(1)) && approver.getAppSerial() < maxSerial) {
                            activeNextApprover(approver.getAppSerial() + 1);
                        }
                        if (approver.getAppSerial().equals(maxSerial))
                            formEnd(FormStatus.AP);
                        break;
                    }
                }
            }
        }

        if (roleType == RoleType.AND && doFlag) {
            Boolean alldoneFlag = true;
            for (int i = 0; i < formApprovers.size(); i++) {
                if (formApprovers.get(i).getAppSerial().equals(serial) && formApprovers.get(i).getApproveStatus() == ApproveStatus.U) {
                    alldoneFlag = false;
                }
                if (i == formApprovers.size() - 1 && alldoneFlag) {
                    if (!isStepComplete)
                        isStepComplete = true;
                    if ((formType==null || formType.equals(1)) && formApprovers.get(i).getAppSerial() < maxSerial) {
                        activeNextApprover(serial + 1);
                    }
                    if (formApprovers.get(i).getAppSerial().equals(maxSerial))
                        formEnd(FormStatus.AP);

                }
            }
        }
        if (doFlag) {
            if (this.formStatus != FormStatus.AP)
                lifeCycle = "R";
            return ResultFactory.commonSuccess(Long.parseLong(step.toString()));
        }

        return ResultFactory.commonError("表单状态异常，签核失败！");


    }
    /**
     * 审核通过
     * 选择审核人节点
     *
     * @param appUserId
     * @param appUserName
     * @param agentId     代理人列表
     * @param content
     */
    public CommonResult approve(Long appUserId, String appUserName, String content, List<Long> agentId, Long nextAppUserId, Integer clStep, Integer appSerial) {
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;

        }
        canApproverUserId.add(appUserId);
        //this.beginDate=new Date();
        formStatus = FormStatus.UA;
        Integer step = 0;
        Boolean doFlag = false;
        RoleType roleType = RoleType.OR;
        Integer serial = 0;
        for (FormApprover approver : formApprovers) {
            if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getApproveStatus() == ApproveStatus.U && approver.getAppType() == AppType.A) {
                if (approver.approve(appUserId, appUserName, content).getIsSuccess()) {
                    proviousApprover = appUserName;
                    doFlag = true;
                    roleType = approver.getRoleType();
                    serial = approver.getAppSerial();
                    if (roleType == RoleType.OR) {
                        for (FormApprover app : formApprovers) {
                            if (roleType == RoleType.OR && app.getAppSerial().equals(serial)) {
                                app.autoPass(appUserId, appUserName, content);
                            }
                        }
                        if (!isStepComplete)
                            isStepComplete = true;
                        if (activeNextApprover(clStep, nextAppUserId, appSerial).getMsg().equals("noNext")) {
                            formEnd(FormStatus.AP);
                        }
                    }
                    break;
                }
            }
        }

        if (roleType == RoleType.AND && doFlag) {
            Boolean alldoneFlag = true;
            for (int i = 0; i < formApprovers.size(); i++) {
                if (formApprovers.get(i).getAppSerial().equals(serial) && formApprovers.get(i).getApproveStatus() == ApproveStatus.U) {
                    alldoneFlag = false;
                }
                if (i == formApprovers.size() - 1 && alldoneFlag) {
                    if (!isStepComplete)
                        isStepComplete = true;
                    if (activeNextApprover(serial + 1).getMsg() == "noNext") {
                        formEnd(FormStatus.AP);
                    }
                }
            }
        }
        if (doFlag) {
            if (this.formStatus != FormStatus.AP)
                lifeCycle = "R";
            return ResultFactory.commonSuccess(Long.parseLong(step.toString()));
        }

        return ResultFactory.commonError("表单状态异常，签核失败！");


    }


    /**
     * 否决
     *
     * @param appUserId
     * @param appUserName
     * @param content
     * @param agentId     代理人列表
     * @return
     */
    public CommonResult reject(Long appUserId, String appUserName, String content, List<Long> agentId) {
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;
        }
        canApproverUserId.add(appUserId);
        formStatus = FormStatus.UA;
        Boolean doFlag = false;

        Iterator<FormApprover> it = formApprovers.iterator();
        while(it.hasNext()){
            FormApprover approver = it.next();
            if (approver.getApproveStatus() == ApproveStatus.U) {
                if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getAppType() == AppType.F) {
                    if (approver.reject(appUserId, appUserName, content).getIsSuccess()) {
                        doFlag = true;
                        proviousApprover = appUserName;
                    }
                } else {
                    it.remove();
                }
            }
            if (approver.getApproveStatus() == ApproveStatus.W) {
                it.remove();
            }
        }

        if (doFlag) {
            lifeCycle = "F";
            return ResultFactory.commonSuccess();
        }

        return ResultFactory.commonError("表单状态异常，否决失败！");

    }

    public void formEnd(FormStatus status) {
        if (this.formStatus == FormStatus.UA) {
            this.formStatus = status;
            this.lifeCycle = "F";
            this.endDate = new Date();
        }
    }

    /**
     * 签核完成，激活下一位签核人
     *
     * @param serial
     * @return
     */
    public CommonResult activeNextApprover(Integer serial) {
        Boolean isHasNext = false;
        for (FormApprover approver : formApprovers) {
            if (serial == approver.getAppSerial()) {
                isHasNext = true;
                if (approver.activeApprover().getIsSuccess()) {
                     this.currentAppType = approver.getAppType();
                    if (approver.getClStep() > this.getClStep()) {
                        this.clStep = approver.getClStep();
                        this.isStepComplete = false;

                    }
                }
            }
        }
        return isHasNext ? ResultFactory.commonSuccess() : new CommonResult(false, "noNext");
    }

    /**
     * 签核完成，激活下一位签核人（根据clstep，appUserId）
     *
     * @param clStep    特殊操作步骤
     * @param appUserId 签核人ID
     * @return
     */
    public CommonResult activeNextApprover(Integer clStep, Long appUserId, Integer appSerial) {
        Boolean isHasNext = false;
        for (FormApprover approver : formApprovers) {
            if (clStep == approver.getClStep() && appUserId == approver.getAppUserId()) {
                isHasNext = true;
                if (approver.activeApprover().getIsSuccess()) {
                    this.currentAppType = approver.getAppType();
                    if (approver.getClStep() > this.getClStep()) {
                        this.clStep = approver.getClStep();
                        this.isStepComplete = false;

                    }
                }
            }
        }
        return isHasNext ? ResultFactory.commonSuccess() : new CommonResult(false, "noNext");
    }


    /**
     * 新增签核人
     *
     * @param appUserId
     * @param appUserName
     * @param content
     * @param addUserId
     * @param isAfter
     * @param setId
     */
    public CommonResult addApprover(Long appUserId, String appUserName, String content, Long addUserId, Boolean isAfter, Long setId, int setSerial, List<Long> agentId) {
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;
        }
        canApproverUserId.add(appUserId);
        FormApprover newApprover = new FormApprover(this.getId(), this.formKind);
        newApprover.setAppType(AppType.A);
        newApprover.setAppAssigner(appUserId);
        newApprover.setAssignType(AssignType.A);
        newApprover.setAppUserId(addUserId);
        FormApprover currentApprover = null;
        Boolean canActiveNext = true;

        Boolean canAddBefore = false;
        Boolean doflag = false;
        for (FormApprover approver : formApprovers) {
            //加签人是表单创建人，则可以加签到任意位置
            if (appUserId.equals(this.applyer)) {
                doflag = true;
                if (isAfter) {
                    if (approver.getAppSerial() == setSerial) {
                        newApprover.setNextApproverId(approver.getNextApproverId());
                        approver.setNextApproverId(newApprover.getAppUserId());
                        newApprover.setAppSerial(approver.getAppSerial() + 1);

                    }
                    //重新排序（大于加签位置的+1）
                    if (approver.getAppSerial() >= setSerial + 1) {
                        approver.setAppSerial(approver.getAppSerial() + 1);
                    }
                }
                //加签在某一位签核人之后
                else {
                    if (approver.getNextApproverId() == setId) {
                        approver.setNextApproverId(addUserId);
                        newApprover.setNextApproverId(setId);

                    }
                    if (approver.getAppSerial() == setSerial) {
                        newApprover.setAppSerial(approver.getAppSerial());
                    }
                    //重新排序（大于加签位置的+1）
                    if (approver.getAppSerial() >= setSerial) {
                        approver.setAppSerial(approver.getAppSerial() + 1);
                    }
                }

            } else {
                //加签人是签核人（加签只能在本人前后加签）
                if (approver.getApproveStatus() == ApproveStatus.U) {// && (setId == appUserId)||agentId==setId) {
                    //加签在自己之后则表示自己同意
                    if (isAfter) {
                        if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId())) {
                            if (!approver.approve(appUserId, appUserName, content).getIsSuccess()) {
                                return ResultFactory.commonError("sorry！表单异常，加签失败");
                            }
                            currentApprover = approver;
                            doflag = true;
                        } else {//除当前签核人外 还有同级签核人
                            if (approver.getRoleType() == RoleType.OR) {
                                approver.autoPass(appUserId, appUserName, content);
                            } else {
                                if (approver.getRoleType() == RoleType.AND)
                                    canActiveNext = false;
                            }
                        }
                        approver.setNextApproverId(addUserId);
                        newApprover.setNextApproverId(approver.getNextApproverId());
                        newApprover.setAppSerial(approver.getAppSerial() + 1);
                    }
                    //加签在自己之前，则将自己设置为等待签核。将加签人设置为签核中状态
                    else {
                        if (!canAddBefore) {
                            for (FormApprover ap : formApprovers) {
                                if (ap.getAppSerial() == approver.getAppSerial() && approver.getApproveStatus() != ApproveStatus.U) {
                                    return ResultFactory.commonError("sorry！已有同级签核人做了处理，无法加在该级之前！");
                                }
                            }
                            canAddBefore = true;
                        }
                        if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId())) {
                            approver.setApproveStatus(ApproveStatus.W);
                            newApprover.setNextApproverId(approver.getAppUserId());
                            newApprover.setAppSerial(approver.getAppSerial());
                            doflag = true;
                        }
                    }
                    //凡是没有签核的，序号+1
                    if (approver.getApproveStatus() == ApproveStatus.W) {
                        approver.setAppSerial(approver.getAppSerial() + 1);
                    }
                }
            }
        }
        if (doflag) {
            if (canActiveNext)
                newApprover.activeApprover();
            this.formApprovers.add(newApprover);
            return ResultFactory.commonSuccess();
        }

        return ResultFactory.commonError("sorry！加签失败，请稍后重试！");
    }

    /**
     * 变更签核人
     *
     * @param appUserId
     * @param appUserName
     * @param reason
     * @param changeUserId
     * @param agentId
     * @return
     */
    public CommonResult changeApprover(Long appUserId, String appUserName, String reason, Long changeUserId, List<Long> agentId) {
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;
        }
        canApproverUserId.add(appUserId);
        FormApprover newApprover = new FormApprover(this.getId(), this.formKind);
        newApprover.setAppType(AppType.A);
        newApprover.setAppAssigner(appUserId);
        newApprover.setAppUserId(changeUserId);
        newApprover.setAssignType(AssignType.T);

        for (FormApprover approver : formApprovers) {
            if (approver.getApproveStatus() == ApproveStatus.U && ArrayUtils.isExist(canApproverUserId, approver.getAppUserId())) {
                approver.setAssignReason(reason);
                approver.setApproveStatus(ApproveStatus.T);
                approver.setAppActorName(appUserName);
                approver.setEndDate(new Date());
                approver.setAppValue(AppValue.P);
                newApprover.setAppSerial(approver.getAppSerial());
                newApprover.setClStep(approver.getClStep());
            }
        }

        newApprover.setBeginDate(new Date());
        newApprover.setApproveStatus(ApproveStatus.U);
        this.formApprovers.add(newApprover);
        return ResultFactory.commonSuccess();
    }

    public CommonResult goBack(Long appUserId, String appUserName, String reason, List<Long> agentId) {
        Boolean doflag = false;
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;

        }
        canApproverUserId.add(appUserId);
        this.remark = "打回上一级：" + reason;
        Integer currentSerial = 0;
        for (FormApprover approver : formApprovers) {
            if (approver.getApproveStatus() == ApproveStatus.U) {
                approver.setApproveStatus(ApproveStatus.W);
                if (approver.getAppSerial() == 1) {
                    return ResultFactory.commonError("打回上一级失败，请直接否决！");
                }
            }
            if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getApproveStatus() == ApproveStatus.U) {//&& approver.getAppUserId() == appUserId) {

                approver.setAppActorName(appUserName);
                approver.setAppUserId(appUserId);
                approver.setAppValue(AppValue.B);
                currentSerial = approver.getAppSerial();
                doflag = true;

            }
        }
        if (doflag) {
            for (FormApprover approver : formApprovers) {
                if (currentSerial.equals(approver.getAppSerial() - 1)) {//&& approver.getAppUserId() == appUserId) {
                    approver.setApproveStatus(ApproveStatus.U);
                    if (approver.getClStep() > 0) {
                        this.clStep = approver.getClStep();
                        isStepComplete = false;
                    }
                }
            }
            return ResultFactory.commonSuccess();
        }
        return ResultFactory.commonError("打回上一级失败，表单状态错误，请重试！");
    }

    public CommonResult goBack2(Long appUserId, String appUserName, String reason, List<Long> agentId) {
        Boolean doflag = false;
        List<Long> canApproverUserId = new ArrayList<>();
        if (agentId != null && agentId.size() > 0) {
            canApproverUserId = agentId;

        }
        Long currentPointid=0L;
        canApproverUserId.add(appUserId);
        this.remark = "打回上一级：" + reason;

        Iterator<FormApprover> it = formApprovers.iterator();
        while(it.hasNext()){
            FormApprover approver = it.next();
            if (approver.getApproveStatus() == ApproveStatus.U) {
                if (ArrayUtils.isExist(canApproverUserId, approver.getAppUserId()) && approver.getAppType() == AppType.A) {

                    approver.setApproveStatus(ApproveStatus.H);
                    approver.setAppActorName(appUserName);
                    approver.setAppUserId(appUserId);
                    approver.setAppValue(AppValue.B);
                    approver.setEndDate(new Date());
                    approver.setAppContent(reason);
                    approver.setAppContent(reason);
                    this.proviousApprover=appUserName;
                    currentPointid = approver.getAppPointId();
                    doflag = true;
                    if (approver.getAppSerial() == 1) {
                        this.formStatus = FormStatus.WD;
                        //return ResultFactory.commonError("打回上一级失败，请直接否决！");
                    }
                }
                else{
                    it.remove();
                }
            }

        }
        if (doflag) {
            if(this.formStatus == FormStatus.UA){
                for (InnerFormApprovePoint point : innerFormApprovePoints) {
                    if (point.getNextPointId() != null && point.getNextPointId() > 0 && point.getNextPointId().equals(currentPointid)) {
                        this.nextAppPointId = point.getId();
//                    this.nextAppPointId=point.getNextPointId();
                    }

                }
            }
            return ResultFactory.commonSuccess();
        }
        return ResultFactory.commonError("打回上一级失败，表单状态错误，请重试！");
    }

    /**
     * 撤回
     *
     * @return
     */
    public CommonResult reCall() {
        if (this.formStatus == FormStatus.UA) {
            this.endDate = new Date();
            this.formStatus = FormStatus.RC;
            lifeCycle = "F";
            Iterator<FormApprover> it = formApprovers.iterator();
            while(it.hasNext()){
                FormApprover approver = it.next();
                if (approver.getApproveStatus() == ApproveStatus.U || approver.getApproveStatus() == ApproveStatus.W) {
                    it.remove();
                }
            }
        }
        return ResultFactory.commonSuccess();
    }


//    public CommonResult passed(Long appUserId, String appUserName, String reason) {
//        //this.beginDate=new Date();
//        formStatus = FormStatus.UA;
//        for (FormApprover approver : formApprovers) {
//            if (appUserId == approver.getAppUserId() && approver.getAppType() == AppType.A) {
//                if (approver.pass(appUserId, appUserName, reason).getIsSuccess()) {
//                    proviousApprover = appUserName;
//                    //最后一级是不以略过的
//                    if (activeNextApprover(approver.getNextApproverId()).getMsg() == "noNext") {
//                        formEnd(FormStatus.AP);
//                    }
//                }
//            }
//        }
//        lifeCycle = "R";
//        return ResultFactory.commonSuccess();
//    }

    /**
     * 取消表单（表单删除）
     *
     * @return
     */
    public CommonResult cancel() {
        if (this.formStatus == FormStatus.UA || this.formStatus == FormStatus.WA) {
            this.endDate = new Date();
            this.formStatus = FormStatus.DE;
            lifeCycle = "F";
            Iterator<FormApprover> it = formApprovers.iterator();
            while(it.hasNext()){
                FormApprover approver = it.next();
                if (approver.getApproveStatus() == ApproveStatus.U || approver.getApproveStatus() == ApproveStatus.W) {
                    it.remove();
                }
            }
        }
        return ResultFactory.commonSuccess();
    }

    public Long getParentFormNo() {
        return parentFormNo;
    }

    public void setParentFormNo(Long parentFormNo) {
        this.parentFormNo = parentFormNo;
    }

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public Boolean getIsStepComplete() {
        return isStepComplete;
    }

    public void setIsStepComplete(Boolean isStepComplete) {
        this.isStepComplete = isStepComplete;
    }


    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
    }

    public Boolean getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(Boolean noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public String getProviousApprover() {
        return proviousApprover;
    }

    public void setProviousApprover(String proviousApprover) {
        this.proviousApprover = proviousApprover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<FormApprover> getFormApprovers() {
        return formApprovers;
    }

    public void setFormApprovers(List<FormApprover> formApprovers) {
        this.formApprovers = formApprovers;
    }

    public Long getBlongDept() {
        return blongDept;
    }

    public void setBlongDept(Long blongDept) {
        this.blongDept = blongDept;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getFormKindName() {
        return formKindName;
    }

    public void setFormKindName(String formKindName) {
        this.formKindName = formKindName;
    }

    public String getBlongDeptName() {
        return blongDeptName;
    }

    public void setBlongDeptName(String blongDeptName) {
        this.blongDeptName = blongDeptName;
    }

//    public String getSite() {
//        return site;
//    }
//
//    public void setSite(String site) {
//        this.site = site;
//    }


    public Boolean getHasApplyView() {
        return hasApplyView;
    }

    public void setHasApplyView(Boolean hasApplyView) {
        this.hasApplyView = hasApplyView;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public AppType getCurrentAppType() {
        return currentAppType;
    }

    public void setCurrentAppType(AppType currentAppType) {
        this.currentAppType = currentAppType;
    }


    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Integer getMaxSerial() {
        return maxSerial;
    }

    public void setMaxSerial(Integer maxSerial) {
        this.maxSerial = maxSerial;
    }

    public Integer getNextAppListSerial() {
        return nextAppListSerial;
    }

    public void setNextAppListSerial(Integer nextAppListSerial) {
        this.nextAppListSerial = nextAppListSerial;
    }

    public Integer getCurrentSerial() {
        return currentSerial;
    }

    public void setCurrentSerial(Integer currentSerial) {
        this.currentSerial = currentSerial;
    }


    public String getCurrentViewAction() {
        return currentViewAction;
    }

    public void setCurrentViewAction(String currentViewAction) {
        this.currentViewAction = currentViewAction;
    }

    public String getCurrentApproveAction() {
        return currentApproveAction;
    }

    public void setCurrentApproveAction(String currentApproveAction) {
        this.currentApproveAction = currentApproveAction;
    }

    public List<InnerFormApprovePoint> getInnerFormApprovePoints() {
        return innerFormApprovePoints;
    }

    public void setInnerFormApprovePoints(List<InnerFormApprovePoint> innerFormApprovePoints) {
        this.innerFormApprovePoints = innerFormApprovePoints;
    }

    public String getCurrentPointName() {
        return currentPointName;
    }

    public void setCurrentPointName(String currentPointName) {
        this.currentPointName = currentPointName;
    }

    public Long getCurrentAppPointId() {
        return currentAppPointId;
    }

    public void setCurrentAppPointId(Long currentAppPointId) {
        this.currentAppPointId = currentAppPointId;
    }

    public Long getNextAppPointId() {
        return nextAppPointId;
    }

    public void setNextAppPointId(Long nextAppPointId) {
        this.nextAppPointId = nextAppPointId;
    }

    public Boolean getIsCurrentPiontComplete() {
        return isCurrentPiontComplete;
    }

    public void setIsCurrentPiontComplete(Boolean isCurrentPiontComplete) {
        this.isCurrentPiontComplete = isCurrentPiontComplete;
    }
//
//    public Long getWithDeptId() {
//        return withDeptId;
//    }
//
//    public void setWithDeptId(Long withDeptId) {
//        this.withDeptId = withDeptId;
//    }
}
