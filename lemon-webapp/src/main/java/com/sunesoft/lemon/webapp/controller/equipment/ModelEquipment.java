package com.sunesoft.lemon.webapp.controller.equipment;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;

/**
 * Created by admin on 2016/8/20.
 */
public class ModelEquipment {

    private EquipmentDto equipmentDto;
    private EquipmentMaintenance equipmentMaintenance;

    public EquipmentMaintenance getEquipmentMaintenance() {
        return equipmentMaintenance;
    }

    public void setEquipmentMaintenance(EquipmentMaintenance equipmentMaintenance) {
        this.equipmentMaintenance = equipmentMaintenance;
    }

    public EquipmentDto getEquipmentDto() {
        return equipmentDto;
    }

    public void setEquipmentDto(EquipmentDto equipmentDto) {
        this.equipmentDto = equipmentDto;
    }
}
