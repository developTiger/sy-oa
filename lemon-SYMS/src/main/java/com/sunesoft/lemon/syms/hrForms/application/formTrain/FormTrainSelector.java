package com.sunesoft.lemon.syms.hrForms.application.formTrain;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangkefan on 2016/7/22.
 */
@Component
public class FormTrainSelector implements FormServiceSelector{
    private  final String formKind = "SYY_RS_LC01";

    @Autowired
    FormTrainService trainService;

    @Override
    public FormService getService(String fKind){
        if(this.formKind.equals(fKind)){
            return trainService;
        }
        return null;
    }
    @Override
    public Boolean checkFormKind(String fKind){
        return formKind.equals(fKind);
    }

}
