package com.sunesoft.lemon.ay.equimentForms.application.formAssessment;

import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by jiangkefan on 2016/8/18.
 */

@Component
public class FormAssessmentSelector implements FormServiceSelector
{
    private final String formKind = "SYY_AY_LC05";

    @Autowired
    private FormAssessmentService formAssessmentService;

    @Override
    public FormService getService(String fk) {
        if(formKind.equals(fk)){
          return formAssessmentService;
        }
        return  null;

    }

    @Override
    public Boolean checkFormKind(String fk) {
            return this.formKind.equals(fk);
    }
}
