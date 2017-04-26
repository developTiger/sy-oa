package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentWorkingRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentWorkingRecordDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
public interface EquipmentWorkingRecordService {

    public CommonResult addOrUpdateEquipWorkRecord(EquipmentWorkingRecordDto dto);

    public PagedResult<EquipmentWorkingRecordDto> getPagesByequipWorkRecord(EquipmentWorkingRecordCriteria recordCriteria,String equipmentId);

    public CommonResult delete(Long ids);

    public EquipmentWorkingRecord getById(Long id);

}
