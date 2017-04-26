package com.sunesoft.lemon.syms.workflow.application.serviceSelector;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/30.
 */
public interface FormService<T extends BaseFormEntity,D> {

    //public CommonResult intiHeader(String formKind, String formKindName, Long applyer, String applyerName, Long deptId, String deptName, String summery,String viewUrl) ;

    /**
     * 新增表单
     * @param dto
     * @return
     */
    public  CommonResult addByDto(D  dto);

    /**
     * 更新表单
     * @param dto
     * @return
     */
    public CommonResult updateByDto(D  dto);



    /**
     * 提交表单那
     * @param formNo
     * @param formKind
     * @return
     */
    public CommonResult submitForm(Long formNo, String formKind);


    /**
     * 设置节点为下一级
     * @param formNo
     * @param setNextListId
     * @return
     */
    public CommonResult setPointAsNext(Long formNo, Long setNextListId);


    /**
     *                                                                                                                                                                                                  设置 节点为下一级 并指定审核人
     * @param formNo
     * @param setNextListId
     * @param empId
     * @return
     */
    public CommonResult setPointAsNext(Long formNo, Long setNextListId, List<Long> empId) ;

    /**
     * 重置下一级签核人
     * @param formNo
     * @param empId
     * @return
     */
    public CommonResult resetNextApprover(Long formNo, List<Long> empId);

    /**
     * 审核统一入口
     * @param dto
     * @param param
     * @return
     */
    public CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param);

    /**
     * 审核通过
     * @param formNo
     * @param formKind
     * @param approverId
     * @param approver
     * @param content
     * @param agentId
     * @return
     * @throws Exception
     */
    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception;

    /**
     * 否决
     * @param formNo
     * @param formKind
     * @param approverId
     * @param approver
     * @param content
     * @param agentId
     * @return
     */
    public CommonResult doReject(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId);


    /**
     * 退回
     * @param formNo
     * @param formKind
     * @param approverId
     * @param approver
     * @param content
     * @param agentId
     * @return
     */
    public CommonResult backToProvs(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId);


    /**
     * 撤回
     * @param formNo
     * @param formKind
     * @param content
     * @param approverId
     * @return
     */
    public CommonResult doRecall(Long formNo, String formKind, String content, Long approverId);

    public CommonResult addApprover(ApproveFormDto dto, Long addUserId, Boolean isAfter, Long setId, int setSerial, List<Long> agentId) ;

    public CommonResult changeApprover(ApproveFormDto dto, Long changeUserId, List<Long> agentId) ;


    /**
     * 删除
     * @param formNo
     * @param formKind
     * @return
     */
    public CommonResult cancel(Long formNo, String formKind);


    /**
     * 根据单号 获取表单
     * @param formNo
     * @return
     */
    D getFormByFormNo(Long formNo);


}
