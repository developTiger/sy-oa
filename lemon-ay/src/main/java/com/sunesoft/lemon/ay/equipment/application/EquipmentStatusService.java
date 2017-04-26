package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import com.sunesoft.lemon.fr.results.CommonResult;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
public interface EquipmentStatusService {

    /**
     * 新增设备运行状态
     */
    public CommonResult addOrUpEquipmentStatus(EquipmentStatusDto equipmentStatusDto);

    /**
     * 根据resId查询一条设备运行状态
     * @param resId
     * @return
     */
     public EquipmentStatus getByResId(Long resId);

}
