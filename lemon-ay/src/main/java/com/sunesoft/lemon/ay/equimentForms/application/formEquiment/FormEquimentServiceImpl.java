package com.sunesoft.lemon.ay.equimentForms.application.formEquiment;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipment;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipmentRepository;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/15.
 */
@Service("formEquimentService")
public class FormEquimentServiceImpl extends FormBase2<FormEquipment,FormEquipmentDto> implements FormEquimentService{

    @Autowired
    FormEquipmentRepository formEquipmentRepository;

    @Autowired
    EquipmentRepository equipmentRepository;


    @Override
    protected CommonResult save(FormEquipment formEquipment) {
        formEquipmentRepository.save(formEquipment);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormEquipment formEquipment) {
        FormEquipment equipment = formEquipmentRepository.get(formEquipment.getId());
        equipment.setUnitName(formEquipment.getUnitName());//使用单位
        equipment.setSavePersonName(formEquipment.getSavePersonName());
        equipment.setFactoryName(formEquipment.getFactoryName());//出厂单位
        equipment.setOriginal(formEquipment.getOriginal());//原值
        equipment.setParts(formEquipment.getParts());//配置清单
        equipment.setStandard(formEquipment.getStandard());//规格
        equipment.setMeasuringName(formEquipment.getMeasuringName());//仪器名称
        equipment.setFactoryName(formEquipment.getFactoryName());//生产厂家
        equipment.setOutFactoryNum(formEquipment.getOutFactoryNum());//出厂标号

        return ResultFactory.commonSuccess(formEquipmentRepository.save(equipment));
    }

    @Override
    protected FormEquipment ConvetDto(FormEquipmentDto Dto) {
        FormEquipment formEquipment = new FormEquipment();
        formEquipment = DtoFactory.convert(Dto,formEquipment);
        return formEquipment;
    }

    @Override
    protected String getSummery(FormEquipment formEquipment) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormEquipment formEquipment = this.getByFormNo(formNo);
        Equipment equipment= DtoFactory.convert(formEquipment, new Equipment());
        equipment.setId(null);
        equipmentRepository.save(equipment);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormEquipment getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(FormEquipment.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormEquipment)criteria.uniqueResult();
    }

    @Override
    public FormEquipmentDto getFormByFormNo(Long formNo) {
        return DtoFactory.convert(this.getByFormNo(formNo),new FormEquipmentDto());
    }

    @Override
    public List<FormEquipment> getLists() {
        List<FormEquipment> list =  formEquipmentRepository.getAll();
      return list ;
    }
}
