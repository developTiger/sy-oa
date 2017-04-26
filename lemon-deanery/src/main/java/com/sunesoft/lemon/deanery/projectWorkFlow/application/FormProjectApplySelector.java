package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.carWorkFlow.application.CarApplyService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
@Component
public class FormProjectApplySelector implements FormServiceSelector {
    private final String formKind = "SYY_KG_LC02";

    @Autowired
    private FormProjectApplyService formProjectApplyService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return formProjectApplyService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
