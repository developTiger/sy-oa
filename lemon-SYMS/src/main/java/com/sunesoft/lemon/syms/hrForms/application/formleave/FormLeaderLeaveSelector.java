package com.sunesoft.lemon.syms.hrForms.application.formleave;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhouz on 2016/6/29.
 *//*
@Component
public class FormLeaderLeaveSelector implements FormServiceSelector {

    private final String formKind = "SYY_RS_LC06_01";


    @Autowired
    FormLeaveService formLeaveService;

    @Override
    public FormService getService(String fkind) {
        if (formKind.equals(fkind))
            return formLeaveService;
        return null;
    }

    @Override
    public Boolean checkFormKind(String formkind) {
        return formKind.equals(formkind);
    }
}
*/