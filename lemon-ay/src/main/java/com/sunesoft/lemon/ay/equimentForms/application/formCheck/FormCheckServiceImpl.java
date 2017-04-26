package com.sunesoft.lemon.ay.equimentForms.application.formCheck;

import com.sunesoft.lemon.ay.equimentForms.domain.FormCheck;
import com.sunesoft.lemon.ay.equimentForms.domain.FormCheckRepository;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormCheckDto;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.Check;
import com.sunesoft.lemon.ay.equipment.domain.CheckRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Service("formCheckService")
public class FormCheckServiceImpl extends FormBase2<FormCheck,FormCheckDto> implements  FormCheckService {
    @Autowired
    FormCheckRepository formCheckRepository;

    @Autowired
    CheckRepository checkRepository;
    @Autowired
    EquipmentService equipmentService;


    @Override
    protected CommonResult save(FormCheck formCheck) {
        formCheckRepository.save(formCheck);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormCheck formCheck) {
        FormCheck fc =  formCheckRepository.get(formCheck.getId());
        fc.setServiceUnit(formCheck.getServiceUnit());//维修方
        fc.setNum(formCheck.getNum());//设备数量
        fc.setRepairProject(formCheck.getRepairProject());//维修项目
        fc.setRunCondition(formCheck.getRunCondition());//维修项目运行情况
        formCheckRepository.save(fc);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormCheck ConvetDto(FormCheckDto Dto) {
        FormCheck formCheck = new FormCheck();
        formCheck =  DtoFactory.convert(Dto, formCheck);
        return formCheck;
    }

    @Override
    protected String getSummery(FormCheck formCheck) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormCheck formCheck =  this.getByFormNo(formNo);
        Check fc =  DtoFactory.convert(formCheck, new Check());
        fc.setId(null);
        checkRepository.save(fc);
        //修改设备状态为正常
        CommonResult c=equipmentService.setStation(formCheck.getResId(), EquipmentStation.Normal);
        if(c.getIsSuccess())
        return ResultFactory.commonSuccess();
        else
            return ResultFactory.commonError("设备状态修改失败");

    }

    @Override
    protected FormCheck getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(FormCheck.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormCheck)criteria.uniqueResult();
    }

    @Override
    public FormCheckDto getFormByFormNo(Long formNo) {
        return DtoFactory.convert(this.getByFormNo(formNo),new FormCheckDto());
    }
}
