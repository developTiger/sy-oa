package com.sunesoft.lemon.syms.hrForms.application.formDeptAppraise;


import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormDeptAppraiseSelector implements FormServiceSelector {
    private  final String formKind = "SYY_RS_LC04";

    @Autowired
    FormDeptAppraiseService formDeptAppraiseService;

    @Override
    public FormService getService(String fKind){
        if(this.formKind.equals(fKind)){
            return formDeptAppraiseService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String fKind){
        return formKind.equals(fKind);
    }

}
