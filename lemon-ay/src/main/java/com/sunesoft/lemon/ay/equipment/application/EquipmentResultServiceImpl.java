package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentResultDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.*;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Education;
import jxl.demo.Demo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@Service("equipmentResultService")
public class EquipmentResultServiceImpl extends GenericHibernateRepository<EquipmentResultDto,Long> implements  EquipmentResultService {
    @Autowired
    EquipmentResultRepository eq;

    @Autowired
    EquipmentStatusRepository equipmentStatusRepository;

    /**
     * 新增记录
     */
    @Override
    public CommonResult addorUpdateEquipmentResult(EquipmentResultDto equipmentResultDto) {
        EquipmentResult result = DtoFactory.convert(equipmentResultDto, new EquipmentResult());
        return ResultFactory.commonSuccess(eq.save(result));
    }

    /**
     * 记录设备信息
     * @param  equipmentDto
     */
    public CommonResult addorUpdateEquipment(EquipmentDto equipmentDto){
        EquipmentResult equipmentResult = eq.getById(equipmentDto.getResId());
        if(equipmentResult == null){
            equipmentResult = new EquipmentResult();
            equipmentDto.setResId(equipmentResult.getId());
        }
        equipmentResult.setName(equipmentDto.getMeasuringName());
        Equipment equipment = new Equipment();
        equipment = DtoFactory.convert(equipmentDto,equipment);

        equipmentResult.getEquipments().add(equipment);
        equipmentResult.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(eq.save(equipmentResult));
    }
    /**
     * 记录设备运行状态信息
     * @param statusDto
     * @return
     */
    @Override
    public CommonResult addorUpdateEquipmentStatus(EquipmentStatusDto statusDto) {
         EquipmentResult equipmentResult = eq.getById(statusDto.getResId());
//        //如果resId不存在
        if(equipmentResult == null){
            equipmentResult = new EquipmentResult();
            statusDto.setResId(equipmentResult.getId());
        }
        equipmentResult.setName(statusDto.getName());
        EquipmentStatus equipmentStatus = new EquipmentStatus();
        equipmentStatus = DtoFactory.convert(statusDto,equipmentStatus);

        equipmentResult.getEquipmentStatuses().add(equipmentStatus);
        equipmentResult.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(eq.save(equipmentResult));
    }
    /**
     * 记录设备保养/保险信息
     */
    @Override
    public CommonResult addOrUpdateEquipmentMaintenance(EquipmentMaintenanceDto maintenanceDto){
        EquipmentResult equipmentResult = eq.getById(maintenanceDto.getLinkId());
        if(equipmentResult == null){
           equipmentResult = new EquipmentResult();
           maintenanceDto.setLinkId(equipmentResult.getId());
        }
        equipmentResult.setName(maintenanceDto.getName());
        EquipmentMaintenance equipmentMaintenance = new EquipmentMaintenance();
        equipmentResult.getEquipmentMaintenances().add(equipmentMaintenance);
        equipmentResult.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(eq.save(equipmentResult));
        }


    /**
     * 根据id查询对象
     * @param resId
     * @return
     */
    @Override
    public EquipmentResult getEquipmentResultById(Long resId) {
        EquipmentResult equipmentResult = eq.getById(resId);
        return equipmentResult;
    }

    /**
     * 删除运行信息
     * @param resId
     * @param id
     * @return
     */
    @Override
    public CommonResult deleteStatus(Long resId, Long id) {
        EquipmentResult equipmentResult = eq.getById(resId);
        if(equipmentResult!=null){
            List<EquipmentStatus> list = equipmentResult.getEquipmentStatuses();
            if(list!=null && list.size()>0){
                for(EquipmentStatus status:list){
                    if(status.getId().equals(id)){
                        status.setIsActive(false);
                        list.remove(status);
                        equipmentResult.setEquipmentStatuses(list);
                        Long l = eq.save(equipmentResult);
                        return ResultFactory.commonSuccess(l);
                    }
                }
                String message = "次设备运行信息不存在";
                return ResultFactory.commonError(message);
            }
        }
        String message = "此记录不存在";
        return ResultFactory.commonError(message);
    }

    /**
     * 删除设备信息
     */
    @Override
    public CommonResult deleteEquipment(Long resId,Long id){
        EquipmentResult equipmentResult = eq.getById(resId);
        if(equipmentResult!=null){
            List<Equipment> list = equipmentResult.getEquipments();
            if(list!=null && list.size()>0){
                for(Equipment equipment:list){
                    if(equipment.getId().equals(id)){
                        equipment.setIsActive(false);
                        list.remove(equipment);
                        equipmentResult.setEquipments(list);
                        Long l = eq.save(equipmentResult);
                        return ResultFactory.commonSuccess(l);
                    }
                }
                String message = "设备不存在";
                return ResultFactory.commonError(message);
            }
        }
        String message = "此记录不存在";
        return ResultFactory.commonError(message);
    }

    /**
     * 删除保修信息
     */
    @Override
    public CommonResult deleteEquiMainten(Long resId, Long id) {
        EquipmentResult equipmentResult = eq.getById(resId);
        if(equipmentResult!=null){
           List<EquipmentMaintenance> list = equipmentResult.getEquipmentMaintenances();
            if(list.size()>0 && list!=null){
                for(EquipmentMaintenance  e: list){
                    if(e.getId().equals(id)){
                        e.setIsActive(false);
                        list.remove(e);
                        equipmentResult.setEquipmentMaintenances(list);
                        Long l = eq.save(equipmentResult);
                        return ResultFactory.commonSuccess(l);
                    }
                }
                String message = "保养维护信息不存在";
                return ResultFactory.commonError(message);
            }
        }
        String message = "此记录不存在";
        return ResultFactory.commonError(message);
    }
    /**
     * 删除记录
     */
    @Override
    public CommonResult deleteRequimentResult(List<Long> ids){
        Criteria criteria = getSession().createCriteria(EquipmentResult.class);
        criteria.add(Restrictions.eq("isActive",true));
        if(ids == null || ids.size() < 1){
            return ResultFactory.commonError("请选择删除记录");
        }
        criteria.add(Restrictions.in("id",ids));
        List<EquipmentResult> list = criteria.list();
        for(EquipmentResult e: list){
            e.setIsActive(false);
            e.setLastUpdateTime(new Date());
            eq.save(e);
        }
        return ResultFactory.commonSuccess();
    }






}
