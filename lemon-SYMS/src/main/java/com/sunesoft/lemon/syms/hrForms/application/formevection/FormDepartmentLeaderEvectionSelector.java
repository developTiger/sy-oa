package com.sunesoft.lemon.syms.hrForms.application.formevection;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/7/19.
 */
@Component
public class FormDepartmentLeaderEvectionSelector implements FormServiceSelector{

     private final String formKind = "SYY_RS_LC05_02";

     @Autowired
     FormEvectionService formEvectionService;


    @Override
    public FormService getService(String fkind) {
        if (formKind.equals(fkind))
            return formEvectionService;
        return null;
    }

    @Override
    public Boolean checkFormKind(String fkind){
        return formKind.equals(fkind);
    }

}
