package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
public interface EquipmentService {

    /**
     * 新增设备
     */
    public CommonResult addOrUpEquipment(EquipmentDto equipmentDto);



    EquipmentDto getEquipById(Long id);

    /**
     * 根据设备的资产编号寻找设备
     * @param propertyNum
     * @return
     */
    public EquipmentDto getEquipByNun(String propertyNum);

    /**
     * 删除一个设备
     * @param id
     * @return
     */
    public CommonResult deleteOne(Long id);

    /**
     * 删除多个设备
     * @param ids
     * @return
     */
    public CommonResult deleteMore(List<Long> ids);



    /**
     * 修改设备的状态：正常，维修中，已报废,已申请 维修
     * @param id
     * @param station
     * @return
     */
    public CommonResult setStation(Long id,EquipmentStation station);


    CommonResult editBySystemNumber(EquipmentDto dto);


    List<EquipmentDto> getAll();

    /**
     * 可以送去维修的所有设备，报废，正在维修中的设备无法再次报修
     * @return
     */
    List<EquipmentDto> getRepairAll(EquipmentStation station);


    public PagedResult<EquipmentDto> getPageEquipmentDto(EquipmentCriteria equipmentCriteria);



}
