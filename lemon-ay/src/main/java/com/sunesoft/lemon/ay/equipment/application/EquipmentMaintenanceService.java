package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentMaintenanceCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
public interface EquipmentMaintenanceService {

    /**
     * 新增设备维护保养记录
     */
    public CommonResult addOrUpEquipmentMaintenance(EquipmentMaintenanceDto equipmentMaintenanceDto);

    /**
     * 获取所有维修信息
     * @return
     */
    public List<EquipmentMaintenance> getAll();

    /**
     * 根据ID获取维修信息
     * @param id
     * @return
     */
    public EquipmentMaintenanceDto getById(Long id);

    public PagedResult<EquipmentMaintenanceDto> getPageEquipmentMaintenance(EquipmentMaintenanceCriteria equipmentMaintenanceCriteria,Long equipmentId);

    /**
     * 通过equipmentId查找所有的EquipmentMaintenance信息
     * @param equipmentId
     * @return
     */
    public List<EquipmentMaintenanceDto> getAllByEquipmentId(Long equipmentId);




}
