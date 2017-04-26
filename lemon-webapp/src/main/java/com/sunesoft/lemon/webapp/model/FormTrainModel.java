package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;

import java.util.List;

/**
 * Created by admin on 2016/7/26.
 */
public class FormTrainModel {


    private FormTrainChooseEmpDto dto;

    private  List<EmpDto> empList;

    public FormTrainChooseEmpDto getDto() {
        return dto;
    }

    public void setDto(FormTrainChooseEmpDto dto) {
        this.dto = dto;
    }

    public List<EmpDto> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmpDto> empList) {
        this.empList = empList;
    }
}
