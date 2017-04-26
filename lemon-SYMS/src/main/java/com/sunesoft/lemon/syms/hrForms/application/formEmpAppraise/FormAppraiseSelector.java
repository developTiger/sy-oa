package com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise;


import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormAppraiseSelector implements FormServiceSelector {
    private  final String formKind = "SYY_RS_LC03";

    @Autowired
    FormAppraiseService appraiseService;

    @Override
    public FormService getService(String fKind){
        if(this.formKind.equals(fKind)){
            return appraiseService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String fKind){
        return formKind.equals(fKind);
    }

}
