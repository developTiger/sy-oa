package com.sunesoft.lemon.ay.equimentForms.application.formEquiMain;

import com.sunesoft.lemon.ay.equimentForms.application.formEquiment.FormEquimentService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/8/17.
 */
@Component
public class FormEquiMainSelector implements FormServiceSelector{

    private final String formKind = "SYY_AY_LC02";

    @Autowired
    private FormEquiMainService formEquiMainService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return formEquiMainService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String formkind) {
        return this.formKind.equals(formkind);
    }
}
