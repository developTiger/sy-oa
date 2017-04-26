package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentHealthRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentWorkingRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentHealthRecordDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentWorkingRecordDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

/**
 * Created by admin on 2016/10/21.
 */
public interface EquipmentHealthRecordService {

    public CommonResult addOrUpdateEquipWorkHealth(EquipmentHealthRecordDto dto);

    public PagedResult<EquipmentHealthRecordDto> getPagesByequipWorkHealth(EquipmentHealthRecordCriteria recordCriteria,String equipmentId);

    public CommonResult delete(Long ids);

    public EquipmentHealthRecord getById(Long id);

}
