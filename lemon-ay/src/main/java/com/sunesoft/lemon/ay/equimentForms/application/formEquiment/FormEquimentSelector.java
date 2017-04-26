package com.sunesoft.lemon.ay.equimentForms.application.formEquiment;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/8/15.
 */
@Component
public class FormEquimentSelector implements FormServiceSelector{
    private final String formKind = "SYY_AY_LC01";

    @Autowired
    private FormEquimentService formEquimentService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return formEquimentService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formkind) {
        return this.formKind.equals(formkind);
    }
}
