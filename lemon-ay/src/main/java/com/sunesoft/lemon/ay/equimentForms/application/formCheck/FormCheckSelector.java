package com.sunesoft.lemon.ay.equimentForms.application.formCheck;

import com.sunesoft.lemon.ay.equimentForms.application.formEquiment.FormEquimentService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Component
public class FormCheckSelector implements FormServiceSelector{
    private final String formKind = "SYY_AY_LC03";

    @Autowired
    private FormCheckService formCheckService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return formCheckService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String formkind) {
        return this.formKind.equals(formkind);
    }
}
