package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/9/3.
 */
@Component
public class FormInnovationAchievementSelector implements FormServiceSelector {

    private final String form_kind = "SYY_DQ_LC03";


    @Autowired
    private  FormInnovationAchievementService formInnovationAchievementService;

    @Override
    public FormService getService(String formkind) {
        if (form_kind.equals(formkind)){
            return formInnovationAchievementService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.form_kind.equals(formKind);
    }
}
