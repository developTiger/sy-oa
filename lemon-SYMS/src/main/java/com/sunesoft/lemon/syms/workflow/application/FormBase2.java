package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.rtx.*;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.domain.*;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.apache.poi.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by zhouz on 2016/6/16.
 */
public abstract class FormBase2<T extends BaseFormEntity, D> extends GenericHibernateFinder {

    @Autowired
    protected FormApproveListService formApproveListService;
    @Autowired
    protected FormHeaderRepository formHeaderRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected FormListRepository formListRepository;


    @Autowired
    protected DeptmentRepository deptmentRepository;

    @Autowired
    Logger logger;

    /**
     * 新增表单数据
     *
     * @param dto 表单信息extends BaseFormDto
     * @return CommonResult
     */
    public CommonResult addByDto(D dto) {


        T t = ConvetDto(dto);
        if (t.getId() != null && t.getId() > 0) {
            return this.updateByDto(dto);
        }
        FormList formInfo = formListRepository.getByFormKind(t.getFormKind());
        CommonResult result;
        //     System.out.println(formInfo.getFormKind()+"******************"+getSummery(t));
        if (!formHeaderRepository.isExistSameTitle(formInfo.getFormKind(), getSummery(t), null)) {

            t.setFormType(formInfo.getFormType());
            t.setHasApplyView(formInfo.getHasApplyView());
            result = intiHeader(formInfo, t.getFormKind(), t.getFormKindName(), t.getApplyer(), t.getApplyerName(), t.getDeptId(), t.getBlongDeptId(), t.getBlongDeptName(), t.getDeptName(), getSummery(t), t.getViewUrl(), t.getParentFormNo());
            if (result.getIsSuccess()) {
                t.setFormNo(result.getId());
                //todo 慢点
                CommonResult commonResult = save(t);
                if (!commonResult.getIsSuccess()) {
                    return commonResult;
                }
            }
        } else {
            return ResultFactory.commonError("表单已存在");
        }
        return result;
    }

    /**
     * 更新表单状态
     *
     * @param formNo
     * @param status
     * @return
     */
    protected CommonResult updateFormStstus(Long formNo, Integer step, Boolean stepIsComplete, FormStatus status, String approveAction, String viewAction) {
        T t = this.getByFormNo(formNo);
        t.setClStep(step);
        t.setIsComplete(stepIsComplete);
        t.setFormStatus(status);
        t.setLastUpdateTime(new Date());
        t.setCurrentApproveAction(approveAction);
        t.setCurrentViewAction(viewAction);
        return this.save(t);
    }

    /**
     * 更新表单状态
     *
     * @param formNo
     * @param status
     * @return
     */
    protected <V extends BaseFormEntity> CommonResult updateSubFormStstus(Long formNo, Integer step, Boolean stepIsComplete, FormStatus status, String approveAction, String viewAction) {
        V t = this.getBySubFormNo(formNo);
        t.setClStep(step);
        t.setIsComplete(stepIsComplete);
        t.setFormStatus(status);
        t.setLastUpdateTime(new Date());
        t.setCurrentApproveAction(approveAction);
        t.setCurrentViewAction(viewAction);
        return this.subFormSave(t);
    }

    /**
     * 保存 表单信息数据
     *
     * @param t
     * @return
     */
    protected abstract CommonResult save(T t);

    /**
     * 保存 表单信息数据
     *
     * @param v
     * @return
     */
    protected <V extends BaseFormEntity> CommonResult subFormSave(V v) {
        return ResultFactory.commonError("not implement;");
    }

    /**
     * 更新表单数据
     *
     * @param t
     * @return
     */
    protected abstract CommonResult update(T t);


    protected CommonResult updateFormInfo(T t) {
        T t1 = getByFormNo(t.getFormNo());
        t1 = DtoFactory.convert(t, t1);
        this.save(t1);
        return ResultFactory.commonSuccess();
    }


//    /**
//     * 审核完善表单数据处理
//     * @param formNo 表单号
//     * @param param 参数
//     * @return CommonResult
//     */
//    public abstract CommonResult doBeforeApprove(Long formNo,Map<String,Object> param);


    /**
     * 更新表单数据
     *
     * @param dto
     * @return
     */
    public CommonResult updateByDto(D dto) {
        T t = ConvetDto(dto);
        return this.updateForm(t);
    }


    protected abstract T ConvetDto(D Dto);

    /**
     * 生成表单汇总信息
     *
     * @param t
     * @return
     */
    protected abstract String getSummery(T t);

    /**
     * 初始化表单头
     *
     * @param formKind     表单类型
     * @param formKindName 表单名称
     * @param applyer      申请人
     * @param applyerName  申请人姓名
     * @param deptId       部门ID
     * @param deptName     部门名称
     * @param summery      摘要
     * @return CommonResult
     */
    public CommonResult intiHeader(FormList formInfo, String formKind, String formKindName, Long applyer, String applyerName, Long deptId, Long blongDeptId, String blongDeptName, String deptName, String summery, String viewUrl, Long parentFormNo) {

        FormHeader header = new FormHeader(formKind, applyer);
        header.setFormKindName(formInfo.getFormName());
        header.setApplyerName(applyerName);
        header.setDeptId(deptId);
        header.setDeptName(deptName);
        if (blongDeptId != null && blongDeptId > 0)
            header.setBlongDept(blongDeptId);
        else
            header.setBlongDept(deptId);
        if (!StringUtils.isNullOrWhiteSpace(blongDeptName))
            header.setBlongDeptName(blongDeptName);
        else
            header.setBlongDeptName(deptName);
        //   header.setBlongDeptName(deptName);
        header.setSummery(summery);

        if (parentFormNo != null && parentFormNo > 0)
            header.setParentFormNo(parentFormNo);
        if (!StringUtils.isNullOrWhiteSpace(viewUrl)) {
            header.setViewUrl(viewUrl);
        } else {
            header.setViewUrl("forms");//兼容现有数据
        }
        if (formInfo.hasApplyView != null && formInfo.hasApplyView)
            header.setHasApplyView(formInfo.hasApplyView);
        else

            header.setHasApplyView(false);
        header.setFormType(formInfo.getFormType());
        List<FormApproveList> approveLists = formApproveListService.getApproveListByFormKind(formKind);
        //重新排序list
        Collections.sort(approveLists, new Comparator<FormApproveList>() {
            public int compare(FormApproveList arg0, FormApproveList arg1) {
                return arg0.getAppSerial().compareTo(arg1.getAppSerial());
            }
        });

        if (approveLists.size() > 0) {
            List<InnerFormApprovePoint> innerFormApprovePoints = new ArrayList<>();
            for (int i = 0; i < approveLists.size(); i++) {
                FormApproveList ll = approveLists.get(i);
                InnerFormApprovePoint in = new InnerFormApprovePoint();
                BeanUtils.copyProperties(ll, in);
                in.setAppSerial(i + 1);
                in.setId(null);

                innerFormApprovePoints.add(in);
            }
            header.setInnerFormApprovePoints(innerFormApprovePoints);
        }
        return ResultFactory.commonSuccess(formHeaderRepository.save(header));
    }


    /**
     * 更新表单
     *
     * @param t
     * @return
     */
    public CommonResult updateForm(T t) {

        FormHeader header = formHeaderRepository.get(t.getFormNo());
        header.setSummery(getSummery(t));

        header.setLastUpdateTime(new Date());
        formHeaderRepository.save(header);
        this.updateFormInfo(t);
        return ResultFactory.commonSuccess(header.getId());
    }

    /**
     * 提交表单
     *
     * @param formNo
     * @param formKind
     * @return
     */
    public CommonResult submitForm(Long formNo, String formKind) {
        FormHeader header = formHeaderRepository.getByKindAndNo(formNo, formKind);
        List<FormApprover> approvers = new ArrayList<FormApprover>();
        List<InnerFormApprovePoint> approveLists = header.getInnerFormApprovePoints();

        //重新排序list
        Collections.sort(approveLists, new Comparator<InnerFormApprovePoint>() {
            public int compare(InnerFormApprovePoint arg0, InnerFormApprovePoint arg1) {
                return arg0.getAppSerial().compareTo(arg1.getAppSerial());
            }
        });
        //Integer step = header.getClStep();
        for (int i = 0; i < approveLists.size(); i++) {
            InnerFormApprovePoint formApproveList = approveLists.get(i);
            FormApproveRole role = formApproveList.getAppRole();

            if (role == null) {
                return ResultFactory.commonError("流程配置错误，节点" + formApproveList.getAppSerial() + "审核人不存在！");
            }
            if (i < (approveLists.size() - 1))
                formApproveList.setNextPointId(approveLists.get(i + 1).getId());

            if ((header.getFormType().equals(0) && i == 0)|| header.getFormType().equals(1)) {
                List<Long> empIds = new ArrayList<>();
                if (role.getApprover().size() == 0) {
                    if (role.getRoleName().equals("APPLYER_SELF")) {
                        empIds.add(header.getApplyer());
                        approvers.addAll(getApprover(formApproveList, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), empIds, false));
                    } else if (role.getRoleName().equals("CHARGE_LEADERS")) {//设置分管领导审核
                        Long byDept;
                        if (formApproveList.getByDept()) {
                            if (formApproveList.getDeptType() == 0) {
                                byDept = header.getDeptId();
                            } else {
                                byDept = header.getBlongDept();
                            }
                        } else {
                            byDept = header.getDeptId();
                        }
                        Deptment deptment = deptmentRepository.get(byDept);
                        //todo 修改问分管领导的iD
//                        empIds.add(deptment.getChargeLeader().getId());
                        empIds.add(deptment.getChargeLeaderId());
                        approvers.addAll(getApprover(formApproveList, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), empIds, false));


                    } else {
                        return ResultFactory.commonError("流程配置错误，节点0审核人不存在！");
                    }
                }
                approvers.addAll(getApprover(formApproveList, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), null, false));
                //TODO  approver msg notify

                StringBuilder builder = new StringBuilder();
                List<Long> appUserIds = new ArrayList<>();
                for (FormApprover approver : approvers) {
                    appUserIds.add(approver.getAppUserId());
                }
                List<String> names = employeeRepository.getLoginName(appUserIds);
                if (null != names && names.size() > 0) {
                    for (String name : names) {
                        builder.append(name);
                        builder.append(";");
                    }

                    String yhm = builder.substring(0, builder.lastIndexOf(";"));
                    new SendNofifyThread(yhm, header.getFormKindName(), header.getApplyerName() + " 发起的流程需要您处理").start();

                }
            }
            if (i == 0) {//第一步签核 设置第一步的当前节点 ，和 Action
//                        formApprovePoint.setActive();

                header.setCurrentAppType(formApproveList.getAppType());
                header.setCurrentAppPointId(approveLists.get(i).getId());
                header.setCurrentPointName(approveLists.get(i).getFormApproveStepName());
                if (i < approveLists.size() - 1) {
                    header.setNextAppPointId(approveLists.get(i + 1).getId());
                }
                if (!StringUtils.isNullOrWhiteSpace(formApproveList.getViewAction()))
                    header.setCurrentViewAction(formApproveList.getViewAction());
                if (!StringUtils.isNullOrWhiteSpace(formApproveList.getApproveAction()))
                    header.setCurrentApproveAction(formApproveList.getApproveAction());

            }
        }

        header.submitForm(formNo, approvers);
        CommonResult result = header.activeForm();
        if (result.getIsSuccess())
            if (formHeaderRepository.save(header) > 0) {
                return  updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());

            } else
                return ResultFactory.commonError("保存表单失败");
        return result;
    }


    /**
     * 获取签核人列表
     *
     * @param formApprovePoint 签核节点信息
     * @param blongDept        归属部门
     * @param formNo           单号
     * @param formKind         类型
     * @param empids           替换现有签核人的ID
     * @param setActive        是否直接激活
     * @return List<FormApprover>
     */
    private List<FormApprover> getApprover(InnerFormApprovePoint formApprovePoint, Long applyDept, Long blongDept, Long formNo, String formKind, List<Long> empids, Boolean setActive) {
        FormApproveRole role = formApprovePoint.getAppRole();
        List<Employee> employees = new ArrayList<>();
        List<FormApprover> approvers = new ArrayList<FormApprover>();
        if (empids != null && empids.size() > 0) {
//            Map<Long, Long> empWithDepIds = employeeRepository.getEmpDeptIds(empids);
//            for(Long empId : empWithDepIds.keySet())
//            {
//                Employee employee =new Employee();
//                employee.setId(empId);
//                Deptment deptment=new Deptment();
//                deptment.setId(empWithDepIds.get(empId));
//                employee.setDept(deptment);
//                employees.add(employee);
//            }
            employees = employeeRepository.getByIds(empids);
        }
        else
            employees = role.getApprover();
        if (employees.size() > 0)
            for (Employee approver : employees) {
                if (!role.getRoleName().equals("APPLYER_SELF") && !role.getRoleName().equals("CHARGE_LEADERS")) {
                    if (formApprovePoint.getByDept()) {
                        Long byDept;
                        if (formApprovePoint.getDeptType() == 0) {
                            byDept = applyDept;
                        } else {
                            byDept = blongDept;
                        }

                        if (approver.getDept() != null)
                            if (!approver.getDept().getId().equals(byDept))
                                continue;

                    }
                }
                FormApprover formApprover = new FormApprover(formNo, formKind);
                formApprover.setAppUserId(approver.getId());
                formApprover.setAppName(approver.getName());

                formApprover.setRoleName(formApprovePoint.getFormApproveStepName());
                formApprover.setAppPointId(formApprovePoint.getId());
                formApprover.setAppNextPointId(formApprovePoint.getNextPointId());
                if (role.getRoleType() != null)
                    formApprover.setRoleType(role.getRoleType());
                if (formApprovePoint.getAppType() != null)
                    formApprover.setAppType(formApprovePoint.getAppType());
                formApprover.setAppSerial(formApprovePoint.getAppSerial());
                formApprover.setClStep(formApprovePoint.getClStep());//特殊步骤填入
                if (setActive)
                    formApprover.activeApprover();
                approvers.add(formApprover);

            }
        return approvers;
    }


    /**
     * 激活下一级签核人
     *
     * @param header 表头信息
     * @return 返回更新后表头信息
     */
    protected FormHeader activeNextApprover(final FormHeader header) {

        if (header.getNextAppPointId() != null && header.getNextAppPointId() > 0) {//have next approvePoint
            List<InnerFormApprovePoint> approveLists = header.getInnerFormApprovePoints();
            Long id = header.getNextAppPointId();
            header.setCurrentAppPointId(id);
            header.setIsCurrentPiontComplete(false);
            Boolean hasApproverAlready = false;
            for (FormApprover approver : header.getFormApprovers()) {
                if (approver.getAppPointId().equals(id) && approver.getApproveStatus() == ApproveStatus.W) {
                    approver.activeApprover();
                    hasApproverAlready = true;

                }
            }
            List<FormApprover> approvers = new ArrayList<FormApprover>();


            for (InnerFormApprovePoint li : approveLists) {

                if (li.getId().equals(id)) {
                    if (!hasApproverAlready) {
                        List<Long> listEmps = new ArrayList<>();
                        //该节点是否已指定审核人
                        if (li.getResetApproverIds() != null && li.getResetApproverIds().length() > 0) {
                            String[] empids = li.getResetApproverIds().split(",");

                            for (String e : empids) {
                                listEmps.add(Long.parseLong(e));
                            }
                        } else {
                            //是否是自己审核
                            if (li.getAppRole().getRoleName().equals("APPLYER_SELF")) {
                                //设置自己为审核人
                                listEmps.add(header.getApplyer());
                            }
                            if (li.getAppRole().getRoleName().equals("CHARGE_LEADERS")) {
                                Long byDept;
                                if (li.getByDept()) {
                                    if (li.getDeptType() == 0) {
                                        byDept = header.getDeptId();
                                    } else {
                                        byDept = header.getBlongDept();
                                    }
                                } else {
                                    byDept = header.getDeptId();
                                }
                                Deptment deptment = deptmentRepository.get(byDept);
                                //todo 修改问分管领导的iD
//                                listEmps.add(deptment.getChargeLeader().getId());
                                listEmps.add(deptment.getChargeLeaderId());
                                // approvers.addAll(getApprover(li, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), listEmps, false));
                            }
                        }
                        if (header.getBlongDept() != null && header.getClStep() == 2 && header.getFormKind().equals("SYY_DQ_LC07")&&header.getFormStatus().equals(FormStatus.UA)) { //特定的归口部门审核
//                            Long deptId=header.getWithDeptId();//todo 设定归口部门
                            approvers = getApprover(li, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), listEmps, true);

                        }
                        if(approvers.size()==0)  //说明前面没有处理，或者处理没有人
                            approvers = getApprover(li, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), listEmps, true);

//                        li.setActive();
                         header.getFormApprovers().addAll(approvers);

                        header.setCurrentAppType(li.getAppType());

                        //TODO  approver msg notify
                        final StringBuilder builder = new StringBuilder();
                        List<String> names = employeeRepository.getLoginName(listEmps);
                        if (null != names && names.size() > 0) {
                            for (String name : names) {
                                builder.append(name);
                                builder.append(";");
                            }

                            builder.substring(0, builder.lastIndexOf(";") - 1);

                            String yhm = builder.substring(0, builder.lastIndexOf(";"));
                            new SendNofifyThread(yhm, header.getFormKindName(), header.getApplyerName() + " 发起的流程需要您处理").start();
                        }
                    }


                    if (li.getClStep() != null && li.getClStep() > 0) {
                        header.setClStep(li.getClStep());
                        header.setIsStepComplete(false);
                    }
                    header.setCurrentAppType(li.getAppType());
                    //if (!StringUtils.isNullOrWhiteSpace(li.getViewAction()))
                    header.setCurrentViewAction(li.getViewAction());
                    //if (!StringUtils.isNullOrWhiteSpace(li.getApproveAction()))
                    header.setCurrentApproveAction(li.getApproveAction());

                    //   if (!StringUtils.isNullOrWhiteSpace(li.getViewAction()))
                    header.setCurrentViewAction(li.getViewAction());
                    header.setCurrentPointName(li.getFormApproveStepName());
                    // if (!StringUtils.isNullOrWhiteSpace(li.getApproveAction()))
                    header.setCurrentApproveAction(li.getApproveAction());

                    header.setNextAppPointId(li.getNextPointId());
                    break;
                }


            }

        }
        else

        { //formEnd
            header.formEnd(FormStatus.AP);
            header.setLifeCycle("F");
        }

        return header;
    }

    /**
     * 设置表单的签核节点为当前签核节点
     *
     * @param formNo         单号
     * @param setNextPointId 设置下一节点的ID
     * @return CommonResult
     */
    public CommonResult setPointAsNext(Long formNo, Long setNextPointId) {
        return setPointAsNext(formNo, setNextPointId, null);
    }


    /**
     * 设置节点为下一签核节点，并指定签核人
     *
     * @param formNo         单号
     * @param setNextPointId 下一节点的Id
     * @param empId          替换的审核人列表
     * @return CommonResult
     */
    public CommonResult setPointAsNext(Long formNo, Long setNextPointId, List<Long> empId) {
        FormHeader header = formHeaderRepository.get(formNo);
        return setPointAsNext(header, setNextPointId, empId);

    }

    public CommonResult setPointAsNext(FormHeader header, Long setNextPointId, List<Long> empId) {
        List<InnerFormApprovePoint> approveLists = header.getInnerFormApprovePoints();
        Long id = header.getNextAppPointId();
        List<FormApprover> approvers = new ArrayList<FormApprover>();
        for (InnerFormApprovePoint li : approveLists) {
            if (li.getId().equals(setNextPointId)) {

                Iterator<FormApprover> it = header.getFormApprovers().iterator();
                while (it.hasNext()) {
                    FormApprover approver = it.next();
                    if (approver.getAppPointId().equals(id))
                        if (approver.getApproveStatus() == ApproveStatus.W)
                            it.remove();
                }
                approvers = getApprover(li, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), empId, false);

                header.setNextAppPointId(setNextPointId);
//                List<Employee> employees = new ArrayList<>();
//                if (empId != null && empId.size() > 0)
//                    employees = employeeRepository.getByIds(empId);
//                else
//                    employees = li.getAppRole().getApprover();
//                FormApproveRole role = li.getAppRole();
//                for (Employee approver : employees) {
//                    if (role.getByDept()) {
//                        if (!approver.getDept().getId().equals(header.getBlongDept()))
//                            continue;
//                    }
//                    FormApprover formApprover = new FormApprover(formNo, header.getFormKind());
//                    formApprover.setAppUserId(approver.getId());
//                    formApprover.setAppName(approver.getName());
//                    formApprover.setRoleName(role.getRoleName());
//                    formApprover.setAppPointId(id);
//                    formApprover.setAppNextPointId(li.getNextPointId());
//                    if (role.getRoleType() != null)
//                        formApprover.setRoleType(role.getRoleType());
//                    if (role.getAppType() != null)
//                        formApprover.setAppType(role.getAppType());
//                    formApprover.setAppSerial(li.getAppSerial());
//                    formApprover.setClStep(li.getClStep());//特殊步骤填入
//                    approvers.add(formApprover);
//
//                }
            }
        }
        header.getFormApprovers().addAll(approvers);
        formHeaderRepository.save(header);
        return ResultFactory.commonSuccess();
    }

    /**
     * 重新设置下一级签核人列表
     *
     * @param formNo 单号
     * @param empId  要替换的下一级审核人列表
     * @return CommonResult
     */
    public CommonResult resetNextApprover(Long formNo, List<Long> empId) {
        FormHeader header = formHeaderRepository.get(formNo);
        List<InnerFormApprovePoint> approveLists = header.getInnerFormApprovePoints();
        Long id = header.getNextAppPointId();
        List<FormApprover> approvers = new ArrayList<FormApprover>();
        for (InnerFormApprovePoint li : approveLists) {
            if (li.getId().equals(id)) {
                Iterator<FormApprover> it = header.getFormApprovers().iterator();
                while (it.hasNext()) {
                    FormApprover approver = it.next();
                    if (approver.getAppPointId().equals(id) && approver.getApproveStatus() == ApproveStatus.W)
                        it.remove();
                }
                approvers = getApprover(li, header.getDeptId(), header.getBlongDept(), header.getId(), header.getFormKind(), empId, false);
                String strEmpids = "";
                for (int i = 0; i < empId.size(); i++) {
                    strEmpids += empId.get(i).toString();
                    if (i < empId.size() - 1) {
                        strEmpids += ",";
                    }
                }
                li.setResetApproverIds(strEmpids);
            }
        }

        header.getFormApprovers().addAll(approvers);
        formHeaderRepository.save(header);
        return ResultFactory.commonSuccess();
    }

    /**
     * 签核表单
     *
     * @param dto
     * @return
     */
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
//      CommonResult rr=doBeforeApprove(dto.getFormNo(), param);
//      if(!rr.getIsSuccess()){
//          return rr;
//      }
        if (dto.getAppValue().equals(AppValue.Y.ordinal())) {
            try {
                //同意的情况
                return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (dto.getAppValue().equals(AppValue.N.ordinal())) {
            //拒绝的情况
            return doReject(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        }
        if (dto.getAppValue().equals(AppValue.B.ordinal())) {
            //撤回的地方
            return backToProvs(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        }
        return ResultFactory.commonError("数据异常，签核失败！");
    }

    /**
     * 审核通过
     *
     * @param formNo     单号
     * @param formKind   类型
     * @param approverId 审核人Id
     * @param approver   审核人姓名
     * @param content    备注
     * @param agentId    代理人id
     * @return CommonResult
     * @throws Exception
     */
    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve2(approverId, approver, content, agentId);

        // step
        //如果审核人是组长

        // 更新当前节点未审核人FormApprover的状态   P

        //设置 header IsCurrentPiontComplete=true;

        if (header.getIsCurrentPiontComplete())
            header = this.activeNextApprover(header);

        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP) {

                result = doAafterAllApproved(formNo);

            } else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            result = updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            //subFormOperate(header.getId(),true,"ceshi",0);
            return result;
        } else
            return ResultFactory.commonError("签核失败");
    }

    /**
     * 同意调用跳转节点
     *
     * @param formNo
     * @param formKind
     * @param approverId
     * @param approver
     * @param content
     * @param agentId
     * @param clStep        下一跳转步骤的特殊属性
     * @param nextAppUserId 下一跳转步骤的签核人
     * @param appSerial     跳转节点的序号
     * @return
     * @throws Exception
     */
    public CommonResult doApprove(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId, Integer clStep, Long nextAppUserId, Integer appSerial) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve(approverId, approver, content, agentId, nextAppUserId, clStep, appSerial);
        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP) {
                result = doAafterAllApproved(formNo);

            } else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            return result;
        } else
            return ResultFactory.commonError("签核失败");
    }


    protected CommonResult subFormOperate(Long parentFormNo, Boolean isSubEnd, String approverName, Integer subStep) {
        Criteria criterion = getSession().createCriteria(FormHeader.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("parentFormNo", parentFormNo));
        List<FormHeader> headers = criterion.list();

        if (isSubEnd) {
            StringBuilder builder = new StringBuilder();
            for (FormHeader sub : headers) {
                builder.append(sub.getId());
                builder.append(",");
            }
            String forms = builder.toString().substring(0, builder.length() - 1);
            String sql = "begin update SYY_OA_FM_HEADER t set t.form_status=3, t.end_date=SYSDATE,t.life_cycle='F',t.provious_approver='" + approverName + "' where t.id in (" + forms + ");";

            sql += " update SYY_OA_FM_APPROVER a set a.approve_status=2,a.app_value=1,a.end_date=SYSDATE,a.app_actor_name='" + approverName + "' where a.form_no in (" + forms + ") and a.approve_status=0; end;";
            Query query = getSession().createSQLQuery(sql);
            query.executeUpdate();

        } else {
            for (FormHeader header : headers) {
                Long formNo = header.getId();
                for (InnerFormApprovePoint point : header.getInnerFormApprovePoints()) {
                    if (point.getClStep().equals(subStep)) {
                        this.setPointAsNext(header, point.getId(), null);

                        CommonResult result = header.approveNoCkeck(0L, approverName, "");
                        if (header.getIsCurrentPiontComplete())
                            header = this.activeNextApprover(header);


                        if (result.getIsSuccess()) {
                            if (header.getFormStatus() == FormStatus.AP)
                                result = doAafterAllApproved(formNo);
                            else
                                result = doAafterApprovedStep(formNo, result.getId());
                        }
                        if (result != null && !result.getIsSuccess()) {
                            return ResultFactory.commonError(result.getMsg());
                        }
                        if (formHeaderRepository.save(header) > 0) {
                            result = updateSubFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
                            return result;
                        } else
                            return ResultFactory.commonError("签核失败");
                    }
                }

            }
        }
        return ResultFactory.commonSuccess();
    }
//    public abstract CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param);


    /**
     * 否决
     *
     * @param formNo
     * @param formKind
     * @param approverId
     * @param approver
     * @param content
     * @param agentId
     * @return
     */
    public CommonResult doReject(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.reject(approverId, approver, content, agentId);
        if (!result.getIsSuccess()) {
            return result;
        }
        header.formEnd(FormStatus.RJ);
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());

            cancelChildForm(formNo);
            return result;
        }

//
//        String sql="update SYY_OA_FM_HEADER t set t.form_status=9, t.end_date=SYSDATE,t.life_cycle='F',t.provious_approver='申请人撤回' where t.parent_form_no="+formNo;
//
//
//        Query query = getSession().createQuery(sql);
//      List<FormHeader> headers=  formHeaderRepository.getHeaderByParentFormNo(formNo);
//        if(headers!=null&& headers.size()>0){
//            for(FormHeader h :headers) {
//                h.cancel();
//                formHeaderRepository.save(h);
//            }
//        }

        return ResultFactory.commonError("否决失败");
    }

    private void cancelChildForm(Long parentFormNo) {
        List<FormHeader> headers = formHeaderRepository.getHeaderByParentFormNo(parentFormNo);
        if (headers != null && headers.size() > 0) {
            for (FormHeader header : headers) {
                header.cancel();
                formHeaderRepository.save(header);
            }
        }
    }

    /**
     * 撤回
     *
     * @param formNo
     * @param formKind
     * @param content
     * @param approverId
     * @return
     */
    public CommonResult doRecall(Long formNo, String formKind, String content, Long approverId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result;
        if (approverId.equals(header.getApplyer())) {
            result = header.reCall();
            if (!result.getIsSuccess()) {
                return result;
            }
            header.formEnd(FormStatus.RC);
            if (formHeaderRepository.save(header) > 0) {
                updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());

                cancelChildForm(formNo);
                return result;
            }
        }

        // cancelChildForm(formNo);
        return ResultFactory.commonError("撤回失败！");
    }

    public CommonResult addApprover(ApproveFormDto dto, Long addUserId, Boolean isAfter, Long setId, int setSerial, List<Long> agentId) {

        return ResultFactory.commonError("加签失败");
    }

    public CommonResult changeApprover(ApproveFormDto dto, Long changeUserId, List<Long> agentId) {

        return ResultFactory.commonError("转签失败");
    }


    /**
     * 打回上一级
     *
     * @param formNo     单号
     * @param formKind   类型
     * @param approverId 审核人id
     * @param approver   审核人姓名
     * @param content    备注
     * @param agentId    代理人
     * @return CommonResult
     */
    public CommonResult backToProvs(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.goBack2(approverId, approver, content, agentId);

        if (header.getFormStatus() == FormStatus.UA)
            activeNextApprover(header);
        if (!result.getIsSuccess()) {
            return result;
        }
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            return result;
        }
        return ResultFactory.commonError("打回失败");

    }

    /**
     * 删除表单
     *
     * @param formNo   单号
     * @param formKind 类型
     * @return CommonResult
     */
    public CommonResult cancel(Long formNo, String formKind) {

        FormHeader header = formHeaderRepository.get(formNo);

        CommonResult result = header.cancel();
        ;
        if (!result.getIsSuccess()) {
            return result;
        } else if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            cancelChildForm(formNo);
            return result;
        }
        return ResultFactory.commonError("取消失败");
    }

    public abstract CommonResult doAafterApprovedStep(Long formNo, Long step);

    public abstract CommonResult doAafterAllApproved(Long formNo);

    protected abstract T getByFormNo(Long formNo);


    protected <V> V getBySubFormNo(Long formNo) {
        return null;
    }
}
