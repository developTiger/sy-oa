package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentResultDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentResult;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import com.sunesoft.lemon.fr.results.CommonResult;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
public interface EquipmentResultService {

    public CommonResult addorUpdateEquipmentStatus(EquipmentStatusDto statusDto);

    public CommonResult addorUpdateEquipmentResult(EquipmentResultDto equipmentResultDto);

    public CommonResult addorUpdateEquipment(EquipmentDto equipmentDto);

    public CommonResult addOrUpdateEquipmentMaintenance(EquipmentMaintenanceDto maintenanceDto);

    public EquipmentResult getEquipmentResultById(Long resId);

    public CommonResult deleteStatus(Long resId,Long id);

    public CommonResult deleteEquipment(Long resId,Long id);

    public CommonResult deleteEquiMainten(Long resId,Long id);

    public CommonResult deleteRequimentResult(List<Long> ids);

}
