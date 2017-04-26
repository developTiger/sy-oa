package com.sunesoft.lemon.deanery.ReceptionFlow.application;

import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseServiceImpl;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Component
public class ReceptionSelector implements FormServiceSelector {
    private final String formKind = "SYY_YW_LC02";

    @Autowired
    private Reception2Service receptionService;

    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return receptionService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
