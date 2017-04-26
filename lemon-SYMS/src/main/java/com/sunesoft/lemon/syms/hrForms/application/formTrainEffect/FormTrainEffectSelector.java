package com.sunesoft.lemon.syms.hrForms.application.formTrainEffect;

import com.sunesoft.lemon.syms.hrForms.application.formTrain.FormTrainService;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainEffect;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@Component
public class FormTrainEffectSelector implements FormServiceSelector {
    private  final String formKind = "SYY_RS_LC02";

    @Autowired
    public FormTrainEffectService formTrainEffectService;


    @Override
    public FormService getService(String fKind) {
        if(this.formKind.equals(fKind)){
            return formTrainEffectService;
        }
        return null;
    }

    @Override
    public Boolean checkFormKind(String fKind) {
        return formKind.equals(fKind);
    }
}
