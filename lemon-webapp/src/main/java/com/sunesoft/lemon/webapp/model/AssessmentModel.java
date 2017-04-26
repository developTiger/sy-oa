package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;

/**
 * Created by admin on 2016/9/1.
 */
public class AssessmentModel {

    private FormAssessmentDto formAssessmentDto;
    private EquipmentDto equipmentDto;

    public FormAssessmentDto getFormAssessmentDto() {
        return formAssessmentDto;
    }

    public void setFormAssessmentDto(FormAssessmentDto formAssessmentDto) {
        this.formAssessmentDto = formAssessmentDto;
    }

    public EquipmentDto getEquipmentDto() {
        return equipmentDto;
    }

    public void setEquipmentDto(EquipmentDto equipmentDto) {
        this.equipmentDto = equipmentDto;
    }
}
