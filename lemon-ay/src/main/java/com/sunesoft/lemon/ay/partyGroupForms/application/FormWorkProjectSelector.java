package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/9/2.
 */
@Component
public class FormWorkProjectSelector implements FormServiceSelector {

    private final String form_kind = "SYY_DQ_LC01";

    @Autowired
    private FormWorkProjectService formWorkProjectService;

    @Override
    public FormService getService(String formkind) {
        if (form_kind.equals(formkind)){
            return formWorkProjectService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.form_kind.equals(formKind);
    }
}
