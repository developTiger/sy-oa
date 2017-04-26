package com.sunesoft.lemon.ay.equimentForms.application.formEquiMain;

import com.sunesoft.lemon.ay.equimentForms.domain.*;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquiMainDto;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.*;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangkefan on 2016/8/17.
 */
@Service("formEquiMainService")
public class FormEquiMainServiceImpl extends FormBase2<FormEquipMain,FormEquiMainDto> implements FormEquiMainService{

    @Autowired
    FormEquipmentRepository formEquipmentRepository;


    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    FormEquipMainRepository formEquipMainRepository;

    @Autowired
    EquipmentMaintenanceRepository repository;

    @Autowired
    EquipmentService equipmentService;

    @Override
    protected CommonResult save(FormEquipMain formEquipMain) {

        formEquipMainRepository.save(formEquipMain);

        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormEquipMain formEquipMain) {
        FormEquipMain main  = new FormEquipMain();
        main.setCost(formEquipMain.getCost());
        formEquipMainRepository.save(main);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormEquipMain ConvetDto(FormEquiMainDto Dto) {
        FormEquipMain formEquipMain = new FormEquipMain();

        formEquipMain = DtoFactory.convert(Dto, formEquipMain);
        if(Dto.getEquipmentDto().getId()!=null){
            formEquipMain.setEquipment(equipmentRepository.get(Dto.getEquipmentDto().getId()));
        }

        return formEquipMain;
    }

    @Override
    protected String getSummery(FormEquipMain formEquipMain) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormEquipMain formEquipMain =  this.getByFormNo(formNo);
        EquipmentMaintenance equipmentMaintenance= DtoFactory.convert(formEquipMain,new EquipmentMaintenance());
        equipmentMaintenance.setLinkId(formEquipMain.getResId());//字段不一样

//        equipmentMaintenance.setResId(null);
        equipmentMaintenance.setId(null);
        repository.save(equipmentMaintenance);
        //all approved
        //修改设备状态为 维修
        CommonResult c=equipmentService.setStation(formEquipMain.getResId(), EquipmentStation.Repair);
        if(c.getIsSuccess())
        return ResultFactory.commonSuccess();
        else
            return ResultFactory.commonError("设备状态更新失败");
    }

    @Override
    protected FormEquipMain getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormEquipMain.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormEquipMain)criteria.uniqueResult();
    }

    @Override
    public FormEquiMainDto getFormByFormNo(Long formNo) {
        FormEquipMain formEquipMain =  this.getByFormNo(formNo);
        FormEquiMainDto formEquiMainDto =DtoFactory.convert(formEquipMain,new FormEquiMainDto());
        formEquiMainDto.setEquipmentDto(DtoFactory.convert(formEquipMain.getEquipment(), new EquipmentDto()));//设置两个表关联的字段，字段为一个对象，是多对一的形式
        return formEquiMainDto;
    }

}
