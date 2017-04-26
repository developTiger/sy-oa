package com.sunesoft.lemon.ay.equimentForms.application.formEquiment;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquipmentDto;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipment;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/15.
 */
public interface FormEquimentService extends FormService<FormEquipment,FormEquipmentDto> {

     public List<FormEquipment> getLists();

}
