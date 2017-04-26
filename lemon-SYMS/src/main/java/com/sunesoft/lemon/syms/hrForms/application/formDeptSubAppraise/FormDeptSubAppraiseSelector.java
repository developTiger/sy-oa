package com.sunesoft.lemon.syms.hrForms.application.formDeptSubAppraise;


import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormDeptSubAppraiseSelector implements FormServiceSelector {
    private  final String formKind = "SYY_RS_LC04_01";

    @Autowired
    FormDeptSubAppraiseService deptSubAppraiseService;

    @Override
    public FormService getService(String fKind){
        if(this.formKind.equals(fKind)){
            return deptSubAppraiseService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String fKind){
        return formKind.equals(fKind);
    }

}
