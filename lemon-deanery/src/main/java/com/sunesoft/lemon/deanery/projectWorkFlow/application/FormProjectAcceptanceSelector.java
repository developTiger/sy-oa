package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2016/8/26 0026.
 */
@Component
public class FormProjectAcceptanceSelector implements FormServiceSelector {
    private final String formKind = "SYY_KG_LC05";

    @Autowired
    private FormProjectAcceptanceService formProjectAcceptanceService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return formProjectAcceptanceService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
