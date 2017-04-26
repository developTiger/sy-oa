package com.sunesoft.lemon.ay.equimentForms.application.formEquiStatus;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquiStatusDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquiStatus;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipStatusRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/16.
 */
@Service("formEquiStatusService")
public class FormEquiStatusServiceImpl extends FormBase2<FormEquiStatus,FormEquiStatusDto> implements FormEquiStatusService{

    @Autowired
    FormEquipStatusRepository formEquipStatusRepository;

    @Override
    protected CommonResult save(FormEquiStatus formEquiStatus) {
        formEquipStatusRepository.save(formEquiStatus);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormEquiStatus formEquiStatus) {
        FormEquiStatus formEquiS = new FormEquiStatus();
        formEquiS.setStatus(formEquiStatus.getStatus());
        formEquiS.setStopTime(formEquiS.getStopTime());
        formEquiS.setRunTime(formEquiS.getRunTime());
        formEquiS.setPerson(formEquiS.getPerson());
        formEquiS.setWorkContent(formEquiS.getWorkContent());
        formEquiS.setName(formEquiS.getName());
        formEquipStatusRepository.save(formEquiS);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormEquiStatus ConvetDto(FormEquiStatusDto Dto) {
        FormEquiStatus formEquiStatus = new FormEquiStatus();
        formEquiStatus= DtoFactory.convert(Dto,formEquiStatus);
        return formEquiStatus;
    }

    @Override
    protected String getSummery(FormEquiStatus formEquiStatus) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
//        FormEquiStatus formEquiStatus = new FormEquiStatus();
//        FormEquiStatusFlowDto fsd = new FormEquiStatusFlowDto();
//        fsd = DtoFactory.convert(formEquiStatus,fsd);
//        formEquipStatusFlowService.add(fsd);
//        return ResultFactory.commonSuccess();
        return null;
    }

    @Override
    protected FormEquiStatus getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(FormEquiStatus.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormEquiStatus)criteria.uniqueResult();
    }

    @Override
    public FormEquiStatusDto getFormByFormNo(Long formNo) {
        return DtoFactory.convert(this.getByFormNo(formNo), new FormEquiStatusDto());
    }
}
