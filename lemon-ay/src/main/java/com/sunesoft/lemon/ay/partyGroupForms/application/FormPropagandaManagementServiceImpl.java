package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.PropagandaManagementService;
import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagement;
import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagementRepository;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormPropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagement;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagementRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/5.
 */
@Service("formPropagandaManagementService")
public class FormPropagandaManagementServiceImpl extends FormBase2<FormPropagandaManagement,FormPropagandaManagementDto> implements FormPropagandaManagementService {

    @Autowired
    FormPropagandaManagementRepository formPropagandaManagementRepository;

    @Autowired
    PropagandaManagementRepository propagandaManagementRepository;

    @Override
    protected CommonResult save(FormPropagandaManagement formPropagandaManagement) {
        formPropagandaManagementRepository.save(formPropagandaManagement);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormPropagandaManagement formPropagandaManagement) {
        return null;
    }

    @Override
    protected FormPropagandaManagement ConvetDto(FormPropagandaManagementDto Dto) {
        FormPropagandaManagement formPropagandaManagement = DtoFactory.convert(Dto,new FormPropagandaManagement());
        return formPropagandaManagement;
    }

    @Override
    protected String getSummery(FormPropagandaManagement formPropagandaManagement) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormPropagandaManagement formPropagandaManagement=this.getByFormNo(formNo);
        PropagandaManagement propagandaManagement = DtoFactory.convert(formPropagandaManagement,new PropagandaManagement());
        propagandaManagement.setId(null);
        propagandaManagement.setFormstatus("通过");
        propagandaManagementRepository.save(propagandaManagement);


//        List<FormPropagandaManagement> list=formPropagandaManagementRepository.getAll();
//
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getFormNo().equals(formNo)){
//                list.remove(i);
//                break;
//            }
//        }
//
//        for (FormPropagandaManagement pr:list){
//            formPropagandaManagementRepository.save(pr);
//        }

        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormPropagandaManagement getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormPropagandaManagement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormPropagandaManagement)criteria.uniqueResult();
    }

    @Override
    public FormPropagandaManagementDto getFormByFormNo(Long formNo) {
        FormPropagandaManagement formPropagandaManagement = this.getByFormNo(formNo);
        FormPropagandaManagementDto formPropagandaManagementDto = DtoFactory.convert(formPropagandaManagement,new FormPropagandaManagementDto());
        return formPropagandaManagementDto;
    }

    @Override
    public FormPropagandaManagementDto getById(Long id) {
        FormPropagandaManagement formPropagandaManagement=formPropagandaManagementRepository.getById(id);
        FormPropagandaManagementDto dto = DtoFactory.convert(formPropagandaManagement,new FormPropagandaManagementDto());
        return dto;
    }

    @Override
    public CommonResult deleteProManagement(Long id) {
        formPropagandaManagementRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult updateProManagement(FormPropagandaManagementDto dto) {
        FormPropagandaManagement propagandaManagement=formPropagandaManagementRepository.getById(dto.getId());
        propagandaManagement.setTitle(dto.getTitle());
        propagandaManagement.setNewsType(dto.getNewsType());
        propagandaManagement.setAuthor(dto.getAuthor());
        formPropagandaManagementRepository.save(propagandaManagement);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {

        FormPropagandaManagement management = formPropagandaManagementRepository.getByFormNo(dto.getFormNo());
        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false")
                && dto.getAppValue().equals(AppValue.Y.ordinal())) {

            String newsType = param.get("newsType").toString();
            String anthor = param.get("author").toString();

            management.setNewsType(newsType);
            if (!StringUtils.isNullOrWhiteSpace(anthor)){
                management.setAuthor(anthor);
            }else{
                return ResultFactory.commonError("请填写作者名称！");
            }

        }


        formPropagandaManagementRepository.save(management);

//        return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        return super.doApprove(dto, param);
    }

}
