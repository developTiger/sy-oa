package com.sunesoft.lemon.deanery.FormFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.FormFlow.domain.Countersign;
import com.sunesoft.lemon.deanery.FormFlow.domain.CountersignRepository;
import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangwj on 2016/8/5 0005.
 */
@Service(value = "countersignRepository")
public class CountersignRepositoryImpl extends GenericHibernateRepository<Countersign,Long> implements CountersignRepository {
    @Autowired
    private FormHeaderRepository formHeaderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 判断是否全部同意
     * @param formNo
     * @return
     */
    @Override
    public boolean counterAllOver(Long formNo){
        Criteria criteria = this.getSession().createCriteria(Countersign.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        List<Countersign> countersigns = criteria.list();
        int cnt = 0;
        for(Countersign countersign : countersigns){
            if(countersign.getCountResult() == 0)
                cnt++;
        }
        return cnt == countersigns.size() ? true : false;
    }

    /**
     * 会签通过后的处理(包括全部通过后的处理)
     * @param formNo
     * @param appUserId 签核人ID
     * @param countRemark 审核意见
     * @param countResult 审核结果 0 同意 1 不同意
     * @return 返回当前表单名称
     */
    @Override
    public CommonResult counterAccess(Long formNo, Long appUserId, String countRemark, Integer countResult) {
        FormHeader header = formHeaderRepository.get(formNo);
        Criteria criteria = this.getSession().createCriteria(Countersign.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("counterId",appUserId));
        List<Countersign> countersigns = criteria.list();
        if(countersigns != null && countersigns.size() > 0){
            Countersign countersign = countersigns.get(0);
            countersign.setCountRemark(countRemark);
            countersign.setCountResult(countResult);
            if(save(countersign) > 0) {
                if(counterAllOver(formNo)){
                    //当全部会签都通过的时候处理相应的方法
                    criteria = this.getSession().createCriteria(FormApprover.class);
                    criteria.add(Restrictions.eq("formNo",formNo));
                    criteria.add(Restrictions.eq("approveStatus", ApproveStatus.W));
                    criteria.add(Restrictions.ne("clStep",header.getClStep()));
                    criteria.add(Restrictions.eq("rownum",1));
                    criteria.addOrder(Order.asc("appSerial"));
                    FormApprover approver = ((List<FormApprover>) criteria.list()).get(0);
                    if(approver.activeApprover().getIsSuccess()){
                        header.setCurrentAppType(approver.getAppType());
                        header.setClStep(approver.getClStep());
                        header.setIsStepComplete(false);
                    }
                }
                if(formHeaderRepository.save(header) > 0)
                    return ResultFactory.commonSuccess();
                else
                    return ResultFactory.commonError("会签审核失败");
            }else
                return ResultFactory.commonError("会签审核失败");
        }else
            return ResultFactory.commonError("不能进行会签");
    }
    /**
     * 启动会签流程
     * 如果是统一启动会签则DTO中直接放入FormNo即可
     * @param counterIdList 会签人员ID列表
     * @param dto
     * @param isSpecial 0 正常启动会签 1 需要统一启动会签
     * @return
     */
    @Override
    public CommonResult beginCounterAccess(String[] counterIdList,ApproveFormDto dto,Integer isSpecial) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        for(String counterId : counterIdList){
            Employee employee = employeeRepository.get(StringCommHelper.nullToLong(counterId));
            if(employee != null) {
                Countersign countersign = new Countersign();
                countersign.setCounterId(StringCommHelper.nullToLong(counterId));
                countersign.setCounterName(employee.getName());
                countersign.setFormNo(dto.getFormNo());
                countersign.setFormKind(header.getFormKind());
                if(save(countersign) <= 0)
                    return ResultFactory.commonError("流程触发失败！");
            }
        }
        if(isSpecial == 1)
            return ResultFactory.commonSuccess();
        else {
            header.setFormStatus(FormStatus.NC);
            header.setClStep(header.getClStep() + 1);//基本都是以下一步为会签做要求
            header.setProviousApprover(dto.getApproverName());
            List<FormApprover> approvers = header.getFormApprovers();
            for (FormApprover approver : approvers) {
                if (approver.getAppUserId() == dto.getApproverId() && approver.getApproveStatus() == ApproveStatus.U && approver.getAppType() == AppType.A) {
                    if (approver.approve(dto.getApproverId(), dto.getApproverName(), dto.getContent()).getIsSuccess())
                        break;
                    else
                        return ResultFactory.commonError("流程处理失败！");
                }
            }
            if (formHeaderRepository.save(header) > 0)
                return ResultFactory.commonSuccess();
            else
                return ResultFactory.commonError("审核失败");
        }
    }

    /**
     * 会签否决退回处理
     * @param appUserId
     * @param countRemark
     * @param countResult
     * @param formNo
     * @return
     */
    @Override
    public CommonResult rejectCounterAccess(Long appUserId,String countRemark,Integer countResult,Long formNo) {
        String appUserName = "";
        FormHeader header = formHeaderRepository.get(formNo);
        Criteria criteria = this.getSession().createCriteria(Countersign.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        List<Countersign> countersigns = criteria.list();
        if(countersigns != null && countersigns.size() > 0){
            for(Countersign countersign : countersigns){
                if(countersign.getCounterId() == appUserId){
                    countersign.setCountRemark(countRemark);
                    countersign.setCountResult(countResult);
                    appUserName = countersign.getCounterName();
                }
                if(save(countersign) <= 0)
                    return ResultFactory.commonError("会签审核失败");
            }
            header.setFormStatus(FormStatus.UA);
            header.setIsStepComplete(false);
            header.setClStep(header.getClStep()-1);
            for(FormApprover approver : header.getFormApprovers()){
                if(header.getClStep() == approver.getClStep()){
                    approver.setApproveStatus(ApproveStatus.U);
                }else if(header.getClStep() + 1 == approver.getClStep() && approver.getApproveStatus() == ApproveStatus.U){
                    approver.setAppContent(countRemark);
                    approver.setAppActor(appUserId);
                    approver.setAppActorName(appUserName);
                    approver.setAppValue(AppValue.B);
                    approver.setEndDate(new Date());
                }
            }
            if(formHeaderRepository.save(header) > 0)
                return ResultFactory.commonSuccess();
            else
                return ResultFactory.commonError("会签审核失败");
        }else
            return ResultFactory.commonError("不能进行会签");
    }
}
