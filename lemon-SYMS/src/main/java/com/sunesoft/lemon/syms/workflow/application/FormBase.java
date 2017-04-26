package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.*;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by zhouz on 2016/6/16.
 */
public abstract class FormBase<T extends BaseFormEntity, D> extends GenericHibernateFinder {

    @Autowired
    protected FormApproveListService formApproveListService;

    @Autowired
    protected FormHeaderRepository formHeaderRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;


    /**
     * 新增表单数据
     *
     * @param t 表单信息extends BaseFormEntity
     * @return CommonResult
     */
    protected CommonResult Add(T t) {

        CommonResult result = intiHeader(t.getFormKind(), t.getFormKindName(), t.getApplyer(), t.getApplyerName(), t.getDeptId(), t.getBlongDeptId(), t.getBlongDeptName(), t.getDeptName(), getSummery(t), t.getViewUrl(), t.getParentFormNo(), t.getHasApplyView(), t.getFormType());
        if (result.getIsSuccess()) {
            t.setFormNo(result.getId());
            if (!save(t).getIsSuccess()) {
                return ResultFactory.commonError("新增失败！");
            }
        }
        return result;
    }


    /**
     * 新增表单数据
     *
     * @param dto 表单信息extends BaseFormEntity
     * @return CommonResult
     */
    public CommonResult addByDto(D dto) {

        T t = ConvetDto(dto);
        CommonResult result = intiHeader(t.getFormKind(), t.getFormKindName(), t.getApplyer(), t.getApplyerName(), t.getDeptId(), t.getBlongDeptId(), t.getBlongDeptName(), t.getDeptName(), getSummery(t), t.getViewUrl(), t.getParentFormNo(), t.getHasApplyView(), t.getFormType());
        if (result.getIsSuccess()) {
            t.setFormNo(result.getId());
            if (!save(t).getIsSuccess()) {
                return ResultFactory.commonError("新增失败！");
            }
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
    protected CommonResult updateFormStstus(Long formNo, Integer step, Boolean stepIsComplete, FormStatus status) {
        T t = this.getByFormNo(formNo);
        t.setClStep(step);
        t.setIsComplete(stepIsComplete);
        t.setFormStatus(status);
        t.setLastUpdateTime(new Date());
        return this.save(t);
    }

    /**
     * 保存 表单信息数据
     *
     * @param t
     * @return
     */
    protected abstract CommonResult save(T t);


    /**
     * 更新表单数据
     *
     * @param t
     * @return
     */
    protected abstract CommonResult update(T t);


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
     * 初始化表单
     *
     * @param formKind
     * @param formKindName
     * @param applyer
     * @param applyerName
     * @param deptId
     * @param deptName
     * @param summery
     * @return
     */
    public CommonResult intiHeader(String formKind, String formKindName, Long applyer, String applyerName, Long deptId, Long blongDeptId, String blongDeptName, String deptName, String summery, String viewUrl, Long parentFormNo, Boolean hasApplyView, Integer formType) {
        FormHeader header = new FormHeader(formKind, applyer);
        header.setFormKindName(formKindName);
        header.setApplyerName(applyerName);
        header.setDeptId(deptId);
        header.setDeptName(deptName);
        header.setFormType(formType);
        if (blongDeptId != null && blongDeptId > 0)
            header.setBlongDept(blongDeptId);
        else
            header.setBlongDept(deptId);
        if (!StringUtils.isNullOrWhiteSpace(blongDeptName))
            header.setBlongDeptName(blongDeptName);
        else
            header.setBlongDeptName(deptName);
        header.setBlongDeptName(deptName);
        header.setSummery(summery);
        if (hasApplyView != null && hasApplyView)
            header.setHasApplyView(hasApplyView);
        else
            header.setHasApplyView(false);
        if (parentFormNo != null && parentFormNo > 0)
            header.setParentFormNo(parentFormNo);
        if (!StringUtils.isNullOrWhiteSpace(viewUrl)) {
            header.setViewUrl(viewUrl);
        } else {
            header.setViewUrl("forms");//兼容现有数据
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
        this.update(t);
        return ResultFactory.commonSuccess();
    }

    /**
     * 提交表单
     *
     * @param formNo
     * @param formKind
     * @return
     */
    public CommonResult submitForm(Long formNo, String formKind) {
        List<FormApproveList> approveLists = formApproveListService.getApproveListByFormKind(formKind);
        //重新排序list
        Collections.sort(approveLists, new Comparator<FormApproveList>() {
            public int compare(FormApproveList arg0, FormApproveList arg1) {
                return arg0.getAppSerial().compareTo(arg1.getAppSerial());
            }
        });
        FormHeader header = formHeaderRepository.getByKindAndNo(formNo, formKind);
        List<FormApprover> approvers = new ArrayList<FormApprover>();
        header.setMaxSerial(approveLists.size());
        header.setCurrentSerial(1);
        header.setNextAppListSerial(2);
        if (approveLists.size() > 0) {
            Integer seral = 1;
            for (int i = 0; i < approveLists.size(); i++) {

                FormApproveRole role = approveLists.get(i).getAppRole();
                FormApproveList formApproveList = approveLists.get(i);

                if (role != null) {
                    if (role.getApprover().size() == 0)
                        continue;
                    for (Employee approver : role.getApprover()) {
                        if (role.getByDept()) {
                            if (!approver.getDept().getId().equals(header.getBlongDept()))
                                continue;
                        }
                        FormApprover formApprover = new FormApprover(formNo, formKind);

                        formApprover.setAppUserId(approver.getId());
                        formApprover.setAppName(approver.getName());
                        formApprover.setRoleName(role.getRoleName());
                        if (role.getRoleType() != null)
                            formApprover.setRoleType(role.getRoleType());
                        if (role.getAppType() != null)
                            formApprover.setAppType(role.getAppType());
                        formApprover.setAppSerial(seral);
                        formApprover.setClStep(formApproveList.getClStep());//特殊步骤填入
                        if (i < approveLists.size() - 1) {
                            formApprover.setNextApproverId(approveLists.get(i + 1).getAppRole().getApprover().get(0).getId());
                        }
                        approvers.add(formApprover);

                    }
                    if (header.getFormType()!=null&&header.getFormType() == 0) break;
                    seral++;
                }
            }
        }
        header.submitForm(formNo, approvers);
        CommonResult result = header.activeForm();
        if (result.getIsSuccess())
            if (formHeaderRepository.save(header) > 0) {
                updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
                return result;
            } else
                return ResultFactory.commonError("保存表单失败");
        return result;
    }


    public FormHeader activeNextApprover(FormHeader header) {
        List<FormApproveList> approveLists = formApproveListService.getApproveListByFormKind(header.getFormKind());
        Collections.sort(approveLists, new Comparator<FormApproveList>() {
            public int compare(FormApproveList arg0, FormApproveList arg1) {
                return arg0.getAppSerial().compareTo(arg1.getAppSerial());
            }
        });

        Integer seral = header.getCurrentSerial();
        if (approveLists.size() > 0) {

//            FormApproveList formApproveList = approveLists.get(header.getCurrentSerial());
            header = addNextApprover(header, approveLists);
        }


        return header;
    }
    public CommonResult setPointAsNext(Long formNo, Long setNextListId, List<Long> empId) {
        return ResultFactory.commonSuccess();
    }

    public CommonResult setPointAsNext(Long formNo, Long empId) {
        return ResultFactory.commonSuccess();
    }

    public CommonResult resetNextApprover(Long formNo, List<Long> empId) {
        return ResultFactory.commonSuccess();
    }
    public FormHeader addNextApprover(FormHeader header, List<FormApproveList> list) {

        Boolean isActiveInsertApprover = false;//是否本次激+活插入的审核人
        List<FormApprover> formApprovers = header.getFormApprovers();
         Integer thisRoleCount = list.size();
        if (thisRoleCount > header.getMaxSerial())
            header.setMaxSerial(list.size());

        List<FormApprover> currentApprovers = new ArrayList<FormApprover>();

        //判断有没有插入入当前级别的审核人，如果有 则激活，原有审核流顺延

        if (formApprovers != null && formApprovers.size() > 0) {
            for (FormApprover approver : formApprovers) {
                if (approver.getAppSerial().equals(header.getCurrentSerial() + 1)) {
                    isActiveInsertApprover = true;
                    approver.activeApprover();
                    currentApprovers.add(approver);

                }
            }
        }


        //如果没有插入审核人，则激活相应角色对应的审核人
        if (!isActiveInsertApprover) {
            if (!(header.getNextAppListSerial()<=thisRoleCount)) {
                return null;
            }
            FormApproveList approveList = list.get(header.getNextAppListSerial() - 1);
            FormApproveRole role = approveList.getAppRole();
            if (role != null) {
                if (role.getApprover().size() == 0)
                    return header;
                for (Employee approver : role.getApprover()) {
                    if (role.getByDept()) {
                        if (!approver.getDept().getId().equals(header.getBlongDept()))
                            continue;
                    }
                    FormApprover formApprover = new FormApprover(header.getId(), header.getFormKind());

                    formApprover.setAppUserId(approver.getId());
                    formApprover.setAppName(approver.getName());
                    formApprover.setRoleName(role.getRoleName());
                    if (role.getRoleType() != null)
                        formApprover.setRoleType(role.getRoleType());
                    if (role.getAppType() != null)
                        formApprover.setAppType(role.getAppType());
                    formApprover.setAppSerial(header.getCurrentSerial() + 1);
                    formApprover.setClStep(approveList.getClStep());//特殊步骤填入
                    formApprover.activeApprover();
                    currentApprovers.add(formApprover);

                }

            }
        }
        if (currentApprovers.size() > 0) {
            if (header.getCurrentSerial().equals(header.getMaxSerial()))
                header.formEnd(FormStatus.AP);
            else
                header.setCurrentSerial(header.getCurrentSerial() + 1);
            if (!isActiveInsertApprover) {
                if (header.hasNextRoleApprover(thisRoleCount))
                    header.setNextAppListSerial(header.getNextAppListSerial() + 1);
            }
            header.getFormApprovers().addAll(currentApprovers);
        }
        return header;
    }

    /**
     * 签核表单
     *
     * @param dto
     * @return
     */
    public CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param) {
//      CommonResult rr=doBeforeApprove(dto.getFormNo(), param);
//      if(!rr.getIsSuccess()){
//          return rr;
//      }
        if (dto.getAppValue().equals(AppValue.Y.ordinal())) {
            try {
                return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (dto.getAppValue().equals(AppValue.N.ordinal())) {
            return doReject(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        }
        if (dto.getAppValue().equals(AppValue.B.ordinal())) {
            return backToProvs(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        }
        return ResultFactory.commonError("数据异常，签核失败！");
    }

    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve(approverId, approver, content, agentId);
        if (header.getFormType()!=null&&header.getFormType().equals(0)) {
            if (header.hasNextSerial())
                header = this.activeNextApprover(header);
        }
        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP)
                result = doAafterAllApproved(formNo);
            else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            result = updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
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
     * @param clStep 下一跳转步骤的特殊属性
     * @param nextAppUserId 下一跳转步骤的签核人
     * @param appSerial 跳转节点的序号
     * @return
     * @throws Exception
     */
    public CommonResult doApprove(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId,Integer clStep,Long nextAppUserId,Integer appSerial) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve(approverId, approver, content, agentId,nextAppUserId,clStep,appSerial);
        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP)
                result = doAafterAllApproved(formNo);
            else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        } else
            return ResultFactory.commonError("签核失败");
    }

//    public abstract CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param);



    public CommonResult doReject(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.reject(approverId, approver, content, agentId);
        if (!result.getIsSuccess()) {
            return result;
        }
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        }
        return ResultFactory.commonError("撤回失败");
    }


    public CommonResult doRecall(Long formNo, String formKind, String content, Long approverId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result;
        if (approverId == header.getApplyer()) {
            result = header.reCall();
            if (!result.getIsSuccess()) {
                return result;
            }
            if (formHeaderRepository.save(header) > 0) {
                updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
                return result;
            }
        }

        return ResultFactory.commonError("撤回失败！");
    }

    public CommonResult addApprover(ApproveFormDto dto, Long addUserId, Boolean isAfter, Long setId, int setSerial, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        CommonResult result = header.addApprover(dto.getApproverId(), dto.getApproverName(), dto.getContent(), addUserId, isAfter, setId, setSerial, dto.getAgentId());
        if (!result.getIsSuccess()) {
            return result;
        } else if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        }
        return ResultFactory.commonError("加签失败");
    }

    public CommonResult changeApprover(ApproveFormDto dto, Long changeUserId, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        CommonResult result = header.changeApprover(dto.getApproverId(), dto.getApproverName(), dto.getContent(), changeUserId, agentId);
        if (!result.getIsSuccess()) {
            return result;
        } else if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        }
        return ResultFactory.commonError("转签失败");
    }


    public CommonResult backToProvs(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.goBack(approverId, approver, content, agentId);
        if (!result.getIsSuccess()) {
            return result;
        }
        if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        }
        return ResultFactory.commonError("打回失败");

    }

    public CommonResult cancel(Long formNo, String formKind) {

        FormHeader header = formHeaderRepository.get(formNo);

        CommonResult result = header.cancel();
        ;
        if (!result.getIsSuccess()) {
            return result;
        } else if (formHeaderRepository.save(header) > 0) {
            updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus());
            return result;
        }
        return ResultFactory.commonError("取消失败");
    }

    public abstract CommonResult doAafterApprovedStep(Long formNo, Long step);

    public abstract CommonResult doAafterAllApproved(Long formNo);

    protected abstract T getByFormNo(Long formNo);

}
