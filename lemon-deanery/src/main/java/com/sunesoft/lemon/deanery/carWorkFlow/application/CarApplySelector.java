package com.sunesoft.lemon.deanery.carWorkFlow.application;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
@Component
public class CarApplySelector implements FormServiceSelector {
    private final String formKind = "SYY_YW_LC01";

    @Autowired
    private CarApplyService carApplyService;
    @Override
    public FormService getService(String formkind) {
        if(formKind.equals(formkind)){
            return carApplyService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String formKind) {
        return this.formKind.equals(formKind);
    }
}
